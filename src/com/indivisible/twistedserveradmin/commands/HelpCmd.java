package com.indivisible.twistedserveradmin.commands;


public class HelpCmd
        extends Cmd
{

    private static final String NAME = "help";

    private static final String HELP_TEXT = "Usage :: admin help [command]\n"
            + "    Displays help on Twisted.cat's minecraft admin tool\n"
            + "    [command] parameter is used to display detailed help on the supplied command.\n"
            + "    If no command is entered (or no matching command found) general, summary help is displayed";

    private static final String HELP_SUMMARY = "  Twisted.cat's Minecraft Admin tool.\n"
            + "  Available commands:\n"
            + "      help     ::  Displays this help or detailed info on commands\n"
            + "      list     ::  Lists all available server nicks (optional filter)\n"
            + "      online   ::  Displays status and info on running servers (optional filter)\n"
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

    //// Command methods 

    public static void printHelp()
    {
        System.out.println(HELP_TEXT);
    }

    public static String getName()
    {
        return NAME;
    }

    public static boolean matchName(String test)
    {
        return NAME.equals(test);
    }

    //// public methods

    public static void invokeHelp(String[] args)
    {
        if (args.length <= 1)
        {
            printDefault();
        }
        else
        {
            String cmdParam = args[1];
            System.out.println("Invoke help for: " + args[1]);
            if (cmdParam.equalsIgnoreCase(HelpCmd.NAME))
            {
                HelpCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(ListCmd.getName()))
            {
                ListCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(OnlineCmd.getName()))
            {
                OnlineCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(StartCmd.getName()))
            {
                StartCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(StopCmd.getName()))
            {
                StopCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(RestartCmd.getName()))
            {
                RestartCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(KillCmd.getName()))
            {
                KillCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(SaveCmd.getName()))
            {
                SaveCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(BackupCmd.getName()))
            {
                BackupCmd.printHelp();
            }
            else if (cmdParam.equalsIgnoreCase(ScreenCmd.getName()))
            {
                ScreenCmd.printHelp();
            }
            else
            {
                System.out.println("! Invalid argument: " + cmdParam);
                HelpCmd.printSummary();
            }
        }
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

}
