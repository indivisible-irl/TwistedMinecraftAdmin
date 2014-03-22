package com.indivisible.twistedserveradmin.screen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Class to handle sorting of ScreenLists
 * 
 * @author indiv
 */
public class ScreenSort
{

    ///////////////////////////////////////////////////////
    ////    public sorting methods
    ///////////////////////////////////////////////////////

    /**
     * Sort a ScreenList's Screens by Id.
     * 
     * @param screens
     * @return
     */
    public static ScreenList sortByScreenId(ScreenList screens)
    {
        List<Screen> scrs = screens.getAllScreens();
        Collections.sort(scrs, new ScreenIdSort());
        screens.setScreens(scrs);
        return screens;
    }

    /**
     * Sort a ScreenList's Screens by name.
     * 
     * @param screens
     * @return
     */
    public static ScreenList sortByScreenName(ScreenList screens)
    {
        List<Screen> scrs = screens.getAllScreens();
        Collections.sort(scrs, new ScreenNameSort());
        screens.setScreens(scrs);
        return screens;
    }

    /**
     * Sort a ScreenList's Screens by state.
     * 
     * @param screens
     * @return
     */
    public static ScreenList sortByScreenState(ScreenList screens)
    {
        List<Screen> scrs = screens.getAllScreens();
        Collections.sort(scrs, new ScreenStateSort());
        screens.setScreens(scrs);
        return screens;
    }

    /**
     * Sort a ScreenList's Screens by age.
     * 
     * @param screens
     * @return
     */
    public static ScreenList sortByScreenAge(ScreenList screens)
    {
        List<Screen> scrs = screens.getAllScreens();
        Collections.sort(scrs, new ScreenAgeSort());
        screens.setScreens(scrs);
        return screens;
    }


    ///////////////////////////////////////////////////////
    ////    sorting classes
    ///////////////////////////////////////////////////////

    /**
     * Private Comparator class for Screen Id sorting
     * 
     * @author indiv
     */
    private static class ScreenIdSort
            implements Comparator<Screen>
    {

        @Override
        public int compare(Screen s1, Screen s2)
        {
            return s1.getIdInteger().compareTo(s2.getIdInteger());
        }
    }

    /**
     * Private Comparator class for Screen name sorting
     * 
     * @author indiv
     */
    private static class ScreenNameSort
            implements Comparator<Screen>
    {

        @Override
        public int compare(Screen s1, Screen s2)
        {
            int nameComp = s1.getName().toLowerCase()
                    .compareTo(s2.getName().toLowerCase());
            if (nameComp == 0)
            {
                return s1.getIdInteger().compareTo(s2.getIdInteger());
            }
            return nameComp;
        }
    }

    /**
     * Private Comparator class for Screen state sorting. <br />
     * Sorts by State then by Name.
     * 
     * @author indiv
     */
    private static class ScreenStateSort
            implements Comparator<Screen>
    {

        @Override
        public int compare(Screen s1, Screen s2)
        {
            int stateComp = s1.getScreenState().compareTo(s2.getScreenState());
            if (stateComp == 0)
            {
                int nameComp = s1.getName().compareTo(s2.getName());
                if (nameComp == 0)
                {
                    return s1.getIdInteger().compareTo(s2.getIdInteger());
                }
                return nameComp;
            }
            return stateComp;
        }
    }

    /**
     * Private Comparator class for Screen state sorting. <br />
     * Sorts by Screen age.
     * 
     * @author indiv
     */
    private static class ScreenAgeSort
            implements Comparator<Screen>
    {

        @Override
        public int compare(Screen s1, Screen s2)
        {
            return s1.getAgeMillis().compareTo(s2.getAgeMillis());
        }
    }

}
