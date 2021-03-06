package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class SaveCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "save";

    private static final String HELP_SHORT = "Saves a running server's world. (Invokes 'save-all')";
    private static final String HELP_LONG = "Usage :: admin save <nick>\n"
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
