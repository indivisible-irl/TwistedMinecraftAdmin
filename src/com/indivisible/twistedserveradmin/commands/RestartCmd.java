package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class RestartCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "restart";

    private static final String HELP_TEXT = "Usage :: admin restart <nick>\n"
            + "    Restarts a running server (or starts if offline).\n"
            + "    Type 'admin online' to see a list of running servers";


    //// constructor

    public RestartCmd()
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
