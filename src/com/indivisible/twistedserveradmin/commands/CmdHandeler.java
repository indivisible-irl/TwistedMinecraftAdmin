package com.indivisible.twistedserveradmin.commands;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CmdHandeler
{

    //// data

    private Map<String, ICmd> cmds = null;
    private HelpCmd helpCmd = null;


    //// constructor

    public CmdHandeler()
    {
        // linked hashmap for both ordered and instant access.
        cmds = new LinkedHashMap<String, ICmd>();
        helpCmd = new HelpCmd();

        // populate with all commands
        ICmd cmd = null;
        cmds.put(helpCmd.getName(), helpCmd);   // help
        cmd = new StatusCmd();
        cmds.put(cmd.getName(), cmd);           // status
        cmd = new ListCmd();
        cmds.put(cmd.getName(), cmd);           // list
        cmd = new ScreenCmd();
        cmds.put(cmd.getName(), cmd);           // screen
        cmd = new StartCmd();
        cmds.put(cmd.getName(), cmd);           // start
        cmd = new StopCmd();
        cmds.put(cmd.getName(), cmd);           // stop
        cmd = new RestartCmd();
        cmds.put(cmd.getName(), cmd);           // restart
        cmd = new SaveCmd();
        cmds.put(cmd.getName(), cmd);           // save
        cmd = new BackupCmd();
        cmds.put(cmd.getName(), cmd);           // backup
        cmd = new PrepCmd();
        cmds.put(cmd.getName(), cmd);           // prepare server

        helpCmd.setCmds(cmds);
    }


    //// public methods

    public boolean processCommand(List<String> args)
    {
        if (args == null || args.size() == 0)
        {
            helpCmd.printDefault();
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
        if (cmds.containsKey(name))
        {
            return cmds.get(name);
        }
        System.out.println("!! No Cmd found with the name: " + name);
        return null;
    }
}
