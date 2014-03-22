package com.indivisible.twistedserveradmin.screen;

import org.joda.time.DateTime;
import com.indivisible.twistedserveradmin.system.Main;
import com.indivisible.twistedserveradmin.util.DateTimeUtil;
import com.indivisible.twistedserveradmin.util.StringUtil;


/**
 * Class to represent a Linux Screen instance.
 * 
 * @author indiv
 */
public class Screen
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private String id = "";
    private String name = "";
    private DateTime startTime = DateTime.now();
    private ScreenState state = ScreenState.unknown;

    private static final String TITLE_SHORT_STATE = "??";
    private static final String TITLE_SHORT_ID = "ID";
    private static final String TITLE_SHORT_NAME = "Name";
    private static final String TITLE_SHORT_AGE = "Age";
    private static final String FORMAT_INFO_SHORT = "%s   %s   %s   %s";
    //private static final String FORMAT_INFO_LONG = "";

    public static final int MAX_LENGTH_ID = 5;
    public static final int DIV_EXTRA_SPACES = 10;   // non %s chars in FORMAT_INFO_SHORT ++

    private static final String TAG = "Screen";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create new Screen from a "screen -ls" or similar result line.
     * 
     * @param screenLineRaw
     */
    public Screen(String screenLineRaw)
    {
        parseScreenDetails(screenLineRaw);
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    /**
     * Check if this is a valid Screen instance. <br />
     * ScreenList will test this before adding.
     * 
     * @return
     */
    public boolean isValid()
    {
        //ASK: Want to include state and start time here too?
        return (!id.equals("") && !name.equals(""));
    }

    /**
     * Gets the Screen's identifier (String of an int).
     * 
     * @return
     */
    public String getId()
    {
        return id;
    }

    /**
     * Get the Screen's identifier as an Integer.
     * 
     * @return
     */
    public Integer getIdInteger()
    {
        try
        {
            return Integer.parseInt(getId());
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }

    /**
     * Gets the Screen's name.
     * 
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the Screen's start DateTime
     * 
     * @return
     */
    public DateTime getStartDateTime()
    {
        return startTime;
    }

    /**
     * Get the Screen's age in milliseconds.
     * 
     * @return
     */
    public Long getAgeMillis()
    {
        return new Long(DateTime.now().getMillis() - startTime.getMillis());
    }

    /**
     * Get the Screen's state
     * 
     * @return
     */
    public ScreenState getScreenState()
    {
        return state;
    }


    ///////////////////////////////////////////////////////
    ////    parse raw screen list output
    ///////////////////////////////////////////////////////

    /**
     * Parse a raw line for Screen info. Set required bits and return boolean
     * of success.
     * 
     * @param rawLine
     * @return
     */
    private boolean parseScreenDetails(String rawLine)
    {
        // clean and split the raw string
        String partialClean = rawLine.trim().replaceAll("\\(", "").replaceAll("\\)", "");
        String[] split = partialClean.split("\\s+");
        if (split.length != 4)
        {
            System.out.println("!!! Invalid numbe of arguments for Screen info: "
                    + split.length + "\n\t" + rawLine);
            return false;
        }
        else
        {
            return parseIdentifiers(split[0]) && parseStarted(split[1], split[2])
                    && parseState(split[3]);
        }
    }

    /**
     * Parse the section containing identifiers (id and name). <br />
     * Returns successful parse.
     * 
     * @param rawIdentifier
     * @return
     */
    private boolean parseIdentifiers(String rawIdentifier)
    {
        String[] split = rawIdentifier.split("\\.");
        if (split.length == 2)
        {
            id = split[0];
            name = split[1];
            return true;
        }
        else
        {
            System.out.println("!!! Error splitting identifiers: " + rawIdentifier);
            return false;
        }
    }

    /**
     * Parse the section containing start date and time. <br />
     * Returns successful parse.
     * 
     * @param rawDate
     * @param rawTime
     * @return
     */
    private boolean parseStarted(String rawDate, String rawTime)
    {
        startTime = DateTimeUtil.parseScreenListTime(rawDate, rawTime);
        return (startTime != null);
    }

    /**
     * Parse raw info for the Screen's state. <br />
     * Returns successful parse.
     * 
     * @param rawScreenState
     * @return
     */
    private boolean parseState(String rawScreenState)
    {
        state = ScreenState.getScreenState(rawScreenState);
        return (state != ScreenState.unknown);
    }


    ///////////////////////////////////////////////////////
    ////    printing and menu choices
    ///////////////////////////////////////////////////////

    /**
     * Return a short String containing Screen info.
     * 
     * @param maxNameLength
     * @return
     */
    public String getInfoShortString(int maxNameLength)
    {
        String strName = StringUtil.leftAlign(name, maxNameLength);
        String strId = StringUtil.rightAlign(id, MAX_LENGTH_ID);
        String strAge = StringUtil.rightAlign(DateTimeUtil.getAgeString(startTime),
                                              DateTimeUtil.MAX_LENGTH_DATE_SHORT);
        String strState = StringUtil.leftAlign(ScreenState.getShortName(state),
                                               ScreenState.MAX_LENGTH_STATE_SHORT);
        return String.format(FORMAT_INFO_SHORT, strName, strId, strAge, strState);
    }

    /**
     * Get a title row for the short screen info.
     * 
     * @param maxNameLength
     * @return
     */
    public static String getInfoShortTitle(int maxNameLength)
    {
        String strState = StringUtil.leftAlign(TITLE_SHORT_STATE,
                                               ScreenState.MAX_LENGTH_STATE_SHORT);
        String strId = StringUtil.rightAlign(TITLE_SHORT_ID, Screen.MAX_LENGTH_ID);
        String strName = StringUtil.leftAlign(TITLE_SHORT_NAME, maxNameLength);
        String strAge = StringUtil.rightAlign(TITLE_SHORT_AGE,
                                              DateTimeUtil.MAX_LENGTH_DATE_SHORT);
        return String.format(FORMAT_INFO_SHORT, strName, strId, strAge, strState);
    }

    /**
     * Get a title row for the short screen info (not sized by contents)
     * 
     * @return
     */
    public static String getInfoShortTitle()
    {
        return getInfoShortTitle(TITLE_SHORT_NAME.length());
    }

    /**
     * Get a spacer of the correct length for splitting header and content.
     * 
     * @param maxNameLength
     * @return
     */
    public static String getInfoShortDivider(int maxNameLength)
    {
        int lineLength = 0;
        lineLength += maxNameLength;
        lineLength += Screen.MAX_LENGTH_ID;
        lineLength += DateTimeUtil.MAX_LENGTH_DATE_SHORT;
        lineLength += ScreenState.MAX_LENGTH_STATE_SHORT;
        lineLength += Screen.DIV_EXTRA_SPACES;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineLength; i++)
        {
            sb.append(StringUtil.CHAR_DIV);
        }
        return sb.toString();
    }

    /**
     * Get a String representation of the Screen's age.
     * 
     * @return
     */
    public String getScreenAgeShort()
    {
        return DateTimeUtil.getAgeString(startTime);
    }


    ///////////////////////////////////////////////////////
    ////    overrides
    ///////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Screen))
        {
            return false;
        }
        Screen scr = (Screen) obj;
        if (getId().equals(scr.getId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    @Override
    public int hashCode()
    {
        try
        {
            return Integer.parseInt(getId());
        }
        catch (NumberFormatException e)
        {
            Main.myLog.error(TAG, "Failed to make hashCode of Screen ID");
        }
        return getName().hashCode();
    }

    @Override
    public String toString()
    {
        return name;
    }


}
