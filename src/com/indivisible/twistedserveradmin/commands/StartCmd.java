package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class StartCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "start";

    private static final String HELP_SHORT = "Starts an offline server (and screen if necessary).";
    private static final String HELP_LONG = "Usage :: admin start <nick>\n"
            + "    Starts up a server (if offline) and creates screen if needed.\n"
            + "    Type 'admin list' to see all available nicks.";


    //// constructor

    public StartCmd()
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
