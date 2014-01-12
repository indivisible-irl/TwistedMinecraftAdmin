package com.indivisible.twistedserveradmin.commands;


public class RestartCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "restart";

    private static final String HELP_TEXT = "Usage :: admin restart <nick>\n"
            + "    Restarts a running server (or starts if offline).\n"
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
