package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class ListCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "list";

    private static final String HELP_SHORT = "Lists all available server instances (optional filter)";
    private static final String HELP_LONG = "Usage :: admin list [string]\n"
            + "    Displays a list of all available nicks.\n"
            + "    [string] parameter is optional and used to filter results";


    //// constructor

    public ListCmd()
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
