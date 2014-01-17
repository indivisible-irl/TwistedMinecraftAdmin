package com.indivisible.twistedserveradmin.logging;

import java.io.File;


public class MyLog
{

    //TODO: logging - use util.Logging instead

    //// data

    //private File logFile = null;
    private int loggingLevel = -1;
    private int debuggingLevel = -1;

    //private static final int LEVEL_OFF = 0;
    private static final int LEVEL_ERROR = 1;
    private static final int LEVEL_WARNING = 2;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_VERBOSE = 4;

    private static String DEBUG_ERROR_MSG = "[ERR]  %s";
    private static String DEBUG_WARNING_MSG = "[WARN] %s";
    private static String DEBUG_INFO_MSG = "[INFO] %s";
    private static String DEBUG_VERBOSE_MSG = "[VERB] %s";


    //// constructor

    public MyLog(File logFile, int logLevel, int debugLevel)
    {
        loggingLevel = logLevel;
        debuggingLevel = debugLevel;
    }


    //// public methods

    public void error(String className, String msg)
    {
        if (loggingLevel >= LEVEL_ERROR)
        {
            logError(className, msg);
        }
        if (debuggingLevel >= LEVEL_ERROR)
        {
            debugError(className, msg);
        }
    }

    public void error(String className, String[] msgs)
    {
        if (loggingLevel >= LEVEL_ERROR)
        {
            for (String msg : msgs)
            {
                logError(className, msg);
            }
        }
        if (debuggingLevel >= LEVEL_ERROR)
        {
            for (String msg : msgs)
            {
                debugError(className, msg);
            }
        }
    }

    public void warning(String className, String msg)
    {
        if (loggingLevel >= LEVEL_WARNING)
        {
            logWarning(className, msg);
        }
        if (debuggingLevel >= LEVEL_WARNING)
        {
            debugWarning(className, msg);
        }
    }

    public void warning(String className, String[] msgs)
    {
        if (loggingLevel >= LEVEL_WARNING)
        {
            for (String msg : msgs)
            {
                logWarning(className, msg);
            }
        }
        if (debuggingLevel >= LEVEL_WARNING)
        {
            for (String msg : msgs)
            {
                debugWarning(className, msg);
            }
        }
    }

    public void info(String className, String msg)
    {
        if (loggingLevel >= LEVEL_INFO)
        {
            logInfo(className, msg);
        }
        if (debuggingLevel >= LEVEL_INFO)
        {
            debugInfo(className, msg);
        }
    }

    public void info(String className, String[] msgs)
    {
        if (loggingLevel >= LEVEL_INFO)
        {
            for (String msg : msgs)
            {
                logInfo(className, msg);
            }
        }
        if (debuggingLevel >= LEVEL_INFO)
        {
            for (String msg : msgs)
            {
                debugInfo(className, msg);
            }
        }
    }

    public void verbose(String className, String msg)
    {
        if (loggingLevel >= LEVEL_VERBOSE)
        {
            logVerbose(className, msg);
        }
        if (debuggingLevel >= LEVEL_VERBOSE)
        {
            debugVerbose(className, msg);
        }
    }

    public void verbose(String className, String[] msgs)
    {
        if (loggingLevel >= LEVEL_VERBOSE)
        {
            for (String msg : msgs)
            {
                logVerbose(className, msg);
            }
        }
        if (debuggingLevel >= LEVEL_VERBOSE)
        {
            for (String msg : msgs)
            {
                debugVerbose(className, msg);
            }
        }
    }


    //// private methods

    // logging methods

    private void logError(String className, String msg)
    {
        //TODO: log
    }

    private void logWarning(String className, String msg)
    {
        //TODO: log
    }

    private void logInfo(String className, String msg)
    {
        //TODO: log
    }

    private void logVerbose(String className, String msg)
    {
        //TODO: log
    }

    // debugging methods

    private void debugError(String className, String msg)
    {
        System.out.println(String.format(DEBUG_ERROR_MSG, msg));
    }

    private void debugWarning(String className, String msg)
    {
        System.out.println(String.format(DEBUG_WARNING_MSG, msg));
    }

    private void debugInfo(String className, String msg)
    {
        System.out.println(String.format(DEBUG_INFO_MSG, msg));
    }

    private void debugVerbose(String className, String msg)
    {
        System.out.println(String.format(DEBUG_VERBOSE_MSG, msg));
    }


}
