package com.indivisible.twistedserveradmin.exceptions;

/**
 * Exception Class to indicate that a required program is not installed on the
 * running system's OS.
 * 
 * @author indiv
 * 
 */
public class MissingInstallException
        extends RuntimeException
{

    private static final long serialVersionUID = 1L;
    private static final String message = "%s not installed or not accessible.\nPlease ensure it is installed and defined on your System Path";

    public MissingInstallException()
    {}

    public MissingInstallException(String cmdName)
    {
        super(String.format(message, cmdName));
    }

    public MissingInstallException(Throwable throwable)
    {
        super(throwable);
    }

    public MissingInstallException(String cmdName, Throwable throwable)
    {
        super(String.format(message, cmdName), throwable);
    }

}
