package com.indivisible.twistedserveradmin.commands;

import java.util.List;


public class ScreenCmd
        implements ICmd
{

    //// Help Strings

    private static final String NAME = "screen";

    private static final String HELP_SHORT = "Performs actions relating to screen management.";
    private static final String HELP_LONG = "Usage :: admin screen <command> [nick]\n"
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
        if (args == null || args.size() == 0)
        {
            listScreens();
            return true;
        }
        else if (args.size() == 1)
        {
            //RET: ScreenCmd argument handling
            throw new IllegalArgumentException(
                    "Haven't written this bit yet. Come back later");
        }


        return false;
    }


    //// actions

    private boolean listScreens()
    {

        return false;
    }

}
