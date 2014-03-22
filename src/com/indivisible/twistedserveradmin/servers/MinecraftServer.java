package com.indivisible.twistedserveradmin.servers;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.indivisible.twistedserveradmin.config.ServerInfo;
import com.indivisible.twistedserveradmin.config.ServerProperties;
import com.indivisible.twistedserveradmin.files.FileGetter;
import com.indivisible.twistedserveradmin.query.MinecraftServerQuery;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Class to represent a Minecraft folder.
 * 
 * @author indiv
 * 
 */
public class MinecraftServer
{

    //TODO: Make a parent Server class for future expansion.

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    // local
    private File serverInstanceRoot;
    private ServerProperties properties = null;
    private ServerInfo info = null;
    private MinecraftServerQuery query = null;
    private ServerStatus serverStatus = ServerStatus.unknown;

    // statics and defaults
    //TODO: Get defaults from settings
    //private static final long QUERY_TOO_OLD = 500L;
    private static final int DEFAULT_QUERY_TIMEOUT = 4000;
    private static final String DEFAULT_MOTD = "No MOTD";
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 25565;
    private static final String DEFAULT_VERSION = "0.0.0";

    private static final String TAG = "MCServer";


    ///////////////////////////////////////////////////////
    //// constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Class to represent a Minecraft Server.
     * 
     * @param serverFolder
     */
    public MinecraftServer(File serverInstanceFolder)
    {
        if (serverInstanceFolder.canRead() && serverInstanceFolder.isDirectory())
        {
            this.serverInstanceRoot = serverInstanceFolder;
            init();
        }
        else
        {
            Main.myLog.verbose(TAG, "Server folder not accessible or not a directory: "
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


    ///////////////////////////////////////////////////////
    ////    server details
    ///////////////////////////////////////////////////////

    /**
     * Check whether the supplied folder is a valid Minecraft Server
     * 
     * @return
     */
    public boolean isServer()
    {
        //TODO: Expand on test for if valid server.
        return hasProperties();
    }

    /**
     * Get the current Server status. <br />
     * ASK: should re-query if out of date?
     * 
     * @return
     */
    public ServerStatus getServerStatus()
    {
        return serverStatus;
    }

    /**
     * Check if the Server is reported as Online.
     * 
     * @return
     */
    public boolean isOnline()
    {
        return serverStatus.equals(ServerStatus.online);
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
     * Get the Server's name. <br />
     * Prefers: info > folder name.
     * 
     * @return
     */
    public String getName()
    {
        String name;
        if (hasInfo())
        {
            name = info.getNickname();
            if (name != null)
            {
                //Main.myLog.debug(TAG, "getName: " + name);
                return name;
            }
            else
            {
                Main.myLog
                        .warning(TAG,
                                 "Failed to get Nickname from Info. Using folder name.");
            }
        }
        else
        {
            Main.myLog.info(TAG, "No Info found. Using folder name.");
        }
        name = serverInstanceRoot.getName();
        return name;
    }

    /**
     * Get the Server's version. <br />
     * Prefers: query > info > default.
     * 
     * @return
     */
    public String getVersion()
    {
        String version;
        if (hasInfo())
        {
            version = info.getVersion();
            if (version == null)
            {
                Main.myLog.warning(TAG, "Failed to get version from Info.");
            }
            else
            {
                return version;
            }
        }
        else
        {
            Main.myLog.warning(TAG, "No Info found.");
        }
        return DEFAULT_VERSION;
    }

    /**
     * Get the Server's port. <br />
     * Prefers: properties > default (25565).
     * 
     * @return
     */
    public int getPort()
    {
        int port;
        if (hasProperties())
        {
            port = properties.getPort();
            if (port != Integer.MIN_VALUE)
            {
                return port;
            }
            return port;
        }
        else
        {
            Main.myLog.warning(TAG, "No properties, using default Port " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

    /**
     * Get the Server's IP address. <br />
     * Prefers: properties > default (localhost).
     * 
     * @return
     */
    public String getIP()
    {
        String ip;
        if (hasProperties())
        {
            ip = properties.getIP();
            if (ip == null || ip.equals(""))
            {
                Main.myLog.warning(TAG, "Failed IP was returned. Using default IP: "
                        + DEFAULT_IP);
                return DEFAULT_IP;
            }
            else
            {
                return ip;
            }
        }
        else
        {
            Main.myLog.warning(TAG, "No properties, using default IP: " + DEFAULT_IP);
            return DEFAULT_IP;
        }
    }

    /**
     * Get the Server's MOTD without formatting. <br />
     * Prefers: properties > default.
     * 
     * @return
     */
    public String getMOTD()
    {
        String motd;
        if (hasProperties())
        {
            motd = properties.getCleanMOTD();
            if (motd != null && !motd.equals(""))
            {
                return motd;
            }
        }
        Main.myLog.warning(TAG, "Failed to get MOTD. Using default.");
        return DEFAULT_MOTD;
    }

    /**
     * Get the Server's MOTD with any formatting included. <br />
     * Prefers: properties > default.
     * 
     * @return
     */
    public String getRawMOTD()
    {
        String motd;
        if (hasProperties())
        {
            motd = properties.getRawMOTD();
            if (motd == null || motd.equals(""))
            {
                Main.myLog.warning(TAG, "Failed to get MOTD.");
                return DEFAULT_MOTD;
            }
            return motd;
        }
        else
        {
            Main.myLog.warning(TAG, "No properties, using default MOTD");
            return DEFAULT_MOTD;
        }
    }

    /**
     * Retrieve the number of players Online. <br />
     * Or -1 if the Server is not Online.
     * 
     * @return
     */
    public int getPlayersOnline()
    {
        if (serverStatus != ServerStatus.online || query == null)
        {
            return -1;
        }
        return query.getPlayersOnline();
    }

    /**
     * Retrieve the maximum allowed players for the Server. <br />
     * Or -1 if the Server is not Online.
     * 
     * @return
     */
    public int getMaxPlayers()
    {
        if (serverStatus != ServerStatus.online || query == null)
        {
            return -1;
        }
        return query.getMaxPlayers();
    }

    /**
     * Get the difficulty as set in the Server Properties.
     * 
     * @return
     */
    public int getDifficulty()
    {
        int diff = properties.getDifficulty();
        if (diff < 0)
        {
            return -1;
        }
        return diff;
    }

    /**
     * Check if the Server has Whitelist access enabled.
     * 
     * @return
     */
    public Boolean isWhitelist()
    {
        return properties.isWhitelisted();
    }

    /**
     * Check if the Server has PvP enabled. <br />
     * Not necessarily accurate as plugins may control this.
     * 
     * @return
     */
    public Boolean isPvP()
    {
        return properties.isPVPEnabled();
    }


    ///////////////////////////////////////////////////////
    ////    queries
    ///////////////////////////////////////////////////////

    /**
     * Check if Server has a previously performed Query
     * 
     * @return
     */
    public boolean hasQuery()
    {
        return query != null;
    }

    /**
     * Get a previously performed ServerQuery.
     * 
     * @return
     */
    public MinecraftServerQuery getQuery()
    {
        if (query == null)
        {
            Main.myLog.warning(TAG, "No ServerQuery to return [" + getName() + "]");
            return null;
        }
        else
        {
            return query;
        }
    }

    /**
     * Perform a query on the Server using a default timeout (1.25 secs).
     * 
     * @return Returns false on offline or null on failure.
     */
    public Boolean performQuery()
    {
        return performQuery(DEFAULT_QUERY_TIMEOUT);
    }

    /**
     * Perform a query on the server using a custom timeout.
     * 
     * @param timeout
     *        (milliseconds)
     * @return Returns true for success, false for timeout, null on failure.
     */
    public Boolean performQuery(int timeout)
    {
        if (hasProperties())
        {
            String addr = getIP();
            int port = getPort();
            if (port > 0)
            {
                if (addr != null)
                {
                    query = new MinecraftServerQuery(addr, port, timeout);
                    //ASK: Evaluate queries and assign states here or always query threaded and do there?
                    //  ^ not already doing that?
                    boolean successfulFetch = query.fetchData();
                    serverStatus = query.getServerStatus();
                    return successfulFetch;
                }
                else
                {
                    Main.myLog.error(TAG, "IP Address returned null for " + getName());
                }
            }
            else
            {
                Main.myLog.error(TAG, "Invalid port for '" + getName() + "': " + port);
            }
        }
        else
        {
            Main.myLog.error(TAG, "Server has no properties set to use for ServerQuery");
        }
        serverStatus = ServerStatus.error;
        return null;
    }


    ///////////////////////////////////////////////////////
    ////    auto handling
    ///////////////////////////////////////////////////////


    /**
     * Test whether the Server is one that should be shown by default. <br />
     * TODO: Sep showInConsole and showInApi?
     * 
     * @return
     */
    public boolean isMonitoredServer()
    {
        Boolean isMonitored = false;
        if (hasInfo())
        {
            isMonitored = info.doShowInConsole();
        }
        if (isMonitored == null)
        {
            return false;
        }
        return isMonitored;
    }

    /**
     * Check whether this Server should be included in a batch Server start.
     * 
     * @return
     */
    public boolean doBatchStart()
    {
        if (hasInfo())
        {
            return info.doAutoStart();
        }
        return false;
    }

    /**
     * Check whether this Server should be included in a batch Server stop.
     * 
     * @return
     */
    public boolean doBatchStop()
    {
        if (hasInfo())
        {
            return info.doAutoStop();
        }
        return false;
    }

    /**
     * Check whether this Server should be restarted if discovered offline.
     * 
     * @return
     */
    public boolean doRestart()
    {
        if (hasInfo())
        {
            return info.doAutoRestart();
        }
        return false;
    }


    ///////////////////////////////////////////////////////
    ////    startup
    ///////////////////////////////////////////////////////

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
            Main.myLog.warning(TAG, "Unimplemented startup called successfully.");
            return true;
        }
        else
        {
            Main.myLog.error(TAG, "Server has no startup script or not accessible");
            return false;
        }
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


    ///////////////////////////////////////////////////////
    //// file methods
    ///////////////////////////////////////////////////////

    /**
     * Get the path to the Server's root. <br />
     * Returns 'null' if not set.
     * 
     * @return
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


    ///////////////////////////////////////////////////////
    ////    String methods
    ///////////////////////////////////////////////////////

    @Override
    public String toString()
    {
        return getName();
    }

}
