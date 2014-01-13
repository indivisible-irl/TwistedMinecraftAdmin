package com.indivisible.twistedserveradmin.commands;

import java.util.List;


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
     * Print information on usage and syntax for the Cmd.<br/>
     * Returns true if the desired help module was found cleanly. False if not
     * found (default help will be printed)
     * 
     * @param args
     * @return
     */
    public boolean printHelp(List<String> args);

    /**
     * Trigger the Cmd's functionality supplying any arguments <br/>
     * (without cmd.NAME)
     * 
     * @param args
     * @return boolean was handled correctly
     */
    public boolean invoke(List<String> args);

}
