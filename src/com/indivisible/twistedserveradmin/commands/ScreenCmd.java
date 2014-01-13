package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class ScreenCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "screen";

    //TODO: Work out commands and functionality for screen manipulation.
    private static final String HELP_TEXT = "Usage :: admin screen <command> [nick]\n"
            + "    Currently not in use.";


    //// constructor

    public ScreenCmd()
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
