package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class BackupCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "backup";

    private static final String HELP_TEXT = "Usage :: admin backup <nick>\n"
            + "    Performs a full backup of a server and saves to .bkup/<nick> folder\n"
            + "    Runs in it's own screen called 'backup'. Attach to it to view output or monitor progress\n"
            + "    Type 'admin list' to see all available nicks";

    //// constructors

    public BackupCmd()
    {}

    //// Command methods

    public String getName()
    {
        return NAME;
    }

    public boolean matchName(String test)
    {
        return NAME.equals(test);
    }

    public boolean printHelp(List<String> args)
    {
        System.out.println(HELP_TEXT);
        return true;
    }


    //// invoke

    public boolean invoke(List<String> args)
    {
        return false;
    }

}
