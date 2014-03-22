package com.indivisible.twistedserveradmin.servers;

import com.indivisible.twistedserveradmin.menu.base.Menu;


/**
 * Enum for Server states. <br />
 * Has method for printable representations.
 * 
 * @author indiv
 */
public enum ServerStatus
{

    ///////////////////////////////////////////////////////
    ////    enumerators
    ///////////////////////////////////////////////////////

    online,     // server is online, got query results
    offline,    // server query tried, looks offline (or incorrect port)
    unknown,    // no query run yet
    error;      // server query failed, connection or settings error


    ///////////////////////////////////////////////////////
    ////    string methods
    ///////////////////////////////////////////////////////

    /**
     * Get a printable String for the given ServerState.
     * 
     * @param status
     * @param fixedWidth
     * @param useColor
     * @return
     */
    public static String getPrintable(ServerStatus status,
                                      boolean fixedWidth,
                                      boolean useColor)
    {
        if (useColor)
            return getColoredString(status, fixedWidth);
        else if (fixedWidth)
            return getStringFixedWidth(status);
        else
            return getString(status);
    }

    /**
     * Return a printable (non fixed width) String for the given ServerState.
     * 
     * @param status
     * @return
     */
    private static String getString(ServerStatus status)
    {
        switch (status)
        {
            case online:
                return "Online";
            case offline:
                return "Offline";
            case error:
                return "Error";
            case unknown:
            default:
                return "Unknown";
        }
    }

    /**
     * Return a printable (fixed width) String for the given ServerState.
     * 
     * @param status
     * @return
     */
    private static String getStringFixedWidth(ServerStatus status)
    {
        switch (status)
        {
            case online:
                return "Online ";
            case offline:
                return "Offline";
            case error:
                return "Error  ";
            case unknown:
            default:
                return "Unknown";
        }
    }

    /**
     * Return a printable String coloured and sized as desired based on
     * supplied ServerStatus.
     * 
     * @param status
     * @param fixedWidth
     * @return
     */
    private static String getColoredString(ServerStatus status, boolean fixedWidth)
    {
        StringBuilder output = new StringBuilder();

        switch (status)
        {
            case online:
                output.append(Menu.ANSI_GREEN);
                break;
            case offline:
                output.append(Menu.ANSI_PURPLE);
                break;
            case error:
                output.append(Menu.ANSI_RED);
                break;
            case unknown:
            default:
                output.append(Menu.ANSI_YELLOW);
                break;
        }
        if (fixedWidth)
        {
            output.append(getStringFixedWidth(status));
        }
        else
        {
            output.append(getString(status));
        }
        output.append(Menu.ANSI_RESET);
        return output.toString();
    }

}
