package com.indivisible.twistedserveradmin.commands;


public class BackupCmd
        extends Cmd
{

    //// Help Strings

    private static final String NAME = "backup";

    private static final String HELP_TEXT = "Usage :: admin backup <nick>\n"
            + "    Performs a full backup of a server and saves to .bkup/<nick> folder\n"
            + "    Runs in it's own screen called 'backup'. Attach to it to view output or monitor progress\n"
            + "    Type 'admin list' to see all available nicks";

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
