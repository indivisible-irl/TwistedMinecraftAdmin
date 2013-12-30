package com.indivisible.twistedserveradmin.servers;

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
public class ServerSettings
{

    //// data

    private String filePath;
    protected Map<String, String> settings = null;


    //// constructor & init

    /**
     * Class to collect key value pairs from a server settings file.
     * 
     * @param propsFilePath
     *        Settings file path
     * @throws IOException
     */
    protected ServerSettings(String settingsFilePath) throws IOException
    {
        filePath = settingsFilePath;
        collectProperties(settingsFilePath);
    }

    /**
     * Perform the collection of key, value pairs from the file
     * 
     * @param propsFilePath
     * @throws IOException
     */
    private void collectProperties(String propsFilePath) throws IOException
    {
        FileIterator iter = new FileIterator(propsFilePath);
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
                else if (line.equals(""))
                {
                    continue;
                }

                equalsLoc = line.indexOf("=");
                if (equalsLoc != -1)
                {
                    settings.put(line.substring(0, equalsLoc).trim(),
                                 line.substring(equalsLoc + 1).trim());
                    hasRunOnce = true;
                }
            }
            if (!hasRunOnce)
            {
                System.out.println(" === Error: Never parsed a single line: " + filePath);
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
     * Retrieve a single value from the collected settings by key.
     * 
     * @param propertyName
     * @return String representation of the setting or null if not exists. May
     *         be empty String if not set.
     */
    public String getRawProperty(String propertyName)
    {
        if (settings == null || settings.isEmpty())
        {
            System.out.println(" === Properties not collected / set");
            return null;
        }
        else
        {
            return settings.get(propertyName);
        }
    }


    //// protected methods

    /**
     * Recursive method to strip out colour codes from the MOTD. Borrowed from
     * my MCServerPing application. (TODO merge its functionality into this)
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
     * Convert unicode escaped Strings to their encoded characters
     * 
     * @param s
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
     * Attempt to convert a String value to an int
     * 
     * @param strNum
     * @return Returns Integer.MIN_VALUE on failure.
     */
    protected int getInt(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            System.out.println(" === Key not exists: [" + propertyKey + "]");
            return Integer.MIN_VALUE;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            System.out.println(" === Error parsing for int: [" + value + "]");
            return -1;
        }
    }

    /**
     * Convert a value to a boolean.
     * 
     * @param propertyKey
     * @return Returns 'null' on failure
     */
    protected Boolean getBool(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            System.out.println(" === Key not exists: [" + propertyKey + "]");
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
            System.out.println(" === Error parsing boolean: " + value);
            return null;
        }
    }

    /**
     * 
     * @param propertyKey
     * @return
     */
    public long getLong(String propertyKey)
    {
        String value = getRawProperty(propertyKey);
        if (value == null)
        {
            System.out.println(" === Key not exists [" + propertyKey + "]");
            return Long.MIN_VALUE;
        }
        try
        {
            long num = Long.parseLong(value);
            return num;
        }
        catch (NumberFormatException e)
        {
            System.out.println(" === Error parsing long: " + value);
            return Long.MIN_VALUE;
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
                System.out
                        .println(" === Encountered non digit character in IP: " + rawIP);
                return false;
            }
        }
        return count == 3;
    }

}
