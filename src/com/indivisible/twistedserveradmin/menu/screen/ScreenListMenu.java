package com.indivisible.twistedserveradmin.menu.screen;

import com.indivisible.twistedserveradmin.menu.base.MenuInputInteger;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.menu.util.EChoiceType;
import com.indivisible.twistedserveradmin.screen.Screen;
import com.indivisible.twistedserveradmin.screen.ScreenList;
import com.indivisible.twistedserveradmin.screen.ScreenSort;


/**
 * A Menu for displaying the status of and interacting with Linux Screens
 * 
 * @author indiv
 */
public class ScreenListMenu
        extends MenuInputInteger
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private ScreenList screens;

    private static final String title = "Screen List";
    private static final String description = null;
    private static final String question = "Screen listing sorted by current state";
    private static final String prompt = "Nothing further to do here yet...";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Create a new Menu for displaying Screens
     */
    public ScreenListMenu()
    {
        super(title, description, question, prompt);
        screens = new ScreenList();
        screens.refreshScreens();
        super.setChoices(makeChoices());
    }

    /**
     * Create the choices for the ScreenListMenu
     * 
     * @return
     */
    private Choices makeChoices()
    {
        //TODO: Varied sorting?
        screens = ScreenSort.sortByScreenState(screens);
        Choices choices = new Choices();
        String currentStatus = "";
        String previousStatus = "";
        choices.add(Screen.getInfoShortTitle(screens.getMaxNameLength()),
                    EChoiceType.header);
        for (Screen screen : screens.getAllScreens())
        {
            currentStatus = screen.getScreenState().name();
            if (!currentStatus.equals(previousStatus))
            {
                previousStatus = currentStatus;
                choices.add(currentStatus, EChoiceType.divider);
            }
            choices.add(screen.getInfoShortString(screens.getMaxNameLength()),
                        EChoiceType.selectable);
        }
        return choices;
    }
}
