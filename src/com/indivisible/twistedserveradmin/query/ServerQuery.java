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
import java.nio.charset.Charset;

public final class ServerQuery
{

    //// data

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

    private long timeLastRun = 0L;

    //// constructors

    public ServerQuery(String address, int port, int timeout)
    {
        this.address = address;
        this.port = port;
        this.timeout = timeout;
    }


    //// gets & sets

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


    //// methods

    /**
     * Connect to and ask for server info
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
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 0xFF)
            {
                socket.close();
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();

            if (length == -1)
            {
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (length == 0)
            {
                socket.close();
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];

            if (inputStreamReader.read(chars, 0, length) != length)
            {
                socket.close();
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

            //			dataOutputStream.close();
            //			outputStream.close();
            //
            //			inputStreamReader.close();
            //			inputStream.close();
            //
            //			socket.close();
        }
        catch (SocketException exception)
        {
            setResponse(false);
            return false;
        }
        catch (IOException exception)
        {
            setResponse(false);
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

        setResponse(true);
        return true;
    }
}
