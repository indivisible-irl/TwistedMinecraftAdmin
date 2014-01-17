package com.indivisible.twistedserveradmin.config;

import java.io.File;
import java.io.IOException;

/**
 * Class to read and parse "server.properties" from a Minecraft Server's
 * folder.
 * 
 * @author indiv
 * 
 */
public class ServerProperties
        extends Settings
{

    //// data

    //TODO: Test if all server types use same names (vanilla, bukkit, spiggot etc) 
    private static final String KEY_MOTD = "motd";
    private static final String KEY_NAME = "server-name";
    private static final String KEY_PORT = "server-port";
    private static final String KEY_IP = "server-ip";
    private static final String KEY_ONLINE_MODE = "online-mode";
    private static final String KEY_QUERY_PORT = "query.port";
    private static final String KEY_QUERY_ENABLED = "enable-query";
    private static final String KEY_SEED = "level-seed";
    private static final String KEY_WHITELIST = "white-list";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_PVP = "pvp";

    private static final String DEFAULT_MOTD = "-= No MOTD =-";
    private static final String DEFAULT_NAME = "-= No Name =-";
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 25565;
    private static final boolean DEFAULT_QUERY_ENABLED = false;
    private static final String DEFAULT_SEED = "-= No Seed =-";
    private static final boolean DEFAULT_WHITELISTED = false;
    private static final boolean DEFAULT_ONLINE = true;
    private static final boolean DEFAULT_PVP_ENABLED = true;
    private static final int DEFAULT_DIFFICULTY = -1;


    //// constructor && init

    /**
     * Class to read and parse "server.properties" from a minecraft Server's
     * folder.
     * 
     * @param propsFilePath
     * @throws IOException
     */
    public ServerProperties(File propsFile) throws IOException
    {
        super(propsFile);
    }


    //// public methods

    /**
     * Get the Server Message of the Day exactly as saved in the settings
     * file. Used for in-game display to user when selecting a Server to join.
     * 
     * @return
     */
    public String getRawMOTD()
    {
        String motd = getString(KEY_MOTD);
        if (motd == null || motd.equals(""))
        {
            return DEFAULT_MOTD;
        }
        else
        {
            return motd;
        }
    }

    /**
     * Get the Server Message of the Day with colour and font style notations
     * removed.
     * 
     * @return
     */
    public String getCleanMOTD()
    {
        String motd = getRawMOTD();
        if (motd.equals(DEFAULT_MOTD))
        {
            return motd;
        }
        String unescapedMOTD = unescapeUnicodeChars(motd);
        return recurseRemoveMinecraftColor(unescapedMOTD);
    }

    /**
     * Get the Server name as saved in the server.properties file.
     * 
     * @return
     */
    public String getServerName()
    {
        String name = getString(KEY_NAME);
        if (name == null || name.equals(""))
        {
            return DEFAULT_NAME;
        }
        return name;
    }

    /**
     * Get the port the server is running on.
     * 
     * @return
     */
    public int getPort()
    {
        int port = getInt(KEY_PORT);
        if (port == Integer.MIN_VALUE)
        {
            return DEFAULT_PORT;
        }
        return port;
    }

    /**
     * Get the IP the server is running on. <br/>
     * If not set will be the Game Server's default address.
     * 
     * @return
     */
    public String getIP()
    {
        String ip = getString(KEY_IP);
        if (ip == null || ip.equals(""))
        {
            return DEFAULT_IP;
        }
        else if (testIPv4(ip))
        {
            return ip;
        }
        else
        {
            return DEFAULT_IP;
        }
    }

    /**
     * Check whether the server has enabled Querying
     * 
     * @return
     */
    public boolean isQueryEnabled()
    {
        Boolean bool = getBool(KEY_QUERY_ENABLED);
        if (bool == null)
        {
            return DEFAULT_QUERY_ENABLED;
        }
        return bool;
    }

    /**
     * Get the port the server listens for UDP Queries on.
     * 
     * @return
     */
    public int getQueryPort()
    {
        int port = getInt(KEY_QUERY_PORT);
        if (port == Integer.MIN_VALUE)
        {
            return DEFAULT_PORT;
        }
        return port;
    }

    /**
     * Get the world seed for the generated map. <br/>
     * May not be accurate to current world as it may have been imported or
     * customised.
     * 
     * @return
     */
    public String getSeed()
    {
        //TODO: if (online && query) getSeed()
        String seed = getString(KEY_SEED);
        if (seed == null || seed.equals(""))
        {
            return DEFAULT_SEED;
        }
        return seed;
    }

    /**
     * Check whether the server is set to allow entry based on whitelist.txt
     * 
     * @return
     */
    public boolean isWhitelisted()
    {
        Boolean whitelisted = getBool(KEY_WHITELIST);
        if (whitelisted == null)
        {
            return DEFAULT_WHITELISTED;
        }
        else
        {
            return whitelisted;
        }
    }

    /**
     * Check whether the server is running in "Online Mode". <br/>
     * If enabled the server will query Mojang servers and verify a user is
     * who the say they are.
     * 
     * @return
     */
    public boolean isOnlineEnabled()
    {
        Boolean bool = getBool(KEY_ONLINE_MODE);
        if (bool == null)
        {
            return DEFAULT_ONLINE;
        }
        return bool;
    }

    /**
     * Checks whether PVP is enabled on the server. <br/>
     * May not be accurate as can be overridden with mods and plugins.
     * 
     * @return
     */
    public boolean isPVPEnabled()
    {
        Boolean bool = getBool(KEY_PVP);
        if (bool == null)
        {
            return DEFAULT_PVP_ENABLED;
        }
        return bool;
    }

    /**
     * Get the configured difficulty level set for the server.
     * <ul>
     * <li>0: peaceful</li>
     * <li>1: easy</li>
     * <li>2: normal</li>
     * <li>3: hard</li>
     * </ul>
     * 
     * @return
     */
    public int getDifficulty()
    {
        int diff = getInt(KEY_DIFFICULTY);
        if (diff == Integer.MIN_VALUE)
        {
            return DEFAULT_DIFFICULTY;
        }
        return diff;
    }


}
