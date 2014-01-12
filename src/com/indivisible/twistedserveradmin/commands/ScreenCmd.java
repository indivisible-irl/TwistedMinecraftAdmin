package com.indivisible.twistedserveradmin.commands;


public class ScreenCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "screen";

    //TODO: Work out commands and functionality for screen manipulation.
    private static final String HELP_TEXT = "Usage :: admin screen <command> [nick]\n"
            + "    Currently not in use.";

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
