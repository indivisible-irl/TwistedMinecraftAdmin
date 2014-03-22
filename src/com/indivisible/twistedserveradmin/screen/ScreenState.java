package com.indivisible.twistedserveradmin.screen;


public enum ScreenState
{

    //// Enums 
    attached,       // all ok, user connected
    detatched,      // all ok, no user connected
    multi,          // all ok, allows multiple users concurrently
    unreachable,    // uh oh
    dead,           // damn
    unknown;        // not covered by the above states


    //// data

    private static final String STATE_ATTACHED = "attached";
    private static final String STATE_DETATCHED = "detached";
    private static final String STATE_MULTI = "multi";
    private static final String STATE_UNREACHABLE = "unreachable";
    private static final String STATE_DEAD = "dead";

    public static final int MAX_LENGTH_STATE_SHORT = 4;
    public static final int MAX_LENGTH_STATE_LONG = 11;

    //// State returns

    public static ScreenState getScreenState(String rawStringState)
    {
        String strState = rawStringState.toLowerCase();
        if (strState.equals(STATE_ATTACHED))
        {
            return ScreenState.attached;
        }
        else if (strState.equals(STATE_DETATCHED))
        {
            return ScreenState.detatched;
        }
        else if (strState.equals(STATE_MULTI))
        {
            return ScreenState.multi;
        }
        else if (strState.equals(STATE_UNREACHABLE))
        {
            return ScreenState.unreachable;
        }
        else if (strState.equals(STATE_DEAD))
        {
            return ScreenState.dead;
        }
        else
        {
            System.out.println("!! Uncaught state: " + rawStringState);
            return ScreenState.unknown;
        }
    }

    //// String returns

    public static String getShortName(ScreenState state)
    {
        switch (state)
        {
            case attached:
                return "ATT";
            case detatched:
                return "det";
            case multi:
                return "mul";
            case unreachable:
                return "UNRE";
            case dead:
                return "DEAD";
            case unknown:
                return "unkn";
            default:
                return "!ERR";
        }
    }

    public static String getLongName(ScreenState state)
    {
        switch (state)
        {
            case attached:
                return "Attached";
            case detatched:
                return "Detached";
            case multi:
                return "Multi";
            case unreachable:
                return "Unreachable";
            case dead:
                return "Dead";
            case unknown:
                return "Unknown";
            default:
                return "ERROR";
        }
    }
}
