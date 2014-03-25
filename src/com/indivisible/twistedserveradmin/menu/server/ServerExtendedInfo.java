package com.indivisible.twistedserveradmin.menu.server;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.base.MenuDisplay;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.util.StringColor;
import com.indivisible.twistedserveradmin.util.StringColor.Color;


/**
 * Simple Menu for displaying more in depth information on a Server.
 * 
 * @author indiv
 */
public class ServerExtendedInfo
        extends MenuDisplay
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String title = "Minecraft Server";
    private static final String description = null;

    private static String FORMAT_INFO = Menu.PADDING_LARGE + "%15s" + Menu.PADDING_SMALL
            + "%s";
    private static String FORMAT_FIELD = "%15s";
    private static Color COLOR_FIELD = Color.CYAN;

    private static String FIELD_LOCATION = "Location";

    private static String FIELD_MOTD = "Server MOTD";
    private static String FIELD_VERSION = "MC Version";
    private static String FIELD_SERVER_STATUS = "Status";
    private static String FIELD_LAST_QUERIED = "Last Query";
    private static String FIELD_PLAYERS_ONLINE = "Players";

    private static String FIELD_IP = "Server IP";
    private static String FIELD_PORT = "Server Port";
    private static String FIELD_WHITELIST = "Whitelist?";
    private static String FIELD_PVP = "PvP enabled?";
    private static String FIELD_DIFFICULTY = "Difficulty";

    private static String FIELD_PROPS = "Properties?";
    private static String FIELD_INFO = "Info?";
    private static String FIELD_STARTUP = "Startup Script";

    private static String FIELD_MONITORED = "Monitored?";
    private static String FIELD_BATCH_START = "Batch Start?";
    private static String FIELD_BATCH_STOP = "Batch Stop?";
    private static String FIELD_AUTO_RESTART = "Auto Restart?";


    ///////////////////////////////////////////////////////
    ////    constructor
    ///////////////////////////////////////////////////////

    /**
     * Make a new Menu with extended information about a Server.
     * 
     * @param server
     */
    public ServerExtendedInfo(MinecraftServer server)
    {
        super(title, description, true);
        super.setContent(makeServerInfoContent(server));
    }


    ///////////////////////////////////////////////////////
    ////    menu methods
    ///////////////////////////////////////////////////////

    @Override
    public Menu invoke()
    {
        super.invoke();
        return null;
    }


    ///////////////////////////////////////////////////////
    ////    content generation
    ///////////////////////////////////////////////////////

    /**
     * Generate the informational content for display
     * 
     * @param server
     * @return
     */
    private static List<String> makeServerInfoContent(MinecraftServer server)
    {
        List<String> lines = new ArrayList<String>();

        lines.add("");
        lines.add(getSubtitle(server.getName()));
        lines.add("");

        lines.add(infoLine(FIELD_LOCATION, server.getServerPath()));
        lines.add("");

        lines.add(infoLine(FIELD_MOTD, server.getMOTD()));
        lines.add(infoLine(FIELD_VERSION, server.getVersion()));
        lines.add(infoLine(FIELD_SERVER_STATUS, server.getServerStatus().toString()));
        lines.add(infoLine(FIELD_LAST_QUERIED, getLastQuery(server)));
        lines.add(infoLine(FIELD_PLAYERS_ONLINE, getPlayersOnline(server)));
        lines.add("");

        lines.add(infoLine(FIELD_IP, server.getIP()));
        lines.add(infoLine(FIELD_PORT, new Integer(server.getPort()).toString()));
        lines.add(infoLine(FIELD_WHITELIST, getYesNo(server.isWhitelist())));
        lines.add(infoLine(FIELD_PVP, getYesNo(server.isPvP())));
        lines.add(infoLine(FIELD_DIFFICULTY, getDifficulty(server)));
        lines.add("");

        lines.add(infoLine(FIELD_PROPS, getYesNo(server.hasProperties())));
        lines.add(infoLine(FIELD_INFO, getYesNo(server.hasInfo())));
        lines.add(infoLine(FIELD_STARTUP, server.getStartupScriptFile().getName()));
        lines.add("");

        lines.add(infoLine(FIELD_MONITORED, getYesNo(server.isMonitoredServer())));
        lines.add(infoLine(FIELD_BATCH_START, getYesNo(server.doBatchStart())));
        lines.add(infoLine(FIELD_BATCH_STOP, getYesNo(server.doBatchStop())));
        lines.add(infoLine(FIELD_AUTO_RESTART, getYesNo(server.doRestart())));
        lines.add("");

        return lines;
    }

    /**
     * Make the intro line containing the Server's name.
     * 
     * @param name
     * @return
     */
    private static String getSubtitle(String name)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying info for ");
        sb.append(StringColor.format(name, Color.BLUE));
        sb.append(":");
        return sb.toString();
    }

    /**
     * Make a standardised info line, two column, centre justified.
     * 
     * @param fieldName
     * @param value
     * @return
     */
    private static String infoLine(String fieldName, String value)
    {
        String fieldLabel = StringColor.format(String.format(FORMAT_FIELD, fieldName),
                                               COLOR_FIELD);
        return String.format(FORMAT_INFO, fieldLabel, value);
    }

    /**
     * Make a printable representation of the age of the last performed
     * ServerQuery.
     * 
     * @param server
     * @return
     */
    private static String getLastQuery(MinecraftServer server)
    {
        long lastQuery;
        try
        {
            lastQuery = DateTime.now().getMillis() - server.getQuery().getTimeLastRun();
        }
        catch (NullPointerException e)
        {
            return "---";
        }
        //TODO: Move printable age to DateTimeUtil (add mins, hrs etc to it also)
        double queryAgeSecs = (double) lastQuery / 1000.0;
        return String.format("%.1f secs ago", queryAgeSecs);
    }

    /**
     * Get a printable representation of the current players online.
     * 
     * @param server
     * @return
     */
    private static String getPlayersOnline(MinecraftServer server)
    {
        int playersOnline = server.getPlayersOnline();
        if (playersOnline < 0)
        {
            return "--";
        }
        else
        {
            int maxPlayers = server.getMaxPlayers();
            return String.format("%d of %d", playersOnline, maxPlayers);
        }
    }

    /**
     * Get a textual representation of the Server's assigned difficulty.
     * 
     * @param server
     * @return
     */
    private static String getDifficulty(MinecraftServer server)
    {
        int difficulty = server.getDifficulty();
        switch (difficulty)
        {
            case 0:
                return "Peaceful";
            case 1:
                return "Easy";
            case 2:
                return "Normal";
            case 3:
                return "Hard";
            default:
                return "unknown";
        }
    }

    /**
     * Make a Boolean value more readable. <br />
     * TODO: Move to StringUtil
     * 
     * @param value
     * @return
     */
    private static String getYesNo(Boolean value)
    {
        if (value == null)
        {
            return "unknown";
        }
        else if (value)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }
}
