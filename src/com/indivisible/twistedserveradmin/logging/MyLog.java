package com.indivisible.twistedserveradmin.logging;

import java.io.File;
import com.indivisible.twistedserveradmin.util.DateTimeUtil;


public class MyLog
{

    //TODO: logging - use util.Logging
    //ASK: log to db?

    //// data

    //private File logFile = null;
    //private Level loggingLevel;
    private static LogLevel printLevel;

    private static String COL_RESET = "\u001B[0m";
    private static String COL_RED = "\u001B[31m";
    private static String COL_PURPLE = "\u001B[35m";
    private static String COL_YELLOW = "\u001B[33m";
    private static String COL_WHITE = "\u001B[37m";
    private static String COL_CYAN = "\u001B[36m";

    private static String FORMAT_ERROR_MSG = "%s " + COL_RED + "[ERR!]  [%s] %s"
            + COL_RESET;
    private static String FORMAT_WARNING_MSG = "%s " + COL_PURPLE + "[WARN] [%s] %s"
            + COL_RESET;
    private static String FORMAT_INFO_MSG = "%s " + COL_YELLOW + "[INFO] [%s] %s"
            + COL_RESET;
    private static String FORMAT_VERBOSE_MSG = "%s " + COL_WHITE + "[VERB] [%s] %s"
            + COL_RESET;
    private static String FORMAT_DEBUG_MSG = "%s " + COL_CYAN + "[DEBG] [%s] %s"
            + COL_RESET;

    //private static String FORMAT_SUBSEQUENT = "    -  %s";


    //// constructor

    public MyLog(File logFile, LogLevel logLevel, LogLevel printLevel)
    {
        //this.loggingLevel = logLevel;
        MyLog.printLevel = printLevel;
    }


    //// public methods

    public void error(String className, String msg)
    {
        //        if (loggingLevel.ordinal() >= Level.error.ordinal())
        //        {
        //            logError(className, msg);
        //        }
        if (printLevel.ordinal() > LogLevel.error.ordinal())
        {
            printError(className, msg);
        }
    }

    public void warning(String className, String msg)
    {
        //        if (loggingLevel.ordinal() >= Level.warning.ordinal())
        //        {
        //            logWarning(className, msg);
        //        }
        if (printLevel.ordinal() >= LogLevel.warning.ordinal())
        {
            printWarning(className, msg);
        }
    }

    public void info(String className, String msg)
    {
        //        if (loggingLevel.ordinal() >= Level.info.ordinal())
        //        {
        //            logInfo(className, msg);
        //        }
        if (printLevel.ordinal() >= LogLevel.info.ordinal())
        {
            printInfo(className, msg);
        }
    }

    public void verbose(String className, String msg)
    {
        //        if (loggingLevel.ordinal() >= Level.verbose.ordinal())
        //        {
        //            logVerbose(className, msg);
        //        }
        if (printLevel.ordinal() >= LogLevel.verbose.ordinal())
        {
            printVerbose(className, msg);
        }
    }

    public void debug(String className, String msg)
    {
        if (printLevel.ordinal() >= LogLevel.debug.ordinal())
        {
            printDebug(className, msg);
        }
    }


    //// private methods

    // logging methods

    //    private void logError(String className, String msg)
    //    {
    //        //TODO: log
    //    }
    //
    //    private void logWarning(String className, String msg)
    //    {
    //        //TODO: log
    //    }
    //
    //    private void logInfo(String className, String msg)
    //    {
    //        //TODO: log
    //    }
    //
    //    private void logVerbose(String className, String msg)
    //    {
    //        //TODO: log
    //    }

    // debugging methods

    private void printError(String className, String msg)
    {
        System.out.println(String.format(FORMAT_ERROR_MSG,
                                         DateTimeUtil.getLogTime(),
                                         className,
                                         msg));
    }

    private void printWarning(String className, String msg)
    {
        System.out.println(String.format(FORMAT_WARNING_MSG,
                                         DateTimeUtil.getLogTime(),
                                         className,
                                         msg));
    }

    private void printInfo(String className, String msg)
    {
        System.out.println(String.format(FORMAT_INFO_MSG,
                                         DateTimeUtil.getLogTime(),
                                         className,
                                         msg));
    }

    private void printVerbose(String className, String msg)
    {
        System.out.println(String.format(FORMAT_VERBOSE_MSG,
                                         DateTimeUtil.getLogTime(),
                                         className,
                                         msg));
    }

    private void printDebug(String className, String msg)
    {
        System.out.println(String.format(FORMAT_DEBUG_MSG,
                                         DateTimeUtil.getLogTime(),
                                         className,
                                         msg));
    }

    //    private void printSubsequent(String className, String msg)
    //    {
    //        System.out.println(String.format(FORMAT_SUBSEQUENT, msg));
    //    }


}
