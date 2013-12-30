package com.indivisible.twistedserveradmin.config;

import java.io.File;
import java.io.IOException;
import com.indivisible.twistedserveradmin.servers.ServerSettings;


public class ApplicationSettings
        extends ServerSettings
{

    // data members
    private long queryTimeoutMillis = -1;
    private long queryLifespanMillis = -1;

    // default values
    //TODO server.info > settings > default. timeout, 
    private static final long DEFAULT_QUERY_TIMEOUT = 1500L;    // 1.5 secs
    private static final long DEFAULT_QUERY_LIFESPAN = 10000L;  // 10 secs

    // settings' keys' names
    private static final String FILE_SETTINGS = "settings.cfg";
    private static final String KEY_QUERY_TIMEOUT = "query-timeout";
    private static final String KEY_QUERY_LIFESPAN = "query-lifespan";


    // constructor & init

    public ApplicationSettings() throws IOException
    {
        super(new File(ApplicationSettings.class.getProtectionDomain().getCodeSource().getLocation()
                .getPath()).getParent()
                + File.separator + FILE_SETTINGS);
        //FIXME ^ that's an awful mess and I don't trust it. Need a better way to organise the Settings family
    }


    // public methods

    /**
     * Get the milliseconds
     * 
     * @return
     */
    public long getQueryTimeout()
    {
        return getLong(KEY_QUERY_TIMEOUT);
    }

    public long getQueryLifespan()
    {
        return getLong(KEY_QUERY_LIFESPAN);
    }
}
