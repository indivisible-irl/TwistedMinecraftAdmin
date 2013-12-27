package com.indivisible.twistedserveradmin.files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.servers.Server;


public class ServerCollector
{

    //// data

    private List<File> rootFolders;
    private List<Server> allServers;
    private String serverRootsListPath;

    public static String TEST_ROOTS_PATH = "/home/indiv/dev/twisted/Minecraft/ServerLoop/SERVERS/servers.list";


    //// constructor & init

    public ServerCollector(String serverRootListPath)
    {
        this.serverRootsListPath = serverRootListPath;
        initRootFolders();
        collectServers();
    }

    private void initRootFolders()
    {
        rootFolders = new ArrayList<File>();
        FileIterator iter = null;
        try
        {
            //System.out.println(String.format(" === Iterating %s", serverRootsListPath));
            iter = new FileIterator(serverRootsListPath);
        }
        catch (IOException e)
        {
            System.out.println(" === Error while reading server list: "
                    + serverRootsListPath);
            System.exit(1);
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
                        if (server != null && server.hasProperties())
                        {
                            //System.out.println("Adding server: "
                            //        + server.getProperties().getServerName());
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
            System.exit(2);
        }
    }


    //// public methods

    public List<Server> getServers()
    {
        return allServers;
    }


    //// private methods

    /**
     * Gather all the directories from a folder
     * 
     * @param rootFolder
     * @return
     */
    private File[] getDirectories(File rootFolder)
    {
        return rootFolder.listFiles(new FileFilter()
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
