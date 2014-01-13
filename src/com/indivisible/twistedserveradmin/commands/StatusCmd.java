package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class StatusCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "status";

    private static final String HELP_TEXT = "Usage :: admin status [string]\n"
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
