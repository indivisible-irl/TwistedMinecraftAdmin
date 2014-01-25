package com.indivisible.twistedserveradmin.system;

import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.commands.CmdHandeler;
import com.indivisible.twistedserveradmin.files.FileGetter;


public class Main
{

    public static void main(String[] argsArray)
    {
        // ensure needed files exist
        boolean rootFilesExist = FileGetter.ensureRootFilesExist();
        if (!rootFilesExist)
        {
            System.exit(1);
        }
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


    private static List<String> makeArgsList(String[] argsArray)
    {
        List<String> argsList = new ArrayList<String>();
        for (String arg : argsArray)
        {
            argsList.add(arg.toLowerCase());
        }
        return argsList;
    }


    /*
     * Status codes:
     * 
     * 0  - all ok
     * 1  - failed ensuring root files exist
     * 2  - failed to parse command properly
     * 
     * 10 - error reading/creating root files
     * 11 - error reading server list file (ServerCollector constructor)
     * 12 - error reading server list file (IOException)
     * 13 - no folders to work with
     */
}
