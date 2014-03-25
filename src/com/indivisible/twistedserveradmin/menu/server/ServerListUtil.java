package com.indivisible.twistedserveradmin.menu.server;

import java.util.List;
import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.servers.ServerStatus;
import com.indivisible.twistedserveradmin.system.Main;
import com.indivisible.twistedserveradmin.util.StringColor;
import com.indivisible.twistedserveradmin.util.StringColor.Color;

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

    private static final Color COLOR_FIELD = Color.WHITE;
    private static final Color COLOR_NO_NUM = Color.BLUE;
    private static final Color COLOR_OK = Color.GREEN;
    private static final Color COLOR_WARN = Color.YELLOW;
    private static final Color COLOR_ERROR = Color.RED;
    private static final Color COLOR_PLAIN = Color.CYAN;
    private static final Color COLOR_NUM = Color.YELLOW;

    private static final String FORMAT_SERVER_INFO = "%s   %s   %2s   %s";  // online | version | players | nick
    private static final String FORMAT_NUMS = "%2d";

    private static final String HEADER_SUMMARY_BLOCK = "Server summary:";
    private static final String NO_NUMBER = " -";
    private static final String FIELD_ONLINE = "Online   ";
    private static final String FIELD_OFFLINE = "Offline  ";
    private static final String FIELD_UNKNOWN = "Unknown  ";
    private static final String FIELD_ERRORED = "Errored  ";

    private static final String TAG = "ServerListUtil";


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
                    Main.myLog.error(TAG, "Uncaught serverState: " + server.getName()
                            + " || " + server.getServerStatus().name());
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
     * Get a standardised line with Server's info. <br />
     * Format: "online | version | players | nickname"
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

    /**
     * Get a header line for display above serverInfoLine(). <br />
     * Format: "online | version | players | nickname"
     * 
     * @return
     */
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

    /**
     * Create the first line of the summary of current Server usage. <br />
     * Format: "Online:   %2d" + PADDING_SMALL + "Unknown:  %2d"
     * 
     * @param serversOnline
     * @param serversUnknown
     * @return
     */
    private static String makeSummaryLine1(int serversOnline, int serversUnknown)
    {
        //FORMAT_SUMMARY_LINE1 = "Online:   %2d      Unknown:  %2d";
        StringBuilder sb = new StringBuilder();
        sb.append(colorField(FIELD_ONLINE));
        sb.append(colorNumOk(serversOnline));
        sb.append(Menu.PADDING_SMALL);
        sb.append(colorField(FIELD_UNKNOWN));
        sb.append(colorNumError(serversUnknown));
        return sb.toString();
    }

    /**
     * Create the second line of the summary of current Server usage. <br />
     * Format: "Offline:  %2d" + PADDING_SMALL + "Errored:  %2d"
     * 
     * @param serversOffline
     * @param serversErrored
     * @return
     */
    private static String makeSummaryLine2(int serversOffline, int serversErrored)
    {
        //FORMAT_SUMMARY_LINE2 = "Offline:  %2d      Errored:  %2d";
        StringBuilder sb = new StringBuilder();
        sb.append(colorField(FIELD_OFFLINE));
        sb.append(colorNumWarn(serversOffline));
        sb.append(Menu.PADDING_SMALL);
        sb.append(colorField(FIELD_ERRORED));
        sb.append(colorNumError(serversErrored));
        return sb.toString();
    }

    /**
     * Create the third line of the summary of current Server usage. <br />
     * Format: "%d players playing on %d of %d Servers"
     * 
     * @param totalPlayersOnline
     * @param serversWithPlayers
     * @param totalServers
     * @return
     */
    private static String makeSummaryLine3(int totalPlayersOnline,
                                           int serversWithPlayers,
                                           int totalServers)
    {
        //FORMAT_SUMMARY_LINE3 = "%d players playing on %d of %d Servers";
        StringBuilder sb = new StringBuilder();
        sb.append(StringColor.format(totalPlayersOnline + "", COLOR_NUM));
        sb.append(StringColor.format(" players playing on ", COLOR_PLAIN));
        sb.append(StringColor.format(serversWithPlayers + "", COLOR_NUM));
        sb.append(StringColor.format(" of ", COLOR_PLAIN));
        sb.append(StringColor.format(totalServers + "", COLOR_NUM));
        sb.append(StringColor.format(" Servers", COLOR_PLAIN));
        return sb.toString();
    }

    /**
     * Colour a Field name.
     * 
     * @param text
     * @return
     */
    private static String colorField(String text)
    {
        return StringColor.format(text, COLOR_FIELD);
    }

    /**
     * Colour a number. Bigger is better.
     * 
     * @param num
     * @return
     */
    private static String colorNumOk(int num)
    {
        return colorNum(num, COLOR_OK);
    }

    /**
     * Colour a number. Increasing value could be an issue.
     * 
     * @param num
     * @return
     */
    private static String colorNumWarn(int num)
    {
        return colorNum(num, COLOR_WARN);
    }

    /**
     * Colour a number. Any value could be a problem.
     * 
     * @param num
     * @return
     */
    private static String colorNumError(int num)
    {
        return colorNum(num, COLOR_ERROR);
    }

    /**
     * Colour a number with the supplied Color if 'num > 0'. <br />
     * Otherwise return a ' -'
     * 
     * @param num
     * @param color
     * @return
     */
    private static String colorNum(int num, Color color)
    {
        if (num > 0)
        {
            return StringColor.format(String.format(FORMAT_NUMS, num), color);
        }
        else
        {
            return StringColor.format(NO_NUMBER, COLOR_NO_NUM);
        }
    }


}
