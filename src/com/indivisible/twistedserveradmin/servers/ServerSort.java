package com.indivisible.twistedserveradmin.servers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ServerSort
{

    public static List<MinecraftServer> versionSort(List<MinecraftServer> servers)
    {
        Collections.sort(servers, new ServerVersionSort());
        return servers;
    }

    public static List<MinecraftServer> nameSort(List<MinecraftServer> servers)
    {
        Collections.sort(servers, new ServerNameSort());
        return servers;
    }

    public static List<MinecraftServer> statusSort(List<MinecraftServer> servers)
    {
        Collections.sort(servers, new ServerStatusSort());
        return servers;
    }

    public static List<MinecraftServer> popularitySort(List<MinecraftServer> servers)
    {
        Collections.sort(servers, new ServerPopularitySort());
        return servers;
    }


    //// Sorting Comparators

    /**
     * Sorts Server List by name. Case insensitive.
     * 
     * @author indiv
     */
    private static class ServerNameSort
            implements Comparator<MinecraftServer>
    {

        @Override
        public int compare(MinecraftServer s1, MinecraftServer s2)
        {
            int comp = s1.getName().compareToIgnoreCase(s2.getName());
            if (comp == 0)
            {
                return s1.getServerStatus().compareTo(s2.getServerStatus());
            }
            return comp;
        }
    }

    /**
     * Sorts Server List by version then by status and finally by name.
     * 
     * @author indiv
     */
    private static class ServerVersionSort
            implements Comparator<MinecraftServer>
    {

        @Override
        public int compare(MinecraftServer s1, MinecraftServer s2)
        {
            int versComp = s1.getVersion().compareTo(s2.getVersion());
            if (versComp == 0)
            {
                int statusComp = s1.getServerStatus().compareTo(s2.getServerStatus());
                if (statusComp == 0)
                {
                    return s1.getName().compareTo(s2.getName());
                }
                return statusComp;
            }
            return versComp;
        }
    }

    /**
     * Sorts Server List by online status, then by version and finally by
     * name.
     * 
     * @author indiv
     * 
     */
    private static class ServerStatusSort
            implements Comparator<MinecraftServer>
    {

        @Override
        public int compare(MinecraftServer s1, MinecraftServer s2)
        {
            int statusComp = s1.getServerStatus().compareTo(s2.getServerStatus());
            if (statusComp == 0)
            {
                int verComp = s1.getVersion().compareTo(s2.getVersion());
                if (verComp == 0)
                {
                    return s1.getName().compareTo(s2.getName());
                }
                return verComp;
            }
            return statusComp;
        }
    }

    /**
     * Sorts Server List by highest people online then by name
     * 
     * @author indiv
     * 
     */
    private static class ServerPopularitySort
            implements Comparator<MinecraftServer>
    {

        @Override
        public int compare(MinecraftServer s1, MinecraftServer s2)
        {
            int statusComp = s1.getServerStatus().compareTo(s2.getServerStatus());
            if (statusComp == 0)
            {
                int playersComp = ((Integer) s1.getPlayersOnline())
                        .compareTo((Integer) s2.getPlayersOnline());
                if (playersComp == 0)
                {
                    return s1.getName().compareTo(s2.getName());
                }
                return playersComp;
            }
            return statusComp;
        }
    }


}
