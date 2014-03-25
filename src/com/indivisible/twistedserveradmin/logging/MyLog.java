package com.indivisible.twistedserveradmin.logging;

import java.io.File;
import com.indivisible.twistedserveradmin.util.DateTimeUtil;


/**
 * Class to handle all writes to log files, logging db and console debug/info
 * printing.
 * 
 * @author indiv
 * 
 */
public class MyLog
{

    //ASK: logging - use util.Logging?

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

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


    ///////////////////////////////////////////////////////
    ////    constructor
    ///////////////////////////////////////////////////////

    public MyLog(File logFile, LogLevel logLevel, LogLevel printLevel)
    {
        //this.loggingLevel = logLevel;
        MyLog.printLevel = printLevel;
    }


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Log an error message.
     * 
     * @param className
     * @param msg
     */
    public void error(String className, String msg)
    {
        if (printLevel.ordinal() > LogLevel.error.ordinal())
        {
            printError(className, msg);
        }
    }

    /**
     * Log a warning message.
     * 
     * @param className
     * @param msg
     */
    public void warning(String className, String msg)
    {
        if (printLevel.ordinal() >= LogLevel.warning.ordinal())
        {
            printWarning(className, msg);
        }
    }

    /**
     * Log an informational message.
     * 
     * @param className
     * @param msg
     */
    public void info(String className, String msg)
    {
        if (printLevel.ordinal() >= LogLevel.info.ordinal())
        {
            printInfo(className, msg);
        }
    }

    /**
     * Log a verbose message.
     * 
     * @param className
     * @param msg
     */
    public void verbose(String className, String msg)
    {
        if (printLevel.ordinal() >= LogLevel.verbose.ordinal())
        {
            printVerbose(className, msg);
        }
    }

    /**
     * Log a debugging message.
     * 
     * @param className
     * @param msg
     */
    public void debug(String className, String msg)
    {
        if (printLevel.ordinal() >= LogLevel.debug.ordinal())
        {
            printDebug(className, msg);
        }
    }


    ///////////////////////////////////////////////////////
    ////    console logging methods
    ///////////////////////////////////////////////////////

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
    //        //TODO: Better printing of multi-line messages
    //        System.out.println(String.format(FORMAT_SUBSEQUENT, msg));
    //    }


    ///////////////////////////////////////////////////////
    ////    TODO: database logging methods
    ///////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////
    ////    TODO: file logging methods
    ///////////////////////////////////////////////////////

    //    private void fileLogError(String className, String msg)
    //    {
    //        
    //    }
    //
    //    private void fileLogWarning(String className, String msg)
    //    {
    //        
    //    }
    //
    //    private void fileLogInfo(String className, String msg)
    //    {
    //        
    //    }
    //
    //    private void fileLogVerbose(String className, String msg)
    //    {
    //        
    //    }
    //
    //    private void fileLogDebug(String className, String msg)
    //    {
    //
    //    }

}
