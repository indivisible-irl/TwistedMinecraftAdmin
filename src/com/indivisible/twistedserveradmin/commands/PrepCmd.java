package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class PrepCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "prep";

    private static final String HELP_SHORT = "Prepare a server instance for use.";
    private static final String HELP_LONG = "Usage :: admin prep\n"
            + "    Prepares a directory for use with this Admin tool.\n"
            + "    Checks for a valid Server instance and creates necessary files.";


    //// constructor

    public PrepCmd()
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
