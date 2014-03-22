package com.indivisible.twistedserveradmin.menu.screen;

import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.base.MenuInputInteger;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Root Menu for Linux Screen actions.
 * 
 * @author indiv
 */
public class ScreenTopMenu
        extends MenuInputInteger
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String title = "SCREEN ACTIONS MENU";
    private static final String description = "Perform a variety of actions on Linux 'screen's";
    private static final String question = "Select from one of the following actions";
    private static final String prompt = "Enter a number";

    private static final String TAG = "ScreenTopMenu";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Make a new Screen Root Menu
     */
    public ScreenTopMenu()
    {
        super(title, description, question, prompt, makeChoices());
    }

    /**
     * Create the Menu's options.
     * 
     * @return
     */
    private static Choices makeChoices()
    {
        Choices choices = new Choices();                                                    // 0 - back
        choices.add("List   - List running screens and their states",
                    EChoiceType.selectable);                                                // 1 - list
        choices.add("New    - Start and attach to a new screen", EChoiceType.selectable);   // 2 - new
        choices.add("Attach - Attach to a running screen", EChoiceType.selectable);         // 3 - attach
        choices.add("Detach - Detach all users from a running screen",
                    EChoiceType.selectable);                                                // 4 - detach
        choices.add("Kill   - Forcebly kill a running screen. USE CAUTION!",
                    EChoiceType.selectable);                                                // 5 - kill
        return choices;
    }


    ///////////////////////////////////////////////////////
    ////    menu methods
    ///////////////////////////////////////////////////////

    @Override
    public Menu invoke()
    {
        super.invoke();

        switch (super.getResponse())
        {
            case 0:
                // go back
                Main.myLog.verbose(TAG, "Go back...");
                return null;
            case 1:
                // list
                Main.myLog.verbose(TAG, "List menu...");
                return new ScreenListMenu();
            case 2:
                // new
                Main.myLog.verbose(TAG, "Screen new menu...");
                //TODO: ScreenNewMenu
                return null;
            case 3:
                // attach
                Main.myLog.verbose(TAG, "Attach menu...");
                //TODO: ScreenAttachMenu
                return null;
            case 4:
                // detach
                Main.myLog.verbose(TAG, "Detach menu...");
                //TODO: ScreenDetatchMenu
                return null;
            case 5:
                // kill
                Main.myLog.verbose(TAG, "Kill menu...");
                //TODO: ScreenKillMenu
                return null;
            default:
                Main.myLog.warning(TAG,
                                   "invoke() Response Switch - default: "
                                           + super.getResponse());
                super.printErrorInput("Not a valid selection. Please try again.");
                return null;
        }
    }

}
