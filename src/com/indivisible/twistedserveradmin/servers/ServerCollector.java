package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.indivisible.twistedserveradmin.commands.HelpCmd;
import com.indivisible.twistedserveradmin.files.FileGetter;
import com.indivisible.twistedserveradmin.files.FileIterator;


public class ServerCollector
{

    //// data

    private List<File> rootFolders;
    private List<Server> allServers;
    private File serverRootsList;


    //// constructor & init

    public ServerCollector()
    {
        this.serverRootsList = FileGetter.getServerListFile();
        if (serverRootsList == null)
        {
            HelpCmd.printServerListNotExists();
            System.exit(11);
        }
        else
        {
            initRootFolders();
            collectServers();
            sortServers();
        }
    }

    private void initRootFolders()
    {
        rootFolders = new ArrayList<File>();
        FileIterator iter = null;
        try
        {
            //System.out.println(String.format(" === Iterating %s", serverRootsListPath));
            iter = new FileIterator(serverRootsList);
        }
        catch (IOException e)
        {
            System.out.println(" === Error while reading server list :: IOException : "
                    + serverRootsList);
            e.printStackTrace();
            System.exit(12);
        }
        if (iter != null)
        {
            String line;
            File folder;
            while (iter.hasNext())
            {
                line = iter.next().trim();
                if (line.equals(""))
                {
                    continue;
                }
                else if (line.startsWith("#"))
                {
                    continue;
                }
                else
                {
                    folder = new File(line);
                    if (folder.exists() && folder.isDirectory() && folder.canRead())
                    {
                        rootFolders.add(folder);
                    }
                    else
                    {
                        System.out.println(" === Not a folder or inaccessible: " + line);
                    }
                }
            }
        }
    }

    private void collectServers()
    {
        if (rootFolders != null && !rootFolders.isEmpty())
        {
            allServers = new ArrayList<Server>();
            for (File folder : rootFolders)
            {
                //System.out.println("    -- Working in: " + folder.getAbsolutePath());
                File[] dirs = getDirectories(folder);
                if (dirs != null && dirs.length > 0)
                {
                    Server server;
                    for (File possibleServer : dirs)
                    {
                        server = checkForServer(possibleServer);
                        if (server != null)
                        {
                            allServers.add(server);
                        }
                    }
                }
                else
                {
                    System.out.println("   -- Encountered error getting directories");
                }
            }

        }
        else
        {
            System.out.println(" === No root folders to work with. Quitting.");
            System.exit(13);
        }
    }

    private void sortServers()
    {
        Collections.sort(allServers, new ServerComparator());
    }


    //// public methods

    /**
     * Retrieve List of Servers
     * 
     * @return
     */
    public List<Server> getServers()
    {
        return allServers;
    }

    /**
     * Test has collected usable Servers
     * 
     * @return
     */
    public boolean hasServers()
    {
        return allServers != null && allServers.size() > 0;
    }


    //// private methods

    /**
     * Gather all the directories from a folder
     * 
     * @param rootFolder
     * @return
     */
    private File[] getDirectories(File folder)
    {
        return folder.listFiles(new FileFilter()
            {

                @Override
                public boolean accept(File file)
                {
                    return file.isDirectory() && file.canRead();
                }
            });
    }

    /**
     * Check if a given directory contains a valid Minecraft Server and
     * initialise it as such.
     * 
     * @param possibleServer
     * @return Returns null on error or not a Minecraft Server
     */
    private Server checkForServer(File possibleServer)
    {
        Server server = new Server(possibleServer);
        if (server.isServer())
        {
            return server;
        }
        else
        {
            return null;
        }
    }
}
