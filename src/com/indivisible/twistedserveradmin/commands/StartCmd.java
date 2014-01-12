package com.indivisible.twistedserveradmin.commands;


public class StartCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "start";

    private static final String HELP_TEXT = "Usage :: admin start <nick>\n"
            + "    Starts up a server (if offline) and creates screen if needed.\n"
            + "    Type 'admin list' to see all available nicks.";

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
}
