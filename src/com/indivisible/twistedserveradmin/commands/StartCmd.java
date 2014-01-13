package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class StartCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "start";

    private static final String HELP_TEXT = "Usage :: admin start <nick>\n"
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
