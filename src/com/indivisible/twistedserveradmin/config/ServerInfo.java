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
    private static final String STARTUP_SCRIPT = "startup-script-name";
    private static final String AUTOSTART = "autostart";
    private static final String RESTART = "auto-restart";
    private static final String SHOW_BACKEND = "show-in-backend";
    private static final String SHOW_API = "show-in-api";

    //ASK save java parameters in server.info?
    //ANS no. let's make an improved start.sh for easier manual start too.

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
        return getString(NICKNAME);
    }

    /**
     * Retrieve the Minecraft version this server is based on. Reads from
     * config file NOT from server instance. May be inaccurate.
     * 
     * @return
     */
    public String getVersion()
    {
        return getString(VERSION);
    }

    /**
     * Get the custom name for the startup file for the Server.
     * 
     * @return
     */
    public String getStartupScriptName()
    {
        return getString(STARTUP_SCRIPT);
    }

    /**
     * Get whether this Server should be started when doing a batch startup.
     * 
     * @return
     */
    public Boolean doAutostart()
    {
        return getBool(AUTOSTART);
    }

    /**
     * Get whether this Server should be restarted (or attempted to) if found
     * offline.
     * 
     * @return
     */
    public Boolean doRestart()
    {
        return getBool(RESTART);
    }

    /**
     * Get whether this Server's status should be displayed when invoking all
     * Servers' status from the command line or server backend.
     * 
     * @return
     */
    public Boolean doShowInBackend()
    {
        return getBool(SHOW_BACKEND);
    }

    /**
     * Get whether this Server's status should be displayed when invoking all
     * Servers' status from a remote connection (web or otherwise).
     * 
     * @return
     */
    public Boolean doShowInAPI()
    {
        return getBool(SHOW_API);
    }

}
