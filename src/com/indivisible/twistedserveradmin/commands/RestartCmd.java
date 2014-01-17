package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class RestartCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "restart";

    private static final String HELP_SHORT = "Restarts a running Sever. (Starts if offline)";
    private static final String HELP_LONG = "Usage :: admin restart <nick>\n"
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
