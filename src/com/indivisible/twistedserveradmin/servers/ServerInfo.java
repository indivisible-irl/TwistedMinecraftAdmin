package com.indivisible.twistedserveradmin.servers;

import java.io.IOException;


public class ServerInfo
        extends ServerSettings
{

    //// data

    private static final String NICKNAME = "nickname";
    private static final String AUTOSTART = "autostart";
    private static final String SHOW_BACKEND = "show-in-backend";
    private static final String SHOW_WEB = "show-in-web";


    //// constructor & init

    public ServerInfo(String infoFilePath) throws IOException
    {
        super(infoFilePath);
    }


    //// public methods

    public String getNickname()
    {
        return getRawProperty(NICKNAME);
    }

    public Boolean doAutostart()
    {
        return getBool(AUTOSTART);
    }

    public Boolean doShowInBackend()
    {
        return getBool(SHOW_BACKEND);
    }

    public Boolean doShowInWeb()
    {
        return getBool(SHOW_WEB);
    }

}
