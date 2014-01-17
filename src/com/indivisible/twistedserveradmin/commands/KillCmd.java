package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class KillCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "kill";

    private static final String HELP_SHORT = "Forcebly kills a running server. Use carefully!";
    private static final String HELP_LONG = "Usage :: admin kill <nick>\n"
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

    public String getShortHelp()
    {
        return HELP_SHORT;
    }

    public String getLongHelp(List<String> args)
    {
        return HELP_LONG;
    }


    //// invoke

    public boolean invoke(List<String> args)
    {
        return false;
    }

}
