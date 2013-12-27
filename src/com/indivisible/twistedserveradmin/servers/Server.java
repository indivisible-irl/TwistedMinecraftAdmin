package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.files.ServerInfo;
import com.indivisible.twistedserveradmin.files.ServerProperties;

/**
 * Class to represent a Minecraft folder.
 * 
 * @author indiv
 * 
 */
public class Server
{

    //// data

    private File serverRoot;
    private ServerProperties properties = null;
    private ServerInfo info = null;

    private static final String PROPERTIES_NAME = "server.properties";
    private static final String INFO_NAME = "server.info";
    private static final String STARTUP_NAME = "start.sh";


    //// constructor & init

    /**
     * Class to represent a Minecraft Server.
     * 
     * @param serverFolder
     */
    public Server(File serverFolder)
    {
        if (serverFolder.canRead() && serverFolder.isDirectory())
        {
            this.serverRoot = serverFolder;
            init();
        }
        else
        {
            System.out.println(" === Server folder not accessible or not a directory: "
                    + serverFolder.getAbsolutePath());
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
        if (serverRoot != null)
        {
            return serverRoot.getAbsolutePath();
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
        File[] jarFiles = serverRoot.listFiles(new FilenameFilter()
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
    public boolean startServer()
    {
        if (hasStartupScript())
        {
            //trigger start.sh
            return true;
        }
        else
        {
            System.out
                    .println("   -- Server has no start.sh script or not accessible.\nExpected: "
                            + getStartupScriptPath());
            return false;
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
     * Get the path to the Server's start.sh file.
     * 
     * @return
     */
    private String getStartupScriptPath()
    {
        return getFilePath(STARTUP_NAME);
    }

    /**
     * Get an absolute path to a server's file.
     * 
     * @param filename
     * @return Returns 'null' on no file or no read access.
     */
    private String getFilePath(String filename)
    {
        String filePath = serverRoot.getAbsolutePath() + File.separator + filename;
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
