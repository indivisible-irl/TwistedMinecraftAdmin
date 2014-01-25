package com.indivisible.twistedserveradmin.commands;

import java.util.List;
import com.indivisible.twistedserveradmin.servers.Server;
import com.indivisible.twistedserveradmin.servers.ServerCollector;


public class ListCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "list";

    private static final String HELP_SHORT = "Lists all available server instances (optional filter)";
    private static final String HELP_LONG = "Usage :: admin list [string]\n"
            + "    Displays a list of all available nicks.\n"
            + "    [string] parameter is optional and used to filter results";
    private static final String ERROR_NO_SERVERS = "!! No servers found. Check your 'servers.list' file.";

    //// constructor

    public ListCmd()
    {}


    //// Command methods

    public String getName()
    {
        return NAME;
    }

    public boolean matchName(String test)
    {
        return NAME.equals(test);
    }

    public String getShortHelp()
    {
        return HELP_SHORT;
    }

    public String getLongHelp(List<String> args)
    {
        return HELP_LONG;
    }


    //// invoke

    public boolean invoke(List<String> args)
    {
        //TODO: branch based on args
        ServerCollector servers = new ServerCollector();
        if (servers.hasServers())
        {
            //String lastVersion = "";
            System.out.println("Servers:");
            for (Server server : servers.getServers())
            {
                System.out.println("  - " + server.getProperties().getCleanMOTD());
            }
            return true;
        }
        else
        {
            System.out.println(ERROR_NO_SERVERS);
            return false;
        }
    }

}
