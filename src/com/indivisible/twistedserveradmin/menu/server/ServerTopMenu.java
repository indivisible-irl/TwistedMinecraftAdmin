package com.indivisible.twistedserveradmin.menu.server;

import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.base.MenuInputInteger;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.servers.ServerManager;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Root Menu for Server actions.
 * 
 * @author indiv
 */
public class ServerTopMenu
        extends MenuInputInteger
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String title = "SERVER MENU";
    private static final String description = null;
    private static final String question = "Select from one of the following actions";
    private static final String prompt = "Enter a number";

    private ServerManager serverManager;

    private static final String TAG = "MenuInputInteger";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new ServerTopMenu. The root Menu for Server actions.
     */
    public ServerTopMenu(ServerManager serverManager)
    {
        super(title, description, question, prompt, makeChoices());
        this.serverManager = serverManager;
    }

    /**
     * Initialise the ServerTopMenu's Choices
     * 
     * @return
     */
    private static Choices makeChoices()
    {
        //REM: Set MainMenu entries here.
        Choices choices = new Choices();                                                    // 0. go back
        choices.add("List default  - list watching servers (set in server.info)",
                    EChoiceType.selectable);                                                // 1. list default
        choices.add("List all      - show status of all servers", EChoiceType.selectable);  // 2. list all
        choices.add("List status   - show status of all servers", EChoiceType.selectable);  // 3. list online
        choices.add("Start  - start an offline server", EChoiceType.selectable);            // 4. start
        choices.add("Stop   - stop an online server", EChoiceType.selectable);              // 5. stop
        choices.add("Kill   - forcibly kill a server, USE CAUTION",
                    EChoiceType.selectable);                                                // 6. kill
        choices.add("Backup - make a backup of a server.", EChoiceType.selectable);         // 7. backup
        return choices;
    }


    ///////////////////////////////////////////////////////
    ////    Menu methods
    ///////////////////////////////////////////////////////

    public Menu invoke()
    {
        super.invoke();

        switch (super.getResponse())
        {
            case 0:
                // ..
                Main.myLog.verbose(TAG, "-- Go back...");
                return null;
            case 1:
                // List default/watching
                return makeServerListMonitoredMenu();
            case 2:
                // List all
                return makeServerListAllMenu();
            case 3:
                // List online
                return makeServerListStatusMenu();
            case 4:
                // Start
                return makeServerStartMenu();
            case 5:
                // Stop
                return makeServerStopMenu();
            case 6:
                // Kill
                return makeServerKillMenu();
            case 7:
                // Backup
                return makeServerBackupMenu();
            default:
                Main.myLog.warning(TAG, "Switch - default: " + super.getResponse());
                return null;
        }
    }


    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    private ServerListMenu makeServerListMonitoredMenu()
    {
        return new ServerListMenu(serverManager, EServerListType.monitored);
    }

    private ServerListMenu makeServerListAllMenu()
    {
        return new ServerListMenu(serverManager, EServerListType.all);
    }

    private ServerListMenu makeServerListStatusMenu()
    {
        return new ServerListMenu(serverManager, EServerListType.status);
    }

    private Menu makeServerStartMenu()
    {
        //TODO: ServerStartMenu
        Main.myLog.error(TAG, "Server starting not yet ready for use");
        return null;
    }

    private Menu makeServerStopMenu()
    {
        //TODO: ServerStopMenu
        Main.myLog.error(TAG, "Server stopping not yet ready for use");
        return null;
    }

    private Menu makeServerKillMenu()
    {
        //TODO: ServerKillMenu
        Main.myLog.error(TAG, "Server killing not yet ready for use");
        return null;
    }

    private Menu makeServerBackupMenu()
    {
        //TODO: ServerBackupMenu
        Main.myLog.error(TAG, "Server backup not yet ready for use");
        return null;
    }

}
