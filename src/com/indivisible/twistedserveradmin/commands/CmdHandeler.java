package com.indivisible.twistedserveradmin.commands;

import java.util.ArrayList;
import java.util.List;


public class CmdHandeler
{

    //// data

    private List<ICmd> cmds = null;
    private HelpCmd helpCmd = null;


    //// constructor

    public CmdHandeler()
    {
        cmds = new ArrayList<ICmd>();
        helpCmd = new HelpCmd();
        // populate with all commands
        // order of addition should reflect usage for improved performance/response times
        cmds.add(helpCmd);
        cmds.add(new StatusCmd());
        cmds.add(new ListCmd());
        cmds.add(new ScreenCmd());
        cmds.add(new StartCmd());
        cmds.add(new StopCmd());
        cmds.add(new RestartCmd());
        cmds.add(new SaveCmd());
        cmds.add(new BackupCmd());

        helpCmd.setCmds(cmds);
    }


    //// public methods

    public boolean processCommand(List<String> args)
    {
        if (args == null || args.size() == 0)
        {
            HelpCmd.printDefault();
            return true;
        }
        else
        {
            ICmd matchingCmd = getCmdByName(args.get(0));
            if (matchingCmd == null)
            {
                HelpCmd.printErrorOnCmd();
                return false;
            }
            else
            {
                args.remove(0);
                return matchingCmd.invoke(args);
            }
        }
    }


    //// private methods

    private ICmd getCmdByName(String name)
    {
        for (ICmd cmd : cmds)
        {
            if (cmd.matchName(name))
            {
                return cmd;
            }
        }
        System.out.println("No Cmd found with the name: " + name);
        return null;
    }
}
