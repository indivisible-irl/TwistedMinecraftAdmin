package com.indivisible.twistedserveradmin.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.indivisible.twistedserveradmin.system.LinuxCommand;
import com.indivisible.twistedserveradmin.system.Main;


public class ScreenList
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private List<Screen> screens = null;
    private List<String> screenIds = null;
    private int maxNameLength = 0;

    private static final String CMD_SCREEN_LIST = "screen -ls";
    //private static final String CMD_SCREEN_LIST_ONE = "screen -ls | grep %s";
    private static final String OUTPUT_NO_SCREENS_FOUND = "No Sockets found";
    private static final String OUTPUT_SCREENS_FOUND = "There are screens";

    private static final String TAG = "ScreenList";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create an empty ScreenList.
     */
    public ScreenList()
    {
        screens = new ArrayList<Screen>();
        screenIds = new ArrayList<String>();
        maxNameLength = 0;
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    /**
     * Set all the screens.
     * 
     * @param scrs
     */
    public void setScreens(List<Screen> scrs)
    {
        screens = scrs;
        updateMaxNameLength();
    }

    /**
     * Empty the ScreenList
     */
    public void clear()
    {
        screens = new ArrayList<Screen>();
        screenIds = new ArrayList<String>();
        maxNameLength = 0;
    }

    /**
     * Refresh the list of screens.
     */
    public void refreshScreens()
    {
        clear();
        List<Screen> scrs = collectScreens();
        if (scrs == null)
        {
            Main.myLog.error(TAG, "collectScreens() returned null");
        }
        else if (scrs.size() == 0)
        {
            Main.myLog.warning(TAG, "No screens found.");
        }
        else
        {
            Main.myLog.debug(TAG, "Screens found: " + scrs.size());
            addMultiple(scrs);
        }
    }

    /**
     * Retrieve all Screens in a List. Maybe want to sort before.
     * 
     * @return
     */
    public List<Screen> getAllScreens()
    {
        return screens;
    }

    /**
     * Gets one screen by id (String) or returns null.
     * 
     * @param id
     * @return
     */
    public Screen getScreenById(String id)
    {
        for (Screen scr : screens)
        {
            if (id.equals(scr.getId()))
            {
                return scr;
            }
        }
        return null;
    }

    /**
     * Gets one screen by name (ignores case) or returns null.
     * 
     * @param name
     * @return
     */
    public Screen getScreenByName(String name)
    {
        for (Screen scr : screens)
        {
            if (name.equalsIgnoreCase(scr.getName()))
            {
                return scr;
            }
        }
        return null;
    }

    /**
     * Gets all screens matching a partial name or returns an empty List.
     * 
     * @param partialName
     * @return
     */
    public List<Screen> findScreensByName(String partialName)
    {
        List<Screen> scrs = new ArrayList<Screen>();
        for (Screen scr : screens)
        {
            if (scr.getName().toLowerCase().contains(partialName.toLowerCase()))
            {
                scrs.add(scr);
            }
        }
        return scrs;
    }

    /**
     * Get the maximum length of any name in the ScreenList.
     * 
     * @return
     */
    public int getMaxNameLength()
    {
        return maxNameLength;
    }


    ///////////////////////////////////////////////////////
    ////    add methods
    ///////////////////////////////////////////////////////

    /**
     * Add a single Screen
     * 
     * @param scr
     * @return
     */
    public boolean add(Screen scr)
    {
        if (!scr.isValid())
        {
            Main.myLog.warning(TAG, "Not a valid screen!");
            return false;
        }
        //System.out.println("Adding: " + scr.getName());
        int thisNameLength = scr.getName().length();
        if (screens.contains(scr))
        {
            System.out.println("!! Screen duplicate:\n\t"
                    + scr.getInfoShortString(maxNameLength));
            return false;
        }
        else
        {
            //System.out.println("= start single add");
            screenIds.add(scr.getId());
            screens.add(scr);
            if (maxNameLength < thisNameLength)
            {
                maxNameLength = thisNameLength;
                //System.out.println("- resized");
            }
            return true;
        }
    }

    /**
     * Add multiple Screens at once
     * 
     * @param scrs
     * @return
     */
    public boolean addMultiple(List<Screen> scrs)
    {
        //System.out.println("- multi count (start): " + scrs.size());
        //System.out.println("- sList count (start): " + screens.size());
        boolean allAddedSuccessfully = true;
        boolean thisAddSuccessful;
        //Screen tmpScreen;
        for (Screen scr : scrs)
        {
            //tmpScreen = new Screen(scr);
            thisAddSuccessful = add(scr);
            //System.out.println("= Success: " + thisAddSuccessful);
            if (!thisAddSuccessful)
            {
                allAddedSuccessfully = false;
            }
        }
        return allAddedSuccessfully;
    }

    /**
     * Add multiple Screens from a "screen -ls" command response.
     * 
     * @param rawScreenListResponse
     * @return
     */
    public boolean addMultipleRaw(List<String> rawScreenListResponse)
    {
        List<Screen> scrs = parseForScreens(rawScreenListResponse);
        if (scrs.size() == 0)
        {
            System.out.println("!! No Screens found.");
            return false;
        }
        return addMultiple(scrs);
    }


    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * Iterate over all screens to reset maxNameLength.
     */
    private void updateMaxNameLength()
    {
        maxNameLength = 0;
        int tempLength;
        for (Screen scr : screens)
        {
            tempLength = scr.getName().length();
            if (maxNameLength < tempLength)
            {
                maxNameLength = tempLength;
            }
        }
    }

    /**
     * Test a String to check if it contains correct info for a Screen.
     * 
     * @param line
     * @return
     */
    private boolean isScreenLine(String line)
    {
        boolean foundNum = false;
        boolean isScreen = false;
        for (int i = 0; i < line.length(); i++)
        {
            if (Character.isDigit(line.charAt(i)))
            {
                foundNum = true;
            }
            else if (line.charAt(i) == '.')
            {
                if (foundNum)
                {
                    isScreen = true;
                    break;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        return isScreen;
    }

    /**
     * Run a "screen -ls" command, parse for Screens and return a List
     * 
     * @return
     */
    private List<Screen> collectScreens()
    {
        List<String> rawScreenResponse = LinuxCommand.runCmdForOutput(CMD_SCREEN_LIST);

        if (rawScreenResponse == null)
        {
            System.out.println("!! Screen error, returned null");
            return null;
        }
        else if (rawScreenResponse.size() == 0)
        {
            System.out.println("!! No lines returned");
            return null;
        }
        else if (rawScreenResponse.get(0).trim().startsWith(OUTPUT_NO_SCREENS_FOUND))
        {
            System.out.println("No screens found.");
            return new ArrayList<Screen>();
        }
        else if (rawScreenResponse.get(0).trim().startsWith(OUTPUT_SCREENS_FOUND))
        {
            //System.out.println("Screens found");
            List<Screen> scrs = parseForScreens(rawScreenResponse);
            //System.out.println("- collect count:" + scrs.size());
            return scrs;
        }
        else
        {
            System.out.println("!! Unhandled screen response");
            return null;
        }
    }

    /**
     * Iterate over the response of a "screen -ls" command to gather Screens.
     * 
     * @param rawScreenResponse
     * @return
     */
    private List<Screen> parseForScreens(List<String> rawScreenListResponse)
    {
        List<Screen> scrs = new CopyOnWriteArrayList<Screen>();

        for (String line : rawScreenListResponse)
        {
            if (isScreenLine(line.trim()))
            {
                Screen scr = new Screen(line);
                if (scr.isValid())
                {
                    scrs.add(scr);
                }
            }
        }
        //System.out.println("- parse count: " + scrs.size());
        return scrs;
    }
}
