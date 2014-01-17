package com.indivisible.twistedserveradmin.servers;

import java.util.Comparator;


public class ServerComparator
        implements Comparator<Server>
{

    @Override
    public int compare(Server s0, Server s1)
    {
        int verComp = s0.getInfo().getVersion().compareTo(s1.getInfo().getVersion());
        if (verComp != 0)
        {
            return s0.getInfo().getNickname().compareTo(s1.getInfo().getNickname());
        }
        return verComp;
    }
}
