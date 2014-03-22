package com.indivisible.twistedserveradmin.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DateTimeUtil
{

    //// data

    private static final String FORMAT_AGE_DAYS = "%2dd,";
    private static final String FORMAT_AGE_HOURS = "%2dh,";
    private static final String FORMAT_AGE_MINUTES = "%2dm";
    private static final String FORMAT_LOG_TIME = "yyyyMMdd-HHmmss";

    public static final int MAX_LENGTH_DATE_SHORT = 11;


    //// methods

    /**
     * Get a DateTime object using a pair of Strings from a Screen list cmd
     * output.
     * 
     * @param rawDate
     * @param rawTime
     * @return
     */
    public static DateTime parseScreenListTime(String rawDate, String rawTime)
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy HH:mm:ss");
            DateTime dt = formatter.parseDateTime(rawDate + " " + rawTime);
            return dt;
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("!! Could not parse Strings for DateTime:");
            System.out.println(String.format("\t%s  %s", rawDate, rawTime));
            return null;
        }

    }

    /**
     * Get the age of a DateTime in milliseconds.
     * 
     * @param dt
     * @return
     */
    public static Period getAgePeriod(DateTime dt)
    {
        DateTime now = DateTime.now();
        return new Period(dt.getMillis(), now.getMillis());
    }

    /**
     * Get a String representation of a DateTime's age.
     * 
     * @param dt
     * @return
     */
    public static String getAgeString(DateTime dt)
    {
        Period age = getAgePeriod(dt);
        int days = age.getDays();
        int hrs = age.getHours();
        int mins = age.getMinutes();

        StringBuilder sb = new StringBuilder();
        if (days > 0)
        {
            sb.append(String.format(FORMAT_AGE_DAYS, days));
        }
        if (hrs > 0)
        {
            sb.append(String.format(FORMAT_AGE_HOURS, hrs));
        }
        sb.append(String.format(FORMAT_AGE_MINUTES, mins));

        return sb.toString();
    }

    public static String getLogTime()
    {
        DateTime now = DateTime.now();
        DateTimeFormatter df = DateTimeFormat.forPattern(FORMAT_LOG_TIME);
        return df.print(now);
    }

}
