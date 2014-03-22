package com.indivisible.twistedserveradmin.menu.server;

import java.util.List;
import com.indivisible.twistedserveradmin.menu.base.Menu;
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

    private static final String FORMAT_SERVER_INFO = "%s   %s   %2s   %s";  // online | version | players | nick
    private static final String HEADER_SUMMARY_BLOCK = "Server summary:";

    private static final String FORMAT_NUMS = "%2d";
    private static final String FORMAT_NONE = " -";
    private static final String FORMAT_ONLINE = "Online   ";
    private static final String FORMAT_OFFLINE = "Offline  ";
    private static final String FORMAT_UNKNOWN = "Unknown  ";
    private static final String FORMAT_ERRORED = "Errored  ";
    private static final String FORMAT_PLAYERS = "%s players playing on %s of %s Servers";


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
        choices.add(getServerListHeader(), EChoiceType.header);
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
        choices.add(getServerListHeader(), EChoiceType.header);
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
        choices.add(getServerListHeader(), EChoiceType.header);
        for (MinecraftServer server : servers)
        {
            choices.add(serverInfoLine(server), EChoiceType.selectable);
        }
        return choices;
    }

    /**
     * Get a summary of current Server status and usage.
     * 
     * @param servers
     * @return
     */
    protected static Choices makeServerSummaryBlock(List<MinecraftServer> servers)
    {
        int serversOnline = 0;
        int serversOffline = 0;
        int serversUnknown = 0;
        int serversErrored = 0;
        int serversWithPlayers = 0;
        int totalPlayersOnline = 0;

        int playersOnline = 0;
        for (MinecraftServer server : servers)
        {
            switch (server.getServerStatus())
            {
                case online:
                    serversOnline++;
                    playersOnline = server.getPlayersOnline();
                    if (playersOnline > 0)
                    {
                        serversWithPlayers++;
                        totalPlayersOnline += playersOnline;
                    }
                    break;
                case offline:
                    serversOffline++;
                    break;
                case unknown:
                    serversUnknown++;
                    break;
                case error:
                    serversErrored++;
                    break;
                default:
                    break;
            }
        }
        Choices choices = new Choices();
        choices.add(HEADER_SUMMARY_BLOCK, EChoiceType.divider);
        choices.add(null, EChoiceType.blank);
        choices.add(makeSummaryLine1(serversOnline, serversUnknown),
                    EChoiceType.unselectable);
        choices.add(makeSummaryLine2(serversOffline, serversErrored),
                    EChoiceType.unselectable);
        choices.add(null, EChoiceType.blank);
        choices.add(makeSummaryLine3(totalPlayersOnline,
                                     serversWithPlayers,
                                     servers.size()),
                    EChoiceType.unselectable);
        choices.add(null, EChoiceType.blank);
        choices.add(null, EChoiceType.blank);
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

    private static String getServerListHeader()
    {
        //FORMAT_SERVER_INFO = "%s   %s   %2s   %s";
        // online | version | players | nick
        String status = "Status ";     // length 7
        String version = "Ver  ";    // length 5
        String players = "Pl";    // length 2
        String nickname = "Name";    // length any
        return String.format(FORMAT_SERVER_INFO, status, version, players, nickname);
    }

    private static String makeSummaryLine1(int serversOnline, int serversUnknown)
    {
        //FORMAT_SUMMARY_LINE1 = "Online:   %2d      Unknown:  %2d";
        StringBuilder sb = new StringBuilder();
        sb.append(Menu.ANSI_WHITE).append(FORMAT_ONLINE);
        if (serversOnline > 0)
            sb.append(Menu.ANSI_GREEN).append(String.format(FORMAT_NUMS, serversOnline));
        else
            sb.append(Menu.ANSI_BLUE).append(FORMAT_NONE);
        sb.append(Menu.PADDING_SMALL);
        sb.append(Menu.ANSI_WHITE).append(FORMAT_UNKNOWN);
        if (serversUnknown > 0)
            sb.append(Menu.ANSI_YELLOW)
                    .append(String.format(FORMAT_NUMS, serversUnknown));
        else
            sb.append(Menu.ANSI_BLUE).append(FORMAT_NONE);
        sb.append(Menu.ANSI_RESET);
        return sb.toString();
    }

    private static String makeSummaryLine2(int serversOffline, int serversErrored)
    {
        //FORMAT_SUMMARY_LINE2 = "Offline:  %2d      Errored:  %2d";
        StringBuilder sb = new StringBuilder();
        sb.append(Menu.ANSI_WHITE).append(FORMAT_OFFLINE);
        if (serversOffline > 0)
            sb.append(Menu.ANSI_PURPLE)
                    .append(String.format(FORMAT_NUMS, serversOffline));
        else
            sb.append(Menu.ANSI_BLUE).append(FORMAT_NONE);
        sb.append(Menu.PADDING_SMALL);
        sb.append(Menu.ANSI_WHITE).append(FORMAT_ERRORED);
        if (serversErrored > 0)
            sb.append(Menu.ANSI_RED).append(String.format(FORMAT_NUMS, serversErrored));
        else
            sb.append(Menu.ANSI_BLUE).append(FORMAT_NONE);
        sb.append(Menu.ANSI_RESET);
        return sb.toString();
    }

    private static String makeSummaryLine3(int totalPlayersOnline,
                                           int serversWithPlayers,
                                           int totalServers)
    {
        String YELL = Menu.ANSI_YELLOW;
        String CYAN = Menu.ANSI_CYAN;

        //FORMAT_SUMMARY_LINE3 = "%2d players playing on %2d of %2d Servers";
        String playingNow = YELL + totalPlayersOnline + CYAN;
        String occupiedServers = YELL + serversWithPlayers + CYAN;
        String numServers = YELL + totalServers + CYAN;
        return String.format(FORMAT_PLAYERS, playingNow, occupiedServers, numServers);
    }
}
