package com.indivisible.twistedserveradmin.config;

import java.io.IOException;
import com.indivisible.twistedserveradmin.files.FileGetter;


/**
 * Class to manage the Application Settings.
 * 
 * @author indiv
 */
public class SettingsApplication
        extends Settings
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    // default values
    //TODO server.info > settings > default.
    private static final boolean DEFAULT_MONITOR = false;
    private static final long DEFAULT_QUERY_TIMEOUT = 1500L;    // 1.5 secs
    private static final long DEFAULT_QUERY_LIFESPAN = 12000L;  // 12 secs
    private static final long DEFAULT_CHECK_FREQUENCY = 30000L; // 30 secs
    private static final int DEFAULT_CHECK_ATTEMPTS = 3;
    private static final boolean DEFAULT_TRY_RESTART = false;
    private static final boolean DEFAULT_CREATE_INFO = false;
    private static final String DEFAULT_SERVER_IP = "localhost";

    // settings' keys' names
    private static final String KEY_QUERY_TIMEOUT = "query-timeout";
    private static final String KEY_QUERY_LIFESPAN = "query-lifespan";
    private static final String KEY_MONITOR = "monitor";
    private static final String KEY_CHECK_FREQUENCY = "check-frequency";
    private static final String KEY_CHECK_ATTEMPTS = "check-attempts";
    private static final String KEY_TRY_RESTART = "try-restart";
    private static final String KEY_CREATE_INFO = "create-info";
    private static final String KEY_DEFAULT_ADDRESS = "default-server-ip";

    //TODO: default backup location


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Create a new instance of the Application wide Settings.
     * 
     * @throws IOException
     */
    public SettingsApplication() throws IOException
    {
        super(FileGetter.getApplicationSettingsFile());
    }


    ///////////////////////////////////////////////////////
    ////    retrieve values
    ///////////////////////////////////////////////////////

    /**
     * Get the time to wait before giving up on a Query response.
     * 
     * @return
     */
    public long getQueryTimeout()
    {
        Float timeoutSeconds = getFloat(KEY_QUERY_TIMEOUT);
        if (timeoutSeconds == Float.MIN_VALUE)
        {
            return DEFAULT_QUERY_TIMEOUT;
        }
        return (long) (timeoutSeconds * 1000);
    }

    /**
     * Get the time span a Query is considered still applicable / fresh.
     * 
     * @return
     */
    public long getQueryLifespan()
    {
        Float lifespanSeconds = getFloat(KEY_QUERY_LIFESPAN);
        if (lifespanSeconds == Float.MIN_VALUE)
        {
            return DEFAULT_QUERY_LIFESPAN;
        }
        return (long) (lifespanSeconds * 1000);
    }

    /**
     * Check if the Monitor functionality is enabled.
     * 
     * @return
     */
    public boolean doMonitor()
    {
        Boolean monitor = getBool(KEY_MONITOR);
        if (monitor == null)
        {
            return DEFAULT_MONITOR;
        }
        return monitor;
    }

    /**
     * Get the time to wait between data refreshes.
     * 
     * @return
     */
    public long getCheckFrequency()
    {
        float freqSeconds = getLong(KEY_CHECK_FREQUENCY);
        if (freqSeconds == Long.MIN_VALUE)
        {
            return DEFAULT_CHECK_FREQUENCY;
        }
        return (long) (freqSeconds * 1000);
    }

    /**
     * Get the number of failed Query attempts allowed before moving on.
     * 
     * @return
     */
    public int getCheckAttempts()
    {
        int attempts = getInt(KEY_CHECK_ATTEMPTS);
        if (attempts == Integer.MIN_VALUE)
        {
            return DEFAULT_CHECK_ATTEMPTS;
        }
        return attempts;
    }

    /**
     * Check whether Server restart is enabled or disabled application wide.
     * 
     * @return
     */
    public boolean doTryRestart()
    {
        Boolean restart = getBool(KEY_TRY_RESTART);
        if (restart == null)
        {
            return DEFAULT_TRY_RESTART;
        }
        return restart;
    }

    /**
     * Check whether or not the application should create a Server info file
     * automatically is none is found in a Server's folder.
     * 
     * @return
     */
    public boolean doCreateMissingInfo()
    {
        Boolean create = getBool(KEY_CREATE_INFO);
        if (create == null)
        {
            return DEFAULT_CREATE_INFO;
        }
        return create;
    }

    /**
     * Get the IP to use as a default in place of none found for any
     * individual Server.
     * 
     * @return
     */
    public String getDefaultServerIP()
    {
        String defaultIP = getString(KEY_DEFAULT_ADDRESS);
        if (defaultIP == null || defaultIP.equals(""))
        {
            return DEFAULT_SERVER_IP;
        }
        return defaultIP;
    }

}
