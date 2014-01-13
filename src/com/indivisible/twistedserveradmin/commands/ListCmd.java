package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class ListCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "list";

    private static final String HELP_TEXT = "Usage :: admin list [string]\n"
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
