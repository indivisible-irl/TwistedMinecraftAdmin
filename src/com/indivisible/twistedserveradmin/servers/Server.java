package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.config.ServerInfo;
import com.indivisible.twistedserveradmin.config.ServerProperties;
import com.indivisible.twistedserveradmin.files.FileGetter;
import com.indivisible.twistedserveradmin.query.ServerQuery;

/**
 * Class to represent a Minecraft folder.
 * 
 * @author indiv
 * 
 */
public class Server
{

    //// data

    private File serverInstanceRoot;
    private ServerProperties properties = null;
    private ServerInfo info = null;
    private ServerQuery query = null;

    private static final int DEFAULT_TIMEOUT = 2250;


    //// constructor & init

    /**
     * Class to represent a Minecraft Server.
     * 
     * @param serverFolder
     */
    public Server(File serverInstanceFolder)
    {
        if (serverInstanceFolder.canRead() && serverInstanceFolder.isDirectory())
        {
            this.serverInstanceRoot = serverInstanceFolder;
            init();
        }
        else
        {
            System.out.println(" === Server folder not accessible or not a directory: "
                    + serverInstanceFolder.getAbsolutePath());
        }
    }

    /**
     * Initialise needed properties files and other resources.
     */
    private void init()
    {
        File propsFile = getPropertiesFile();
        if (propsFile != null && propsFile.exists() && propsFile.canRead())
        {
            try
            {
                properties = new ServerProperties(propsFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (properties != null)
        {
            File infoFile = getInfoFile();
            if (infoFile != null && infoFile.exists() && infoFile.canRead())
            {

                try
                {
                    info = new ServerInfo(infoFile);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }


    //// public methods

    /**
     * Get the path to the Server's root.
     * 
     * @return Returns 'null' if not set.
     */
    public String getServerPath()
    {
        if (serverInstanceRoot != null)
        {
            return serverInstanceRoot.getAbsolutePath();
        }
        else
        {
            return null;
        }
    }

    /**
     * Check whether the supplied folder is a valid Minecraft Server
     * 
     * @return
     */
    public boolean isServer()
    {
        return hasProperties();
    }

    /**
     * Test whether the Server has a Properties file (and has read access).
     * 
     * @return
     */
    public boolean hasProperties()
    {
        return properties != null && properties.hasSettings();
    }

    /**
     * Test whether the Server has an Info file (and has read access).
     * 
     * @return
     */
    public boolean hasInfo()
    {
        return info != null && info.hasSettings();
    }

    /**
     * Access the Server's Properties
     * 
     * @return
     */
    public ServerProperties getProperties()
    {
        if (hasProperties())
        {
            return properties;
        }
        else
        {
            System.out.println(" === No properties found.");
            return null;
        }
    }

    /**
     * Access the Server's Info
     * 
     * @return
     */
    public ServerInfo getInfo()
    {
        return info;
    }

    /**
     * Get a list of all .jar files in the Server's folder.
     * 
     * @return
     */
    public List<String> getJars()
    {
        List<String> jars = new ArrayList<String>();
        File[] jarFiles = serverInstanceRoot.listFiles(new FilenameFilter()
            {

                @Override
                public boolean accept(File parentDir, String filename)
                {
                    return filename.endsWith(".jar");
                }
            });
        for (File file : jarFiles)
        {
            jars.add(file.getName());
        }
        return jars;
    }

    /**
     * Test a .jar file exists in the Server's folder.
     * 
     * @return
     */
    public boolean hasJar()
    {
        return !getJars().isEmpty();
    }

    /**
     * Check if Server has a startup script available
     * 
     * @return
     */
    public boolean hasStartupScript()
    {
        File testFile = getStartupScriptFile();
        return testFile.exists() && testFile.canRead();
    }

    /**
     * Trigger a Server's start.sh startup script. JVM, screen and other such
     * configuration and start/stop/restart logic should be written to there.
     * 
     * @return
     */
    public boolean start()
    {
        if (hasStartupScript())
        {
            return true;
        }
        else
        {
            System.out.println("   -- Server has no startup script or not accessible");
            return false;
        }
    }

    /**
     * Perform a query on the Server using a default timeout (1.25 secs).
     * 
     * @return Returns false on offline or null on failure.
     */
    public Boolean performQuery()
    {
        return performQuery(DEFAULT_TIMEOUT);
    }

    /**
     * Perform a query on the server using a custom timeout
     * 
     * @param timeout
     *        (milliseconds)
     * @return Returns false on offline or null on failure.
     */
    public Boolean performQuery(int timeout)
    {
        if (hasProperties())
        {
            String addr = properties.getIP();
            int port = properties.getPort();
            if (port > 0 && addr != null)
            {
                query = new ServerQuery(addr, port, timeout);
                return query.fetchData();
            }
            else
            {
                System.out.println(" === Invalid port: " + port);
                return null;
            }
        }
        else
        {
            System.out
                    .println(" === Server has no properties set to use for ServerQuery");
            return null;
        }
    }

    /**
     * Get a previously performed ServerQuery.
     * 
     * @return
     */
    public ServerQuery getQuery()
    {
        if (query == null)
        {
            System.out.println(" === No ServerQuery to return");
            return null;
        }
        else
        {
            return query;
        }
    }


    //// private methods

    /**
     * Get the Server's properties file.
     * 
     * @return
     */
    private File getPropertiesFile()
    {
        return FileGetter.getServerInstancePropertiesFile(serverInstanceRoot);
    }

    /**
     * Get the Server's info file.
     * 
     * @return
     */
    private File getInfoFile()
    {
        return FileGetter.getServerInstanceInfoFile(serverInstanceRoot);
    }

    /**
     * Get the path to the Server's startup file.
     * 
     * @return
     */
    public File getStartupScriptFile()
    {
        if (hasInfo())
        {
            String startupScriptName = info.getStartupScriptName();
            return FileGetter.getServerInstanceStartupFile(serverInstanceRoot,
                                                           startupScriptName);
        }
        else
        {
            return null;
        }

    }

    //    /**
    //     * Get an absolute path to a server's file.
    //     * 
    //     * @param filename
    //     * @return Returns 'null' on no file or no read access.
    //     */
    //    private String getFilePath(String filename)
    //    {
    //        String filePath = serverInstanceRoot.getAbsolutePath() + File.separator
    //                + filename;
    //        File test = new File(filePath);
    //        if (test.exists() && test.canRead())
    //        {
    //            return filePath;
    //        }
    //        else
    //        {
    //            return null;
    //        }
    //    }


}
