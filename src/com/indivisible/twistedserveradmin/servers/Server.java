package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.config.ServerInfo;
import com.indivisible.twistedserveradmin.config.ServerProperties;
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

    private static final String PROPERTIES_NAME = "server.properties";
    private static final String INFO_NAME = "server.info";
    private static final String DEFAULT_STARTUP = "start.sh";
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
        String propPath = getPropertiesPath();
        if (propPath != null)
        {
            try
            {
                properties = new ServerProperties(propPath);
            }
            catch (IOException e)
            {
                System.out.println(" === Error trying to read properties: " + propPath);
            }
        }
        if (properties != null)
        {
            String infoPath = getInfoPath();
            if (infoPath != null)
            {
                try
                {
                    info = new ServerInfo(infoPath);
                }
                catch (IOException e)
                {
                    System.out.println(" === Error trying to read info: " + infoPath);
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
        File testFile = new File(getStartupScriptPath());
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
            String startupPath = getStartupScriptPath();
            try
            {
                Process process = Runtime.getRuntime().exec(String.format("bash %s",
                                                                          startupPath));
                int exitStatus = process.waitFor();
                if (exitStatus == 0)
                {
                    System.out.println(" === Startup completed successfully.");
                    return true;
                }
                else
                {
                    System.out.println(" === Startup exited with non zero status code: "
                            + exitStatus);
                    return false;
                }
            }
            catch (IOException e)
            {
                System.out.println(" === Failed to trigger startup script: "
                        + startupPath);
                return false;
            }
            catch (InterruptedException e)
            {
                System.out.println(" === Startup was interrupted.");
                return false;
            }
        }
        else
        {
            System.out
                    .println("   -- Server has no start.sh script or not accessible.\nExpected: "
                            + getStartupScriptPath());
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
     * Get the path to the Server's properties files.
     * 
     * @return
     */
    private String getPropertiesPath()
    {
        return getFilePath(PROPERTIES_NAME);
    }

    /**
     * Get the path to the Server's info file.
     * 
     * @return
     */
    private String getInfoPath()
    {
        return getFilePath(INFO_NAME);
    }

    /**
     * Get the path to the Server's startup file.
     * 
     * @return
     */
    public String getStartupScriptPath()
    {
        if (hasInfo())
        {
            String customScriptName = getInfo().getStartupScriptName();
            if (customScriptName != null && !customScriptName.equals(""))
            {
                return getFilePath(customScriptName);
            }
        }
        return getFilePath(DEFAULT_STARTUP);
    }

    /**
     * Get an absolute path to a server's file.
     * 
     * @param filename
     * @return Returns 'null' on no file or no read access.
     */
    private String getFilePath(String filename)
    {
        String filePath = serverInstanceRoot.getAbsolutePath() + File.separator
                + filename;
        File test = new File(filePath);
        if (test.exists() && test.canRead())
        {
            return filePath;
        }
        else
        {
            return null;
        }
    }


}
