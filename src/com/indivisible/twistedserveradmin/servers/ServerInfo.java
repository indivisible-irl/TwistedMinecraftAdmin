package com.indivisible.twistedserveradmin.servers;

import java.io.IOException;

/**
 * Class to access Server's server.info settings
 * 
 * @author indiv
 * 
 */
public class ServerInfo
        extends ServerSettings
{

    //// data

    private static final String NICKNAME = "nickname";
    private static final String AUTOSTART = "autostart";
    private static final String RESTART = "auto-restart";
    private static final String SHOW_BACKEND = "show-in-backend";
    private static final String SHOW_WEB = "show-in-web";

    //ASK save java parameters in server.info?
    // make bash start command here?

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
        return getRawProperty(NICKNAME);
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
    public Boolean doShowInWeb()
    {
        return getBool(SHOW_WEB);
    }

}
