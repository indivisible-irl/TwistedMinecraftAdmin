package com.indivisible.twistedserveradmin.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.indivisible.twistedserveradmin.files.FileIterator;

/**
 * Parent class to parse a settings file and collect key, value pairs of the
 * contained settings.
 * 
 * @author indiv
 * 
 */
public class Settings
{

    //// data

    private File file;
    protected Map<String, String> settings = null;


    //// constructor & init

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
        settings = new HashMap<String, String>();
        String line;
        int equalsLoc;
        boolean hasRunOnce = false;
        try
        {
            while (iter.hasNext())
            {
                line = iter.next().trim();
                if (line.startsWith("#"))
                {
                    continue;
                }
                else if (line.isEmpty())
                {
                    continue;
                }
                else
                {
                    equalsLoc = line.indexOf("=");
                    if (equalsLoc != -1)
                    {
                        String key = line.substring(0, equalsLoc).trim();
                        String prop;
                        try
                        {
                            prop = line.substring(equalsLoc + 1).trim();
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            prop = "";
                        }
                        settings.put(key, prop);
                        hasRunOnce = true;
                    }
                }
            }
            if (!hasRunOnce)
            {
                //TODO: count lines added and return an int instead
                System.out.println(" === Error: Never parsed a single line: "
                        + file.getAbsolutePath());
            }
        }
        finally
        {
            iter.close();
        }

    }


    //// public methods

    /**
     * Verify that there are settings saved
     * 
     * @return
     */
    public boolean hasSettings()
    {
        return (settings != null && !settings.isEmpty());
    }

    /**
     * Retrieve a single string value from the collected settings by key.
     * 
     * @param propertyName
     * @return String representation of the setting or null if not exists. May
     *         be empty String if not set.
     */
    private String getRawProperty(String propertyName)
    {
        if (settings == null || settings.isEmpty())
        {
            System.out.println(" === Properties not collected / set");
            return null;
        }
        else
        {
            // null if no key, empty String if not set
            return settings.get(propertyName);
        }
    }


    //// protected methods

    /**
     * Recursive method to strip out colour codes from the MOTD.<br/>
     * Borrowed from my MCServerPing application.<br/>
     * (TODO: merge its functionality into this)
     * 
     * @param name
     * @return
     */
    protected String recurseRemoveMinecraftColor(String name)
    {
        int foundIndex = name.indexOf("§");
        if (foundIndex == -1)
        {
            return name;
        }
        if (foundIndex == 0)
        {
            return recurseRemoveMinecraftColor(name.substring(2));
        }
        else
        {
            String start = name.substring(0, foundIndex);
            String end = name.substring(foundIndex + 2);
            return recurseRemoveMinecraftColor(start.concat(end));
        }
    }

    /**
     * Convert unicode escaped Strings to their encoded characters and return
     * full converted string
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
     * Get a string value from the settings. Remember, String may be empty.
     * 
     * @param propertyKey
     * @return String, Returns 'null' on failure.
     */
    public String getString(String propertyKey)
    {
        return getRawProperty(propertyKey);
    }

    /**
     * Attempt to convert a String value to an int
     * 
     * @param strNum
     * @return int, Returns Integer.MIN_VALUE on failure.
     */
    protected int getInt(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            //System.out.println(" === Key not exists: [" + propertyKey + "]");
            return Integer.MIN_VALUE;
        }
        try
        {
            int val = Integer.parseInt(value);
            return val;
        }
        catch (NumberFormatException e)
        {
            //System.out.println(" === Error parsing for int: [" + value + "]");
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Convert a value to a boolean.
     * 
     * @param propertyKey
     * @return Boolean, Returns 'null' on failure
     */
    protected Boolean getBool(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            //System.out.println(" === Key not exists: [" + propertyKey + "]");
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
            //System.out.println(" === Error parsing boolean: " + value);
            return null;
        }
    }

    /**
     * Convert and return a long from the settings.
     * 
     * @param propertyKey
     * @return long, Returns Long.MIN_VALUE on failure.
     */
    public long getLong(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            //System.out.println(" === Key not exists [" + propertyKey + "]");
            return Long.MIN_VALUE;
        }
        try
        {
            long num = Long.parseLong(value);
            return num;
        }
        catch (NumberFormatException e)
        {
            //System.out.println(" === Error parsing long: " + value);
            return Long.MIN_VALUE;
        }
    }

    /**
     * Get a float value from the settings.
     * 
     * @param propertyKey
     * @return float, Returns Float.MIN_VALUE on failure.
     */
    public float getFloat(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null || value.equals(""))
        {
            //System.out.println(" === Error reading float: " + propertyKey);
            return Float.MIN_VALUE;
        }
        try
        {
            float num = Float.parseFloat(value);
            return num;
        }
        catch (NumberFormatException e)
        {
            //System.out.println(" === Unable to convert to float: [" + value + "]");
            return Float.MIN_VALUE;
        }
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
                //System.out.println(" === Encountered non digit character in IP: " + rawIP);
                return false;
            }
        }
        return count == 3;
    }

}
