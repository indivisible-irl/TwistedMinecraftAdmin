package com.indivisible.twistedserveradmin.menu.server;

import java.util.List;
import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.base.MenuInputInteger;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.servers.ServerManager;
import com.indivisible.twistedserveradmin.servers.ServerSort;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Abstract class to display a listing of Servers in a Menu
 * 
 * @author indiv
 * 
 */
public class ServerListMenu
        extends MenuInputInteger
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static List<MinecraftServer> servers = null;

    // common
    private static final String question = "Select a Server for extended information";
    private static final String prompt = "Select a Server or blank to return";

    // all servers
    private static final String ALL_title = "Server List - ALL Servers";
    private static final String ALL_description = null;
    // monitored servers
    private static final String MON_title = "Server List - MONITORED for extended information";
    private static final String MON_description = null;
    // status servers
    private static final String STA_title = "Server List - Servers' Status";
    private static final String STA_description = null;


    private static final String TAG = "ServerListMenu";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Base class for a Menu display of Servers. <br />
     * Children must Override makeChoices(...)
     * 
     * @param title
     * @param description
     * @param question
     * @param prompt
     * @param choices
     */
    protected ServerListMenu(ServerManager serverManager, EServerListType listType)
    {
        super(getTitle(listType), getDescription(listType), question, prompt,
                getChoices(listType, serverManager));
    }


    ///////////////////////////////////////////////////////
    ////    menu methods
    ///////////////////////////////////////////////////////

    /**
     * Menu Method, invoke the display, User input and result parsing.
     */
    public Menu invoke()
    {
        super.invoke();

        int response = super.getResponse();
        if (response == 0)
        {
            // go back
            Main.myLog.debug(TAG, "Go back...");
        }
        else if (response == Integer.MIN_VALUE || response < 0)
        {
            // error on input
            Main.myLog.warning(TAG, "Incorrect user input (go back)");
        }
        else
        {
            if (response > servers.size() + 1)
            {
                super.printErrorInput("There is no Server with that number, please try again.");
            }
            else
            {
                try
                {
                    return new ServerExtendedInfo(servers.get(response - 1));
                }
                catch (IndexOutOfBoundsException e)
                {
                    Main.myLog.error(TAG, "Failed to get Server index " + (response - 1)
                            + " from " + servers.size());
                    super.printErrorInput("Failed to get a server with that number,\n\tplease try again or contact the dev if this is a bug");
                }
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////
    ////    List field choices
    ///////////////////////////////////////////////////////

    /**
     * Get required title based on desired ServerListType
     * 
     * @param listType
     * @return
     */
    private static String getTitle(EServerListType listType)
    {
        switch (listType)
        {
            case all:
                return ALL_title;
            case monitored:
                return MON_title;
            case status:
                return STA_title;
            default:
                throw new IllegalStateException(
                        "That EServerListType was not handled correctly: "
                                + listType.toString());
        }
    }

    /**
     * Get required description based on desired ServerListType
     * 
     * @param listType
     * @return
     */
    private static String getDescription(EServerListType listType)
    {
        switch (listType)
        {
            case all:
                return ALL_description;
            case monitored:
                return MON_description;
            case status:
                return STA_description;
            default:
                throw new IllegalStateException(
                        "That EServerListType was not handled correctly: "
                                + listType.toString());
        }
    }

    /**
     * Get required Choices based on desired ServerListType
     * 
     * @param listType
     * @param serverManager
     * @return
     */
    private static Choices getChoices(EServerListType listType,
                                      ServerManager serverManager)
    {
        serverManager.refreshServerList();
        serverManager.refreshServerQueries();
        switch (listType)
        {
            case all:
                return getChoicesAll(serverManager);
            case monitored:
                return getChoicesMonitored(serverManager);
            case status:
                return getChoicesStatus(serverManager);
            default:
                throw new IllegalStateException(
                        "That EServerListType was not handled correctly: "
                                + listType.toString());
        }
    }


    ///////////////////////////////////////////////////////
    ////    choice generation
    ///////////////////////////////////////////////////////

    /**
     * Get Choices for ALL Servers sorted and split by version.
     * 
     * @param serverManager
     * @return
     */
    private static Choices getChoicesAll(ServerManager serverManager)
    {
        servers = serverManager.getServersAll();
        servers = ServerSort.versionSort(servers);
        Choices choices = ServerListUtil.makeServerSummaryBlock(servers);
        choices.add(ServerListUtil.makeVersionSplitChoices(servers));
        return choices;
    }

    /**
     * Get Choices for MONITORED Servers sorted and split by version.
     * 
     * @param serverManager
     * @return
     */
    private static Choices getChoicesMonitored(ServerManager serverManager)
    {
        servers = serverManager.getServersMonitored();
        servers = ServerSort.versionSort(servers);
        Choices choices = ServerListUtil.makeServerSummaryBlock(servers);
        choices.add(ServerListUtil.makeVersionSplitChoices(servers));
        return choices;
    }

    /**
     * Get Choices for ALL Servers sorted and split by status.
     * 
     * @param serverManager
     * @return
     */
    private static Choices getChoicesStatus(ServerManager serverManager)
    {
        servers = serverManager.getServersAll();
        servers = ServerSort.statusSort(servers);
        Choices choices = ServerListUtil.makeServerSummaryBlock(servers);
        choices.add(ServerListUtil.makeStatusSplitChoices(servers));
        return choices;
    }


}
