package com.indivisible.twistedserveradmin.system;

import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.commands.CmdHandeler;


public class Main
{

    public static void main(String[] argsArray)
    {
        CmdHandeler cmds = new CmdHandeler();
        List<String> argsList = makeArgsList(argsArray);
        boolean result = cmds.processCommand(argsList);
        if (result)
        {
            System.exit(0);
        }
        else
        {
            System.exit(1);
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
}
