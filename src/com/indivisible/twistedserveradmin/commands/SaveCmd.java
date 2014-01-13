package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class SaveCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "save";

    private static final String HELP_TEXT = "Usage :: admin save <nick>\n"
            + "    Saves a servers world and state. (Invokes 'save-all')\n"
            + "    Type 'admin online' to see a list of running servers";


    //// constructor

    public SaveCmd()
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
