package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.commands.HelpCmd;
import com.indivisible.twistedserveradmin.files.FileGetter;
import com.indivisible.twistedserveradmin.files.FileIterator;
import com.indivisible.twistedserveradmin.query.QueryPerformer;
import com.indivisible.twistedserveradmin.system.Main;


/**
 * Class to hold, maintain and manage a List of Servers
 * 
 * @author indiv
 */
public class ServerManager
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private List<File> rootFolders;
    private List<MinecraftServer> masterServerList;
    private List<MinecraftServer> workingServerList;
    private File serverRootsList;

    private static final String TAG = "ServerManager";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Create a new ServerCollector and populate all available Servers.
     */
    public ServerManager()
    {
        this.serverRootsList = FileGetter.getServerListFile();
        if (serverRootsList == null)
        {
            HelpCmd.printServerListNotExists();
            System.exit(11);
        }
        refreshServerList();
    }

    /**
     * Read the root folders file and collect any usable folders.
     * 
     * @return
     */
    private boolean gatherRootFolders()
    {
        rootFolders = new ArrayList<File>();
        FileIterator iter = null;
        try
        {
            Main.myLog.debug(TAG,
                             String.format("Iterating %s",
                                           serverRootsList.getAbsolutePath()));
            iter = new FileIterator(serverRootsList);
        }
        catch (IOException e)
        {
            Main.myLog.error(TAG, "Error while reading server list :: IOException : "
                    + serverRootsList);
            e.printStackTrace();
            System.exit(12);
            return false;
        }
        if (iter != null)
        {
            Main.myLog.debug(TAG,
                             "Starting iteration of: "
                                     + serverRootsList.getAbsolutePath());
            String line;
            File folder;
            if (!iter.hasNext())
            {
                Main.myLog.error(TAG, "No lines found in file.");
                return false;
            }
            while (iter.hasNext())
            {
                line = iter.next().trim();
                Main.myLog.debug(TAG, "Testing line:\n\t[" + line + "]");
                folder = new File(line);
                if (folder.exists() && folder.isDirectory() && folder.canRead())
                {
                    Main.myLog.debug(TAG, "Is valid folder: " + folder.getAbsolutePath());
                    rootFolders.add(folder);
                }
                else
                {
                    Main.myLog.warning(TAG, "Not a folder or inaccessible: " + line);
                }
            }
        }
        if (rootFolders.isEmpty())
        {
            Main.myLog.error(TAG, "No root folders found.");
            return false;
        }
        else
        {
            Main.myLog.info(TAG,
                            String.format("%s root folders found.", rootFolders.size()));
            return true;
        }
    }

    /**
     * Collects all valid Minecraft Server instances. <br />
     * Returns true for success and false on failure or no results.
     * 
     * @return
     */
    private boolean collectServers()
    {
        if (rootFolders != null && !rootFolders.isEmpty())
        {
            masterServerList = new ArrayList<MinecraftServer>();
            for (File folder : rootFolders)
            {
                //System.out.println("    -- Working in: " + folder.getAbsolutePath());
                File[] dirs = FileGetter.getDirectories(folder);
                if (dirs != null && dirs.length > 0)
                {
                    MinecraftServer server;
                    for (File possibleServer : dirs)
                    {
                        server = checkForServer(possibleServer);
                        if (server != null)
                        {
                            masterServerList.add(server);
                        }
                    }
                }
                else
                {
                    Main.myLog.error(TAG,
                                     "Error getting directories for Server collection");
                }
            }
            if (masterServerList.size() > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            System.out.println("No root folders to work with. Quitting...");
            System.exit(13);
            return false;
        }
    }

    ///////////////////////////////////////////////////////
    ////    sorts
    ///////////////////////////////////////////////////////

    public void sortServersByName()
    {
        workingServerList = ServerSort.nameSort(masterServerList);
    }

    public void sortServersByVersion()
    {
        workingServerList = ServerSort.versionSort(masterServerList);
    }

    public void sortServersByStatus()
    {
        workingServerList = ServerSort.statusSort(masterServerList);
    }

    public void sortServersByPopularity()
    {
        workingServerList = ServerSort.statusSort(masterServerList);
    }


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Test has collected usable Servers
     * 
     * @return
     */
    public boolean hasServers()
    {
        return masterServerList != null && masterServerList.size() > 0;
    }

    /**
     * Refresh the List of Servers.
     * 
     * @return
     */
    public boolean refreshServerList()
    {
        if (!gatherRootFolders())
        {
            return false;
        }
        if (collectServers())
        {
            Main.myLog.info(TAG, "sucessful server list refresh");
            return true;
        }
        else
        {
            Main.myLog.error(TAG, "failed to refresh server list");
            return false;
        }
    }

    /**
     * Query all Servers
     * 
     * @return
     */
    public boolean refreshServerQueries()
    {
        if (hasServers())
        {
            return queryAllServers();
        }
        return false;
    }

    /**
     * Retrieve List of all Servers.
     * 
     * @return
     */
    public List<MinecraftServer> getServersAll()
    {
        workingServerList = new ArrayList<MinecraftServer>();
        for (MinecraftServer server : masterServerList)
        {
            workingServerList.add(server);
        }
        return workingServerList;
    }

    /**
     * Get a List of only the Servers marked to be shown by default.
     * 
     * @return
     */
    public List<MinecraftServer> getServersMonitored()
    {
        workingServerList = new ArrayList<MinecraftServer>();
        for (MinecraftServer server : masterServerList)
        {
            if (server.isMonitoredServer())
            {
                workingServerList.add(server);
            }
        }
        return workingServerList;
    }

    /**
     * Get a sorted List of only Online Servers.
     * 
     * @return
     */
    public List<MinecraftServer> getServersOnline()
    {
        workingServerList = new ArrayList<MinecraftServer>();
        for (MinecraftServer server : masterServerList)
        {
            if (server.isOnline())
            {
                workingServerList.add(server);
            }
        }
        return workingServerList;
    }

    /**
     * Retrieve the subset of Servers last requested.
     * 
     * @return
     */
    public List<MinecraftServer> getWorkingServerList()
    {
        return workingServerList;
    }


    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * Check if a given directory contains a valid Minecraft Server and
     * initialise it as such.
     * 
     * @param possibleServer
     * @return Returns null on error or not a Minecraft Server
     */
    private MinecraftServer checkForServer(File possibleServer)
    {
        MinecraftServer server = new MinecraftServer(possibleServer);
        if (server.isServer())
        {
            return server;
        }
        else
        {
            return null;
        }
    }

    /**
     * Perform a query on every server found.
     * 
     * @return
     */
    private boolean queryAllServers()
    {
        if (masterServerList == null || masterServerList.size() == 0)
        {
            if (!collectServers())
            {
                Main.myLog.error(TAG, "Unable to find any valid servers");
                return false;
            }
        }
        //TODO: status bar
        QueryPerformer queries = new QueryPerformer(masterServerList);
        masterServerList = queries
                .queryAllServers(QueryPerformer.DEFAULT_QUERY_VALID_AGE);
        return true;
    }
}
