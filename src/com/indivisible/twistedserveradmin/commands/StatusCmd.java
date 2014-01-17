package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class StatusCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "status";

    private static final String HELP_SHORT = "Displays status and info on servers (optional filter)";
    private static final String HELP_LONG = "Usage :: admin status [string]\n"
            + "    Displays a list of running servers.\n"
            + "    [string] paramater is optional and used to filter results by nick.";


    //// constructor

    public StatusCmd()
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
