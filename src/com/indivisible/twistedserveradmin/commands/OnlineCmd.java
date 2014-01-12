package com.indivisible.twistedserveradmin.commands;


public class OnlineCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "online";

    private static final String HELP_TEXT = "Usage :: admin online [string]\n"
            + "    Displays a list of running servers.\n"
            + "    [string] paramater is optional and used to filter results by nick.";

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
