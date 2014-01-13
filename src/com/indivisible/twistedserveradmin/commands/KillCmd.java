package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class KillCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "kill";

    private static final String HELP_TEXT = "Usage :: admin kill <nick>\n"
            + "    Forcebly kills a running server instance.\n"
            + "    Use only for unresponsive servers. World save is not guaranteed.\n"
            + "    Type 'admin online' to see a list of running servers";


    //// constructor

    public KillCmd()
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
