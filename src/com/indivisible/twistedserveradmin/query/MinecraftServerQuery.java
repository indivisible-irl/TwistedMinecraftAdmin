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

/**
 * Class to contain and perform a single, basic Query on a Minecraft Server.
 * 
 * @author indiv
 */
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

    /**
     * Create a new MinecraftServerQuery instance.
     * 
     * @param address
     * @param port
     * @param timeout
     */
    public MinecraftServerQuery(String address, int port, int timeout)
    {
        //TODO: Just take a single Server argument.
        this.address = address;
        this.port = port;
        this.timeout = timeout;
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    /**
     * Set the Server's ping version.
     * 
     * @param pingVersion
     */
    protected void setPingVersion(int pingVersion)
    {
        this.pingVersion = pingVersion;
    }

    /**
     * Get the Server's ping version.
     * 
     * @return
     */
    public int getPingVersion()
    {
        //TODO: Look into MCServer.pingVersion
        return this.pingVersion;
    }

    /**
     * Set the Server's query protocol version.
     * 
     * @param protocolVersion
     */
    protected void setProtocolVersion(int protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    /**
     * Get the Server's query protocol version.
     * 
     * @return
     */
    public int getProtocolVersion()
    {
        //TODO: Look into MCServer.protocolVersion
        return this.protocolVersion;
    }

    /**
     * Set the Server's game version.
     * 
     * @param gameVersion
     */
    protected void setGameVersion(String gameVersion)
    {
        this.gameVersion = gameVersion;
    }

    /**
     * Get the Server's game version.
     * 
     * @return
     */
    public String getGameVersion()
    {
        //TODO: Test and actually use this value
        return this.gameVersion;
    }

    /**
     * Set the number of players online.
     * 
     * @param playersOnline
     */
    protected void setPlayersOnline(int playersOnline)
    {
        this.playersOnline = playersOnline;
    }

    /**
     * Get the number of players online.
     * 
     * @return
     */
    public int getPlayersOnline()
    {
        return this.playersOnline;
    }

    /**
     * Set the maximum allowed players online.
     * 
     * @param maxPlayers
     */
    protected void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Get the maximum allowed players online.
     * 
     * @return
     */
    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    /**
     * Set whether the query received a response form the Server.
     * 
     * @param response
     */
    protected void setResponse(Boolean response)
    {
        this.response = response;
    }

    /**
     * Get whether the query received a response from the Server.
     * 
     * @return
     */
    public Boolean receivedResponse()
    {
        return response;
    }

    /**
     * Get the time, in epoch milliseconds, this query was last run.
     * 
     * @return
     */
    public long getTimeLastRun()
    {
        return timeLastRun;
    }

    /**
     * Set the status of the queried Server.
     * 
     * @param status
     */
    protected void setServerStatus(ServerStatus status)
    {
        this.serverStatus = status;
    }

    /**
     * Get the status of the queried Server.
     * 
     * @return
     */
    public ServerStatus getServerStatus()
    {
        return serverStatus;
    }

    ///////////////////////////////////////////////////////
    ////    query methods
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
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-16BE"));

            dataOutputStream.write(new byte[] {
                    (byte) 0xFE, (byte) 0x01
            });

            int packetId = inputStream.read();
            if (packetId == -1)
            {
                socket.close();
                throw new IOException("mcdev: Premature end of stream.");
            }

            if (packetId != 0xFF)
            {
                socket.close();
                throw new IOException("mcdev: Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();
            if (length == -1)
            {
                socket.close();
                throw new IOException("mcdev: Premature end of stream.");
            }
            else if (length == 0)
            {
                socket.close();
                throw new IOException("mcdev: Invalid string length.");
            }

            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length)
            {
                socket.close();
                throw new IOException("mcdev: Premature end of stream.");
            }

            String string = new String(chars);
            if (string.startsWith("§"))         //TODO: Test which versions use which format
            {
                String[] data = string.split("\0");
                this.setPingVersion(Integer.parseInt(data[0].substring(1)));
                this.setProtocolVersion(Integer.parseInt(data[1]));
                this.setGameVersion(data[2]);   //TODO: Test this value across versions
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
            //ASK: any socket exception means offline? need to test
            setServerStatus(ServerStatus.offline);
            //e.printStackTrace();
            return false;
        }
        catch (SocketTimeoutException e)
        {
            setResponse(false);
            Main.myLog.error(TAG, "Socket for Query timed out for " + address + ":"
                    + port);
            setServerStatus(ServerStatus.error);
            //NOTE: BTeam was crashed (OutOfMemoryError) and threw to here.
            return false;
        }
        catch (IOException e)
        {
            setResponse(false);
            Main.myLog.error(TAG, "IOException -- " + address + ":" + port + " / "
                    + timeout);
            setServerStatus(ServerStatus.error);
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

        setServerStatus(ServerStatus.online);
        setResponse(true);
        return true;
    }
}
