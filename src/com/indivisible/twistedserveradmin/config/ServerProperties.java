package com.indivisible.twistedserveradmin.config;

import java.io.File;
import java.io.IOException;
import com.indivisible.twistedserveradmin.system.Main;

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

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

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
    //ASK: Any extra (possibly optional) keys?

    private static final String TAG = "ServerProps";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

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


    ///////////////////////////////////////////////////////
    ////    retrieve values
    ///////////////////////////////////////////////////////

    /**
     * Get the Server Message of the Day exactly as saved in the settings
     * file. ie in-game description.
     * 
     * @return
     */
    public String getRawMOTD()
    {
        return getString(KEY_MOTD);
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
        if (motd == null || motd.equals(""))
        {
            return null;
        }
        String unescapedMOTD = unescapeUnicodeChars(motd);
        return recurseRemoveMinecraftFormatting(unescapedMOTD);
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
            return null;
        }
        return name;
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
            return null;
        }
        else if (!testIPv4(ip))
        {
            //TODO: Do or don't allow failed IPs
            Main.myLog
                    .warning(TAG, "IP failed IPv4 test. Returning [" + ip + "] anyway.");

        }
        return ip;
    }

    /**
     * Get the port the server is running on.
     * 
     * @return
     */
    public int getPort()
    {
        return getInt(KEY_PORT);
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
        return getString(KEY_SEED);
    }

    /**
     * Check whether the server is set to allow entry based on whitelist.txt
     * 
     * @return
     */
    public boolean isWhitelisted()
    {
        return getBool(KEY_WHITELIST);
    }

    /**
     * Check whether the server is running in "Online Mode". <br/>
     * If enabled the server will query Mojang servers and verify a user is
     * who the say they are.
     * 
     * @return
     */
    public boolean isOnlineModeEnabled()
    {
        return getBool(KEY_ONLINE_MODE);
    }

    /**
     * Checks whether PVP is enabled on the server. <br/>
     * May not be accurate as can be overridden with mods and plugins.
     * 
     * @return
     */
    public boolean isPVPEnabled()
    {
        return getBool(KEY_PVP);
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
        return getInt(KEY_DIFFICULTY);
    }

    /**
     * Check if the Server has extended Queries enabled.
     * 
     * @return
     */
    public boolean isQueryEnabled()
    {
        return getBool(KEY_QUERY_ENABLED);
    }

    /**
     * Retrieve the port the Server is listening on for extended Query
     * requests. <br />
     * Remember to test isQueryEnabled() if you intend to Query on this.
     * 
     * @return
     */
    public String getQueryPort()
    {
        return getString(KEY_QUERY_PORT);
    }


}
