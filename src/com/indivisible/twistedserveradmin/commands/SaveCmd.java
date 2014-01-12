package com.indivisible.twistedserveradmin.commands;


public class SaveCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "save";

    private static final String HELP_TEXT = "Usage :: admin save <nick>\n"
            + "    Saves a servers world and state. (Invokes 'save-all')\n"
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
