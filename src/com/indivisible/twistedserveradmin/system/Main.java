package com.indivisible.twistedserveradmin.system;

import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.commands.CmdHandeler;
import com.indivisible.twistedserveradmin.files.FileGetter;
import com.indivisible.twistedserveradmin.logging.LogLevel;
import com.indivisible.twistedserveradmin.logging.MyLog;
import com.indivisible.twistedserveradmin.menu.MenuManager;

/**
 * Main class to be executed first. Can handle command line arguments or
 * invoke a menu interface.
 * 
 * @author indiv
 */
public class Main
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    //TODO: read log levels from settings
    public static MyLog myLog = new MyLog(null, LogLevel.off, LogLevel.debug);

    private static final String TAG = "Main";


    ///////////////////////////////////////////////////////
    ////    main
    ///////////////////////////////////////////////////////

    /**
     * Main method. Program entry point.
     * 
     * @param argsArray
     */
    public static void main(String[] argsArray)
    {
        // regardless of desired mode ensure needed files exist
        ensureRootFilesExist();

        // if no arguments supplied start Menu interface
        if (argsArray == null || argsArray.length == 0)
        {
            MenuManager menuManager = new MenuManager();
            menuManager.run();
        }
        // otherwise invoke desired action directly
        else
        {
            // gather and pass args to commands
            CmdHandeler cmds = new CmdHandeler();
            List<String> argsList = makeArgsList(argsArray);
            boolean result = cmds.processCommand(argsList);
            if (result)
            {
                System.exit(0);
            }
            else
            {
                System.exit(2);
            }
        }
    }

    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * Test for existence of necessary settings files. Attempt to create if
     * missing. Exit if cannot access or create them.
     */
    private static void ensureRootFilesExist()
    {
        boolean rootFilesExist = FileGetter.ensureRootFilesExist();
        if (!rootFilesExist)
        {
            System.exit(1);
        }
        else
        {
            Main.myLog.verbose(TAG, "");
        }
    }

    /**
     * Convert supplied array to ArrayList to enable dropping of args as we
     * traverse the invoked commands.
     * 
     * @param argsArray
     * @return
     */
    private static List<String> makeArgsList(String[] argsArray)
    {
        List<String> argsList = new ArrayList<String>();
        for (String arg : argsArray)
        {
            argsList.add(arg.toLowerCase());
        }
        return argsList;
    }


    ///////////////////////////////////////////////////////
    ////    status codes
    ///////////////////////////////////////////////////////

    //TODO: document status codes for release

    /*
     * Status codes:
     * 
     * 0  - all ok
     * 1  - failed ensuring root files exist
     * 2  - failed to parse command properly. may have given default response or help.
     * 
     * 10 - error reading/creating root files
     * 11 - error reading server list file (ServerCollector constructor)
     * 12 - error reading server list file (IOException)
     * 13 - no folders to work with
     * 
     * 
     * 101 - makeChoices() not overridden by a child of ServerListMenu
     */
}
