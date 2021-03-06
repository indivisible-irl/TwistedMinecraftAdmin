package com.indivisible.twistedserveradmin.commands;

import java.util.List;
import com.indivisible.twistedserveradmin.query.MinecraftServerQuery;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.servers.ServerManager;


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
    private static final String LIST_SERVER_TITLE = "  %s  ::  %s";
    private static final String LIST_SERVER_ONLINE = "             %d / %d";

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
        ServerManager servers = new ServerManager();
        if (servers.hasServers())
        {
            //String lastVersion = "";
            MinecraftServerQuery query;
            System.out.println("Servers:");
            for (MinecraftServer server : servers.getServersAll())
            {
                int port = server.getPort();
                String motd = server.getMOTD();
                System.out.println(String.format(LIST_SERVER_TITLE, port, motd));
                if (server.performQuery())
                {
                    query = server.getQuery();
                    int players = query.getPlayersOnline();
                    int max = query.getMaxPlayers();
                    System.out.println(String.format(LIST_SERVER_ONLINE, players, max));
                }
                else
                {
                    System.out.println("             ---");
                }
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
