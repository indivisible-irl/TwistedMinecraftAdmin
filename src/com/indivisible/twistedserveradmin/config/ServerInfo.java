package com.indivisible.twistedserveradmin.config;

import java.io.IOException;

/**
 * Class to access Server's server.info settings
 * 
 * @author indiv
 * 
 */
public class ServerInfo
        extends Settings
{

    //// data

    private static final String NICKNAME = "server-nickname";
    private static final String VERSION = "minecraft-version";
    private static final String STARTUP_SCRIPT = "startup-script";
    private static final String AUTO_START = "auto-start";
    private static final String AUTO_STOP = "auto-stop";
    private static final String AUTO_RESTART = "auto-restart";
    private static final String SHOW_IN_CONSOLE = "show-in-console";
    private static final String SHOW_IN_API = "show-in-api";

    private static final String DEFAULT_NICKNAME = "no nickname";
    private static final String DEFAULT_VERSION = "no vers";
    private static final String DEFAULT_STARTUP_SCRIPT = "start.sh";
    private static final boolean DEFAULT_AUTO_START = false;
    private static final boolean DEFAULT_AUTO_STOP = false;
    private static final boolean DEFAULT_AUTO_RESTART = false;
    private static final boolean DEFAULT_SHOW_IN_CONSOLE = true;
    private static final boolean DEFAULT_SHOW_IN_API = false;


    //// constructor & init

    /**
     * Class to access Server's server.info settings
     * 
     * @param infoFilePath
     * @throws IOException
     */
    public ServerInfo(String infoFilePath) throws IOException
    {
        super(infoFilePath);
    }


    //// public methods

    /**
     * Get the Server's nickname. To be used for screen and easy
     * identification.
     * 
     * @return
     */
    public String getNickname()
    {
        String nick = getString(NICKNAME);
        if (nick == null || nick.equals(""))
        {
            return DEFAULT_NICKNAME;
        }
        return nick;
    }

    /**
     * Retrieve the Minecraft version this server is based on.<br/>
     * Reads from config file NOT from server instance. May be inaccurate.
     * 
     * @return
     */
    public String getVersion()
    {
        String vers = getString(VERSION);
        if (vers == null || vers.isEmpty())
        {
            return DEFAULT_VERSION;
        }
        return vers;
    }

    /**
     * Get the custom name for the startup file for the Server.
     * 
     * @return
     */
    public String getStartupScriptName()
    {
        String startup = getString(STARTUP_SCRIPT);
        if (startup == null || startup.isEmpty())
        {
            return DEFAULT_STARTUP_SCRIPT;
        }
        return startup;
    }

    /**
     * Get whether this Server should be started when doing a batch startup.
     * 
     * @return
     */
    public boolean doAutoStart()
    {
        Boolean bool = getBool(AUTO_START);
        if (bool == null)
        {
            return DEFAULT_AUTO_START;
        }
        return bool;
    }

    /**
     * Get whether this Server should be started when doing a batch stop.
     * 
     * @return
     */
    public boolean doAutoStop()
    {
        Boolean bool = getBool(AUTO_STOP);
        if (bool == null)
        {
            return DEFAULT_AUTO_STOP;
        }
        return bool;
    }

    /**
     * Get whether this Server should be restarted (or attempted to) if found
     * offline.
     * 
     * @return
     */
    public boolean doAutoRestart()
    {
        Boolean bool = getBool(AUTO_RESTART);
        if (bool == null)
        {
            return DEFAULT_AUTO_RESTART;
        }
        return bool;
    }

    /**
     * Get whether this Server's status should be displayed when invoking all
     * Servers' status from the command line or server backend.
     * 
     * @return
     */
    public boolean doShowInConsole()
    {
        Boolean bool = getBool(SHOW_IN_CONSOLE);
        if (bool == null)
        {
            return DEFAULT_SHOW_IN_CONSOLE;
        }
        return bool;
    }

    /**
     * Get whether this Server's status should be displayed when invoking all
     * Servers' status from a remote connection (web or otherwise).
     * 
     * @return
     */
    public boolean doShowInAPI()
    {
        Boolean bool = getBool(SHOW_IN_API);
        if (bool == null)
        {
            return DEFAULT_SHOW_IN_API;
        }
        return bool;
    }

}
