package com.indivisible.twistedserveradmin.commands;


public class KillCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "kill";

    private static final String HELP_TEXT = "Usage :: admin kill <nick>\n"
            + "    Forcebly kills a running server instance.\n"
            + "    Use only for unresponsive servers. World save is not guaranteed.\n"
            + "    Type 'admin online' to see a list of running servers";

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
