package com.indivisible.twistedserveradmin.menu.server;

import java.util.List;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.servers.ServerStatus;

/**
 * Single class to supply common static methods for ServerMenu utilisation.
 * 
 * @author indiv
 */
public class ServerListUtil
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String FORMAT_SERVER_INFO = "%s | %s | %2s | %s";  // online | version | players | nick


    ///////////////////////////////////////////////////////
    ////    choices splitting / header
    ///////////////////////////////////////////////////////

    /**
     * Get a printable listing of Servers split by version. <br />
     * Doesn't perform sort in case calling methods rely on current ordering.
     * 
     * @param servers
     * @return
     */
    protected static Choices makeVersionSplitChoices(List<MinecraftServer> servers)
    {
        String previousVersion = "";
        String currentVersion = "";
        Choices choices = new Choices();
        for (MinecraftServer server : servers)
        {
            currentVersion = server.getVersion();
            if (!currentVersion.equals(previousVersion))
            {
                previousVersion = currentVersion;
                choices.add(currentVersion, EChoiceType.divider);
            }
            choices.add(serverInfoLine(server), EChoiceType.selectable);
        }
        return choices;
    }

    /**
     * Get a printable listing of Servers split by Online status. <br />
     * Doesn't perform sort in case calling methods rely on current ordering.
     * 
     * @param servers
     * @return
     */
    protected static Choices makeStatusSplitChoices(List<MinecraftServer> servers)
    {
        String previousStatus = "";
        String currentStatus = "";
        Choices choices = new Choices();
        for (MinecraftServer server : servers)
        {
            currentStatus = server.getServerStatus().toString();
            if (!currentStatus.equals(previousStatus))
            {
                previousStatus = currentStatus;
                choices.add(currentStatus, EChoiceType.divider);
            }
            choices.add(serverInfoLine(server), EChoiceType.selectable);
        }
        return choices;
    }

    /**
     * Get a printable listing of Servers without any splitting. <br />
     * Doesn't perform any sort in case calling methods rely on current
     * ordering.
     * 
     * @param servers
     * @return
     */
    protected static Choices makeNoSplitChoices(List<MinecraftServer> servers)
    {
        Choices choices = new Choices();
        for (MinecraftServer server : servers)
        {
            choices.add(serverInfoLine(server), EChoiceType.selectable);
        }
        return choices;
    }

    ///////////////////////////////////////////////////////
    ////    string methods
    ///////////////////////////////////////////////////////

    /**
     * Get a standardised line with Server's info.
     * 
     * @param server
     * @return
     */
    private static String serverInfoLine(MinecraftServer server)
    {
        String serverStatus = ServerStatus.getPrintable(server.getServerStatus(),   // status 
                                                        true,                       // fixed width
                                                        true);                      // use colours
        String serverVersion = server.getVersion();
        int playersOnline = server.getPlayersOnline();
        String strPlayersOnline = "";
        if (playersOnline < 0)
        {
            strPlayersOnline = "  ";
        }
        else if (playersOnline == 0)
        {
            strPlayersOnline = "-";
        }
        else
        {
            strPlayersOnline = new Integer(playersOnline).toString();
        }
        String serverName = server.getName();
        return String.format(FORMAT_SERVER_INFO,
                             serverStatus,
                             serverVersion,
                             strPlayersOnline,
                             serverName);
    }
}
