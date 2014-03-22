package com.indivisible.twistedserveradmin.menu.common;

import com.indivisible.twistedserveradmin.Test;
import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.base.MenuInputInteger;
import com.indivisible.twistedserveradmin.menu.screen.ScreenTopMenu;
import com.indivisible.twistedserveradmin.menu.server.ServerTopMenu;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.servers.ServerManager;
import com.indivisible.twistedserveradmin.system.Main;


/**
 * Class to display a top level choice of functionality to invoke.
 * 
 * @author indiv
 */
public class MainMenu
        extends MenuInputInteger
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String title = "MAIN MENU";
    private static final String description = "Welcome to indivisible's Minecraft Admin Tool built for Twisted.cat's servers.";
    private static final String question = "Select from one of the following groups";
    private static final String prompt = "Enter a number";

    private static final String TAG = "MainMenu";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new MainMenu
     */
    public MainMenu()
    {
        super(title, description, question, prompt, makeChoices());
    }

    /**
     * Initialise the MainMenu's Choices
     * 
     * @return
     */
    private static Choices makeChoices()
    {
        //REM: Set MainMenu entries here.
        Choices choices = new Choices();
        choices.add("Server Actions - list, status, start, stop, backup etc",
                    EChoiceType.selectable);                                    // 1. status, start, stop, backup
        choices.add("Screen Actions - list, attach, detach, kill etc",
                    EChoiceType.selectable);                                    // 2. list, attach, detach, kill
        choices.add("Player Queries - activity, homes, plrFile admin etc",
                    EChoiceType.selectable);                                    // 3. last active, online where, location, homes, swap plrFiles,
        choices.add("AdminTool Help - get help for command line usage",
                    EChoiceType.selectable);                                    // 4. short help leading to extended
        choices.add("TEST (indev). Could do anything - be careful!",
                    EChoiceType.selectable);                                    // 5. testing option.
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
                // Server Menu
                return makeServerTopMenu();
            case 2:
                // Screen Menu
                return makeScreenTopMenu();
            case 3:
                // Player Menu
                return makePlayerTopMenu();
            case 4:
                // Help Menu
                return makeHelpTopMenu();
            case 5:
                // TEST
                System.out.println("==== Testing... ====");
                Test.main(null);
                return null;
            default:
                Main.myLog.warning(TAG, "Switch - default: " + super.getResponse());
                return null;
        }
    }

    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    private Menu makeServerTopMenu()
    {
        Main.myLog.verbose(TAG, "Server menu...");
        return new ServerTopMenu(new ServerManager());
    }

    private Menu makeScreenTopMenu()
    {
        Main.myLog.verbose(TAG, "Screen menu...");
        return new ScreenTopMenu();
    }

    private Menu makePlayerTopMenu()
    {
        Main.myLog.verbose(TAG, "Player menu...");
        return null;
    }

    private Menu makeHelpTopMenu()
    {
        Main.myLog.verbose(TAG, "Help menu...");
        return null;
    }
}
