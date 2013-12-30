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

    public static final String SERVER_LIST = "servers.list";


    //// constructor & init

    public ServerCollector()
    {
        this.serverRootsListPath = getServerListFilePath();
        if (serverRootsListPath != null)
        {
            initRootFolders();
            collectServers();
        }
        else
        {
            System.out.println(" === No accessible server list. Quitting.");
            System.exit(2);
        }
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
            System.out.println(" === Error while reading server list :: IOException : "
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
     * Get the folder this jar is located in
     * 
     * @return
     */
    private File getInstalledFolder()
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation()
                .getPath();
        String parentPath = new File(path).getParent();
        return new File(parentPath);
    }

    /**
     * Get the path to the server list file that should contain a list of root
     * folders containing Minecraft Server instances.
     * 
     * @return Returns an absolute path to the server list file or null if not
     *         exists or cannot read.
     */
    private String getServerListFilePath()
    {
        File thisFolder = getInstalledFolder();
        File serverListFile = new File(thisFolder, SERVER_LIST);
        if (serverListFile.exists() && serverListFile.canRead())
        {
            return serverListFile.getAbsolutePath();
        }
        else
        {
            System.out.println(" === Could not find or read server list");
            return null;
        }
    }

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
