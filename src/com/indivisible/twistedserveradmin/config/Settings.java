package com.indivisible.twistedserveradmin.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.indivisible.twistedserveradmin.files.FileIterator;
import com.indivisible.twistedserveradmin.system.Main;


/**
 * Parent class to parse a settings file and collect key, value pairs of the
 * contained settings.
 * 
 * @author indiv
 * 
 */
abstract class Settings
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private File file;
    protected Map<String, String> settingsMap = null;

    private static final String COMMENT_CHAR = "#";
    private static final String EQUALS_CHAR = "=";

    private static String TAG = "Settings";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Class to collect key value pairs from a server settings file.
     * 
     * @param propsFilePath
     *        Settings file path
     * @throws IOException
     */
    protected Settings(File settingsFile) throws IOException
    {
        file = settingsFile;
        collectProperties(settingsFile);
    }

    /**
     * Perform the collection of key, value pairs from the file
     * 
     * @param propsFilePath
     * @throws IOException
     */
    private void collectProperties(File settingsFile) throws IOException
    {
        FileIterator iter = new FileIterator(settingsFile);
        settingsMap = new HashMap<String, String>();
        boolean hasCollectedOne = false;
        try
        {
            String line;
            int equalsLoc;
            while (iter.hasNext())
            {
                line = iter.next().trim();
                if (line.startsWith(COMMENT_CHAR))
                {
                    continue;
                }
                else if (line.isEmpty())
                {
                    continue;
                }
                else
                {
                    equalsLoc = line.indexOf(EQUALS_CHAR);
                    if (equalsLoc != -1)
                    {
                        String key = line.substring(0, equalsLoc).trim();
                        String value;
                        try
                        {
                            value = line.substring(equalsLoc + 1).trim();
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            value = "";
                        }
                        settingsMap.put(key, value);
                        hasCollectedOne = true;
                    }
                }
            }
        }
        finally
        {
            iter.close();
        }
        if (!hasCollectedOne)
        {
            //ASK: count lines added and return an int instead?
            Main.myLog
                    .error(TAG, "Never parsed a single line: " + file.getAbsolutePath());
        }
    }


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Verify that there are settings saved
     * 
     * @return
     */
    public boolean hasSettings()
    {
        return (settingsMap != null && !settingsMap.isEmpty());
    }


    ///////////////////////////////////////////////////////
    ////    Value retrieval
    ///////////////////////////////////////////////////////

    /**
     * Retrieve a single string value from the collected settings by key.
     * 
     * @param propertyName
     * @return String representation of the setting or null if not exists. May
     *         be empty String if not set.
     */
    private String getRawProperty(String propertyName)
    {
        if (settingsMap == null)
        {
            Main.myLog.error(TAG, "ServerProperties not collected or set");
            return null;
        }
        else if (settingsMap.isEmpty())
        {
            Main.myLog.error(TAG, "ServerProperties empty");
            return null;
        }
        else
        {
            // null if no key, empty String if not set
            String value = settingsMap.get(propertyName);
            if (value == null)
            {
                Main.myLog.error(TAG, "No key found matching [" + propertyName + "] "
                        + file.getAbsolutePath());
            }
            else if (value.equals(""))
            {
                Main.myLog.warning(TAG, "No value saved for key [" + propertyName + "] "
                        + file.getAbsolutePath());
            }
            return value;
        }
    }

    /**
     * Get a string value from the settings. Remember, String may be empty. <br />
     * Returns 'null' on failure.
     * 
     * @param propertyKey
     * @return String
     */
    public String getString(String propertyKey)
    {
        return getRawProperty(propertyKey);
    }

    /**
     * Attempt to convert a String value to an int. <br />
     * Returns Integer.MIN_VALUE on failure.
     * 
     * @param strNum
     * @return int
     */
    protected int getInt(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            return Integer.MIN_VALUE;
        }
        try
        {
            int val = Integer.parseInt(value);
            return val;
        }
        catch (NumberFormatException e)
        {
            Main.myLog.warning(TAG, "Error parsing " + propertyKey + " for int: ["
                    + value + "] " + file.getAbsolutePath());
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Convert a value to a boolean. <br />
     * Returns 'null' on failure.
     * 
     * @param propertyKey
     * @return Boolean
     */
    protected Boolean getBool(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            return null;
        }
        else if (value.toLowerCase().equals("true"))
        {
            return true;
        }
        else if (value.toLowerCase().equals("false"))
        {
            return false;
        }
        else
        {
            Main.myLog.warning(TAG,
                               "Error parsing for Boolean: [" + value + "] "
                                       + file.getAbsolutePath());
            return null;
        }
    }

    /**
     * Convert and return a long from the settings. <br />
     * Returns Long.MIN_VALUE on failure.
     * 
     * @param propertyKey
     * @return long
     */
    public long getLong(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            return Long.MIN_VALUE;
        }
        try
        {
            long num = Long.parseLong(value);
            return num;
        }
        catch (NumberFormatException e)
        {
            Main.myLog.warning(TAG,
                               "Error parsing for long: [" + value + "] "
                                       + file.getAbsolutePath());
            return Long.MIN_VALUE;
        }
    }

    /**
     * Get a float value from the settings. <br />
     * Returns Float.MIN_VALUE on failure.
     * 
     * @param propertyKey
     * @return float
     */
    public float getFloat(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            return Float.MIN_VALUE;
        }
        try
        {
            float num = Float.parseFloat(value);
            return num;
        }
        catch (NumberFormatException e)
        {
            Main.myLog.warning(TAG,
                               "Error parsing for float: [" + value + "] "
                                       + file.getAbsolutePath());
            return Float.MIN_VALUE;
        }
    }


    ///////////////////////////////////////////////////////    
    ////    String test and parse methods
    ///////////////////////////////////////////////////////

    /**
     * Recursive method to strip out colour codes from the MOTD.<br/>
     * Borrowed from my MCServerPing application.<br/>
     * ASK: merge any of its functionality into this app? <br />
     * TODO: Move to util.StringUtil
     * 
     * @param name
     * @return
     */
    protected String recurseRemoveMinecraftFormatting(String name)
    {
        int foundIndex = name.indexOf("§");
        if (foundIndex == -1)
        {
            return name;
        }
        if (foundIndex == 0)
        {
            return recurseRemoveMinecraftFormatting(name.substring(2));
        }
        else
        {
            String start = name.substring(0, foundIndex);
            String end = name.substring(foundIndex + 2);
            return recurseRemoveMinecraftFormatting(start.concat(end));
        }
    }

    /**
     * Convert unicode escaped Strings to their encoded characters and return
     * full converted string.
     * 
     * @param
     * @return
     */
    protected String unescapeUnicodeChars(String str)
    {
        int i = 0, len = str.length();
        char c;
        StringBuffer sb = new StringBuffer(len);
        while (i < len)
        {
            c = str.charAt(i++);
            if (c == '\\')
            {
                if (i < len)
                {
                    c = str.charAt(i++);
                    if (c == 'u')
                    {
                        c = (char) Integer.parseInt(str.substring(i, i + 4), 16);
                        i += 4;
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Test an IP address as an IPv4 address.
     * 
     * @param rawIP
     * @return
     */
    protected boolean testIPv4(String rawIP)
    {
        int count = 0;
        for (int i = 0; i < rawIP.length(); i++)
        {
            if (rawIP.charAt(i) == '.')
            {
                count++;
            }
            else if (!Character.isDigit(rawIP.charAt(i)))
            {
                Main.myLog
                        .warning(TAG, "Encountered non digit character in IP: " + rawIP);
                return false;
            }
        }
        return count == 3;
    }

    //ASK: Also test for IPv6??
    //Looks way too complicated to bother to attempt.
    //Go with a try...catch model instead?

}
