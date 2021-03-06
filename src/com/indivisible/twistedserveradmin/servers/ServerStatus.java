package com.indivisible.twistedserveradmin.servers;

import com.indivisible.twistedserveradmin.util.StringColor;
import com.indivisible.twistedserveradmin.util.StringColor.Color;


/**
 * Enum for Server states. <br />
 * Has method for printable representations.
 * 
 * <ul>
 * <li><b>online</b> - Server is online and reachable</li>
 * <li><b>offline</b> - Server is offline</li>
 * <li><b>unknown</b> - Server has not yet been queried or state is weird</li>
 * <li><b>error</b> - Server is in a broken state. Likely frozen and
 * unresponsive</li>
 * </ul>
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
        Color col;
        switch (status)
        {
            case online:
                col = Color.GREEN;
                break;
            case offline:
                col = Color.MAGENTA;
                break;
            case error:
                col = Color.RED;
                break;
            case unknown:
            default:
                col = Color.YELLOW;
                break;
        }
        if (fixedWidth)
        {
            return StringColor.format(getStringFixedWidth(status), col);
            //output.append(getStringFixedWidth(status));
        }
        else
        {
            return StringColor.format(getString(status), col);
            //output.append(getString(status));
        }
    }

}
