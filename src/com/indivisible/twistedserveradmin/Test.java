package com.indivisible.twistedserveradmin;

import java.io.IOException;
import com.indivisible.twistedserveradmin.files.ServerCollector;
import com.indivisible.twistedserveradmin.servers.Server;
import com.indivisible.twistedserveradmin.servers.ServerProperties;


public class Test
{

    public static void main(String[] args)
    {
        //testProperties();
        testServerCollection();
    }

    private static void testServerCollection()
    {
        ServerCollector collector = new ServerCollector(ServerCollector.TEST_ROOTS_PATH);
        for (Server server : collector.getServers())
        {
            ServerProperties props = server.getProperties();
            if (props.hasSettings())
            {
                System.out.println(server.getProperties().getServerName());
            }
            else
            {
                System.out.println("No props");
            }
        }
    }

    public static void testProperties()
    {
        String testFile = "/home/indiv/dev/twisted/Minecraft/ServerLoop/SERVERS/Minecraft01/server.properties";

        try
        {
            ServerProperties spr = new ServerProperties(testFile);
            System.out.println(spr.getServerName());
            System.out.println(spr.getCleanMOTD());
            System.out.println(spr.getIP());
            System.out.println(spr.getPort());
            System.out.println(spr.isQueryEnabled());
            System.out.println(spr.getQueryPort());
            System.out.println(spr.getSeed());
            System.out.println(spr.isOnlineEnabled());
            System.out.println(spr.isWhitelisted());
            System.out.println(spr.isPVPEnabled());
        }
        catch (IOException e)
        {
            System.out.println("IOException caught in main thread for file: " + testFile);
        }
    }

}
