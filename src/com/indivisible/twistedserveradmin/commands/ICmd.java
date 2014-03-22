package com.indivisible.twistedserveradmin.commands;

import java.util.List;

//ASK: Make an abstract Cmd class with most of these methods instead of an interface?

public interface ICmd
{

    /**
     * Retrieve the identifying name for the Cmd.
     * 
     * @return String
     */
    public String getName();

    /**
     * Test if the supplied string matches the Cmd's identifier.
     * 
     * @param test
     * @return String is a match
     */
    public boolean matchName(String test);

    /**
     * Get a shortened version of Cmd's help.
     * 
     * @return
     */
    public String getShortHelp();

    /**
     * Print information on usage and syntax for the Cmd.<br/>
     * Returns true if the desired help module was found cleanly. False if not
     * found (default help will be printed)
     * 
     * @param args
     * @return
     */
    public String getLongHelp(List<String> args);

    /**
     * Trigger the Cmd's functionality supplying any arguments <br/>
     * (without cmd.NAME)
     * 
     * @param args
     * @return boolean was handled correctly
     */
    public boolean invoke(List<String> args);

}
