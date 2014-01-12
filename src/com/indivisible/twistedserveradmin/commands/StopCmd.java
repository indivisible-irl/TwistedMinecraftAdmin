package com.indivisible.twistedserveradmin.commands;


public class StopCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "stop";

    private static final String HELP_TEXT = "Usage :: admin stop <nick>\n"
            + "    Stops a running server. Does not close the screen.\n"
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
