package com.indivisible.twistedserveradmin.commands;

import java.io.File;
import java.util.List;
import javax.tools.JavaFileManager.Location;
import com.indivisible.twistedserveradmin.servers.ServerCollector;


public class HelpCmd
        implements ICmd
{

    //// data

    private static final String NAME = "help";

    private static final String HELP_TEXT = "Usage :: admin help [command]\n"
            + "    Displays help on Twisted.cat's minecraft admin tool\n"
            + "    [command] parameter is used to display detailed help on the supplied command.\n"
            + "    If no command is entered (or no matching command found) general, summary help is displayed";

    private static final String HELP_SUMMARY = "  Twisted.cat's Minecraft Admin tool.\n"
            + "  Available commands:\n"
            + "      help     ::  Displays this help or detailed info on commands\n"
            + "      list     ::  Lists all available server nicks (optional filter)\n"
            + "      status   ::  Displays status and info on servers (optional filter)\n"
            + "      start    ::  Starts an offline server (and screen if necessary)\n"
            + "      stop     ::  Stops a running server.\n"
            + "      restart  ::  Stops and start a running server. Only starts if offline.\n"
            + "      kill     ::  Forcebly kills a running server. Use only on unresponsive servers.\n"
            + "      save     ::  Saves a running server's world. (Invokes 'save-all')\n"
            + "      backup   ::  Makes a full backup of a server and saves to approprite directory.\n"
            + "      screen   ::  Performs actions relating to screen management\n"
            + "\n  Type 'admin help <command>' for more detailed help on a command and it's usage.";

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
    private List<ICmd> cmds;


    //// constructor

    public HelpCmd()
    {}

    public void setCmds(List<ICmd> cmds)
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

    public boolean printHelp(List<String> args)
    {
        System.out.println(HELP_TEXT);
        return true;
    }


    //// print extended help methods

    public static void printDefault()
    {
        System.out.println(HELP_SUMMARY);
        System.out.println(HELP_INFO);
    }

    public static void printSummary()
    {
        System.out.println(HELP_SUMMARY);
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
        String str = String.format(HELP_SERVER_LIST_NOT_EXISTS, getServerListPath());
        System.out.println(str);
    }

    public static void printServerListIsEmpty()
    {
        String str = String.format(HELP_SERVER_LIST_EMPTY, getServerListPath());
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
                printSummary();
                return false;
            }
            else
            {
                args.remove(0);
                return matchingCmd.printHelp(args);
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

    private static String getServerListPath()
    {
        String jarPath = Location.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        File installFolder = new File(jarPath).getParentFile();
        return (new File(installFolder, ServerCollector.SERVER_LIST)).getAbsolutePath();
    }

}
