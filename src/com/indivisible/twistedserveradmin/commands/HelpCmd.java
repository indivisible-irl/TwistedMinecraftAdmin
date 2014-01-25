package com.indivisible.twistedserveradmin.commands;

import java.util.List;
import java.util.Map;
import com.indivisible.twistedserveradmin.files.FileGetter;


public class HelpCmd
        implements ICmd
{

    //// data

    private static final String NAME = "help";

    private static final String HELP_SHORT = "Displays this help or detailed info on commands.";
    private static final String HELP_LONG = "Usage :: admin help [command]\n"
            + "    Displays help on Twisted.cat's minecraft admin tool\n"
            + "    [command] parameter is used to display detailed help on the supplied command.\n"
            + "    If no command is entered (or no matching command found) general, summary help is displayed";

    private static final String HELP_CMDS_START = "  Twisted.cat's Minecraft Admin tool.\n"
            + "  Available commands:\n";
    private static final String HELP_CMDS_FORMAT = "    %s  ::  %s";
    private static final String HELP_CMDS_END = "\n  Type 'admin help <command>' for more detailed help on a command and it's usage.";

    private static final String HELP_INFO = "\n"
            + "  Written by indivisible for the Twisted.cat Minecraft servers.\n"
            + "  To get support or contact the dev (indivisible):\n"
            + "      Email: indivisible@twisted.cat | support@twisted.cat\n"
            + "      Web:   http://twisted.cat";

    private static final String HELP_SERVER_LIST_NOT_EXISTS = "Error: No server root list found!\n"
            + "Creating a file called 'servers.list' at this tool's location.\n"
            + "Please add Server root paths to this file:\n\t%s";

    private static final String HELP_SERVER_LIST_EMPTY = "Error: No Server root paths found.\n"
            + "Please add all paths to the folders containing your Minecraft Servers.\n"
            + "This file is located at:\n\t%s";

    // collection of all commands registered
    private Map<String, ICmd> cmds;


    //// constructor

    public HelpCmd()
    {}

    public void setCmds(Map<String, ICmd> cmds)
    {
        this.cmds = cmds;
    }

    //// Command methods 

    public String getName()
    {
        return NAME;
    }

    public boolean matchName(String test)
    {
        return NAME.equals(test);
    }

    public String getShortHelp()
    {
        return HELP_SHORT;
    }

    public String getLongHelp(List<String> args)
    {
        return HELP_LONG;
    }


    //// print extended help methods

    public void printDefault()
    {
        printCmdsSummary();
        System.out.println(HELP_INFO);
    }

    public void printCmdsSummary()
    {
        System.out.println(HELP_CMDS_START);
        for (ICmd cmd : cmds.values())
        {
            System.out.println(String.format(HELP_CMDS_FORMAT,
                                             cmd.getName(),
                                             cmd.getShortHelp()));
        }
        System.out.println(HELP_CMDS_END);
    }

    public static void printInfo()
    {
        System.out.println(HELP_INFO);
    }

    public static void printErrorOnCmd()
    {
        System.out.println("!! Unable to process command correctly.");
    }

    public static void printServerListNotExists()
    {
        String str = String.format(HELP_SERVER_LIST_NOT_EXISTS, FileGetter
                .getServerListFile().getAbsoluteFile());
        System.out.println(str);
    }

    public static void printServerListIsEmpty()
    {
        String str = String.format(HELP_SERVER_LIST_EMPTY, FileGetter.getServerListFile()
                .getAbsoluteFile());
        System.out.println(str);
    }


    //// invoke

    /**
     * Return true on successful handling, false if not.
     */
    public boolean invoke(List<String> args)
    {
        if (args == null || args.size() == 0)
        {
            printDefault();
            return true;
        }
        else
        {
            ICmd matchingCmd = getCmdByName(args.get(0));
            if (matchingCmd == null)
            {
                printErrorOnCmd();
                printCmdsSummary();
                return false;
            }
            else
            {
                args.remove(0);
                String cmdHelp = matchingCmd.getLongHelp(args);
                System.out.println(cmdHelp);
                return true;
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
        System.out.println("No Cmd found with the name: " + name);
        return null;
    }


}
