package com.indivisible.twistedserveradmin.query;

/*
 * This class courtesy of #mcdevs Original source can be found on
 * https://gist.github.com/Jckf/4574114
 * 
 * Modified by indivisible for www.Twisted.cat Minecraft servers
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import com.indivisible.twistedserveradmin.servers.ServerStatus;
import com.indivisible.twistedserveradmin.system.Main;

public final class MinecraftServerQuery
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    // need to connect
    private String address = null;
    private int port = -1;
    private int timeout = -1;

    // query results
    private int pingVersion = -1;
    private int protocolVersion = -1;
    private String gameVersion = null;
    private int playersOnline = 0;
    private int maxPlayers = -1;
    private Boolean response = null;
    private ServerStatus serverStatus = ServerStatus.unknown;

    private long timeLastRun = 0L;

    private static final String TAG = "MCServerQuery";

    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    public MinecraftServerQuery(String address, int port, int timeout)
    {
        this.address = address;
        this.port = port;
        this.timeout = timeout;
    }

    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    private void setPingVersion(int pingVersion)
    {
        this.pingVersion = pingVersion;
    }

    public int getPingVersion()
    {
        return this.pingVersion;
    }

    private void setProtocolVersion(int protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    public int getProtocolVersion()
    {
        return this.protocolVersion;
    }

    private void setGameVersion(String gameVersion)
    {
        this.gameVersion = gameVersion;
    }

    public String getGameVersion()
    {
        return this.gameVersion;
    }

    private void setPlayersOnline(int playersOnline)
    {
        this.playersOnline = playersOnline;
    }

    public int getPlayersOnline()
    {
        return this.playersOnline;
    }

    private void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    private void setResponse(Boolean response)
    {
        this.response = response;
    }

    public Boolean receivedResponse()
    {
        return response;
    }

    public long getTimeLastRun()
    {
        return timeLastRun;
    }

    public ServerStatus getServerStatus()
    {
        return serverStatus;
    }

    ///////////////////////////////////////////////////////
    ////    methods
    ///////////////////////////////////////////////////////

    /**
     * Connect to and ask for Server's info
     * 
     * @return
     */
    public boolean fetchData()
    {
        timeLastRun = System.currentTimeMillis();
        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        Socket socket = null;

        try
        {
            socket = new Socket();
            socket.setSoTimeout(timeout);

            socket.connect(new InetSocketAddress(address, port), timeout);
            //System.out.println("socket connected");

            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            //System.out.println("socket output");

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-16BE"));
            //System.out.println("socket input");

            dataOutputStream.write(new byte[] {
                    (byte) 0xFE, (byte) 0x01
            });
            //System.out.println("sent query");

            int packetId = inputStream.read();
            //System.out.println("read query");

            if (packetId == -1)
            {
                socket.close();
                //System.out.println("Premature end of stream");
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 0xFF)
            {
                socket.close();
                //System.out.println("Invalid packet ID");
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();

            if (length == -1)
            {
                socket.close();
                //System.out.println("Premature 2");
                throw new IOException("Premature end of stream.");
            }

            if (length == 0)
            {
                socket.close();
                //System.out.println("Invalid string length");
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];

            if (inputStreamReader.read(chars, 0, length) != length)
            {
                socket.close();
                //System.out.println("Premature 3");
                throw new IOException("Premature end of stream.");
            }

            String string = new String(chars);

            if (string.startsWith("§"))
            {
                String[] data = string.split("\0");
                this.setPingVersion(Integer.parseInt(data[0].substring(1)));
                this.setProtocolVersion(Integer.parseInt(data[1]));
                this.setGameVersion(data[2]);
                this.setPlayersOnline(Integer.parseInt(data[4]));
                this.setMaxPlayers(Integer.parseInt(data[5]));
            }
            else
            {
                String[] data = string.split("§");
                this.setPlayersOnline(Integer.parseInt(data[1]));
                this.setMaxPlayers(Integer.parseInt(data[2]));
            }
        }
        catch (SocketException e)
        {
            setResponse(false);
            Main.myLog.info(TAG, "Failed to connect to " + address + ":" + port);
            //ASK: socket exception means offline? Need to test
            serverStatus = ServerStatus.offline;
            //e.printStackTrace();
            return false;
        }
        catch (SocketTimeoutException e)
        {
            setResponse(false);
            Main.myLog.error(TAG, "Socket for Query timed out for " + address + ":"
                    + port);
            serverStatus = ServerStatus.error;
            //NOTE: BTeam was crashed (OutOfMemoryError) and threw to here.
            return false;
        }
        catch (IOException e)
        {
            setResponse(false);
            Main.myLog.error(TAG, "IOException -- " + address + ":" + port + " / "
                    + timeout);
            serverStatus = ServerStatus.error;
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (dataOutputStream != null)
            {
                try
                {
                    dataOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null)
            {
                try
                {
                    inputStreamReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (socket != null)
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        serverStatus = ServerStatus.online;
        setResponse(true);
        return true;
    }
}
