package com.indivisible.twistedserveradmin.commands;


public class ListCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "list";

    private static final String HELP_TEXT = "Usage :: admin list [string]\n"
            + "    Displays a list of all available nicks.\n"
            + "    [string] parameter is optional and used to filter results";

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
