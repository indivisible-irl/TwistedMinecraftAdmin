package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class StopCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "stop";

    private static final String HELP_SHORT = "Stops a running server instance.";
    private static final String HELP_LONG = "Usage :: admin stop <nick>\n"
            + "    Stops a running server. Does not close the screen.\n"
            + "    Type 'admin list' to see all available nicks.";


    //// constructor

    public StopCmd()
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
