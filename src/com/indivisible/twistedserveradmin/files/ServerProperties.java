package com.indivisible.twistedserveradmin.files;

import java.io.IOException;

/**
 * Class to read and parse "server.properties" from a Minecraft Server's
 * folder.
 * 
 * @author indiv
 * 
 */
public class ServerProperties
        extends ServerSettings
{

    //// data

    //TODO Test if all server types use same names (vanilla, bukkit, spiggot etc) 
    private static final String MOTD = "motd";
    private static final String NAME = "server-name";
    private static final String PORT = "server-port";
    private static final String IP = "server-ip";
    private static final String ONLINE_MODE = "online-mode";
    private static final String QUERY_PORT = "query.port";
    private static final String QUERY_ENABLED = "enable-query";
    private static final String SEED = "level-seed";
    private static final String WHITELIST = "white-list";
    private static final String DIFFICULTY = "difficulty";
    private static final String PVP = "pvp";


    //// constructor && init

    /**
     * Class to read and parse "server.properties" from a minecraft Server's
     * folder.
     * 
     * @param propsFilePath
     * @throws IOException
     */
    public ServerProperties(String propsFilePath) throws IOException
    {
        super(propsFilePath);
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
        return getRawProperty(MOTD);
    }

    /**
     * Get the Server Message of the Day with colour and font style notations
     * removed.
     * 
     * @return
     */
    public String getCleanMOTD()
    {
        String unescapedMOTD = unescape(getRawMOTD());
        //System.out.println(" === Unescaped: [" + unescapedMOTD + "]");
        return recurseRemoveColor(unescapedMOTD);
    }

    /**
     * Get the Server name as saved in the server.properties file.
     * 
     * @return
     */
    public String getServerName()
    {
        return getRawProperty(NAME);
    }

    /**
     * Get the port the server is running on.
     * 
     * @return
     */
    public int getPort()
    {
        return getInt(PORT);
    }

    /**
     * Get the IP the server is running on. If not set will be the Game
     * Server's default address. TODO: Need to retrieve this default address?
     * 
     * @return
     */
    public String getIP()
    {
        String value = getRawProperty(IP);
        if (testIPv4(value))
        {
            return value;
        }
        else
        {
            System.out.println(" === Not an IPv4 address");
            return null;
        }
    }

    /**
     * Check whether the server has enabled Querying
     * 
     * @return
     */
    public Boolean isQueryEnabled()
    {
        return getBool(QUERY_ENABLED);
    }

    /**
     * Get the port the server listens for Queries on.
     * 
     * @return
     */
    public int getQueryPort()
    {
        return getInt(QUERY_PORT);
    }

    /**
     * Get the world seed for the generated map. May not be accurate to
     * current world as may have been imported or customised.
     * 
     * @return
     */
    public String getSeed()
    {
        return getRawProperty(SEED);
    }

    /**
     * Check whether the server is set to allow entry based on whitelist.txt
     * 
     * @return
     */
    public boolean isWhitelisted()
    {
        return getBool(WHITELIST);
    }

    /**
     * Check whether the server is running in "Online Mode". If enabled the
     * server will Query Mojang and verify a user is who the say they are.
     * 
     * @return
     */
    public boolean isOnlineEnabled()
    {
        return getBool(ONLINE_MODE);
    }

    /**
     * Checks whether PVP is enabled on the server. May not be accurate as can
     * be overridden with mods and plugins.
     * 
     * @return
     */
    public boolean isPVPEnabled()
    {
        return getBool(PVP);
    }

    /**
     * Get the configured difficulty level set for the server.
     * <ul>
     * <li>0: peaceful</li>
     * <li>1: easy</li>
     * <li>2: normal</li>
     * <li>3: hard</li>
     * <li>4: hardcore</li>
     * </ul>
     * 
     * @return
     */
    public int getDifficulty()
    {
        return getInt(DIFFICULTY);
    }


}
