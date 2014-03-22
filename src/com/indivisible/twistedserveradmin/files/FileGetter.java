package com.indivisible.twistedserveradmin.files;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Class to handle calculation and testing for necessary files and folders.
 * 
 * @author indiv
 * 
 */
public class FileGetter
{

    //RET: prepare new instance

    //// data

    private static final String RESOURCE_FOLDER = "/res/";
    private static final String APP_SETTINGS_FILENAME = "settings.cfg";
    private static final String SERVER_LIST_FILENAME = "servers.list";
    private static final String INFO_FILENAME = "server.info";
    private static final String PROPS_FILENAME = "server.properties";
    private static final String TAG = "FileGetter";


    //// public methods

    /**
     * Gather all the directories from a folder
     * 
     * @param rootFolder
     * @return
     */
    public static File[] getDirectories(File folder)
    {
        if (folder == null | !folder.exists() || !folder.canRead()
                || !folder.isDirectory())
        {
            return null;
        }
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
     * Retrieve the file that contains the Application settings.<br/>
     * 
     * @return
     */
    public static File getApplicationSettingsFile()
    {
        File rootFolder = getApplicationRootFolder();
        File settingsFile = getFileFromDisk(rootFolder, APP_SETTINGS_FILENAME);
        if (testFileExists(settingsFile))
        {
            return settingsFile;
        }
        // try to write settings to disk and then return
        boolean writeSuccess = writeInternalFileToDisk(rootFolder, APP_SETTINGS_FILENAME);
        if (writeSuccess && testFileExists(settingsFile))
        {
            return settingsFile;
        }
        else
        {
            Main.myLog.error(TAG,
                             "Error getting settings file: "
                                     + settingsFile.getAbsolutePath());
            Main.myLog.error(TAG, "Quitting...");
            System.exit(20);
            return null;
        }
    }

    /**
     * Retrieve the File that should contain the list of directories
     * containing server instance folders.<br/>
     * 
     * @return
     */
    public static File getServerListFile()
    {
        File rootFolder = getApplicationRootFolder();
        File serverListFile = getFileFromDisk(rootFolder, SERVER_LIST_FILENAME);
        if (testFileExists(serverListFile))
        {
            return serverListFile;
        }
        else
        {
            Main.myLog.error(TAG,
                             "Error getting server list file: "
                                     + serverListFile.getAbsolutePath());
            return null;
        }
    }

    /**
     * Get a Server instance's properties File.<br/>
     * Returns null if not exists or not accessible.
     * 
     * @param serverInstanceRoot
     * @return
     */
    public static File getServerInstancePropertiesFile(File serverInstanceRoot)
    {
        return getFileFromDisk(serverInstanceRoot, PROPS_FILENAME);
    }

    /**
     * Get a Server instance's info file or the default version from root
     * folder if not exists or not accessible.
     * 
     * @param serverInstanceRoot
     * @return
     */
    public static File getServerInstanceInfoFile(File serverInstanceRoot)
    {
        File info = getFileFromDisk(serverInstanceRoot, INFO_FILENAME);
        if (info == null)
        {
            info = getServerDefaultInfoFile();
        }
        return info;
    }

    /**
     * Get the default Server instance info File from the Application's root
     * folder.
     * 
     * @return
     */
    public static File getServerDefaultInfoFile()
    {
        return getFileFromRoot(INFO_FILENAME);
    }

    /**
     * Get the Server instance's startup script File named according to the
     * instance's info file.
     * 
     * @param serverInstanceRoot
     * @param startupScriptName
     * @return
     */
    public static File getServerInstanceStartupFile(File serverInstanceRoot,
                                                    String startupScriptName)
    {
        return getFileFromDisk(serverInstanceRoot, startupScriptName);
    }


    /**
     * Make sure that all required files exist in the Application's root.
     */
    public static boolean ensureRootFilesExist()
    {
        String[] rootFileNames = new String[] {
                APP_SETTINGS_FILENAME, SERVER_LIST_FILENAME, INFO_FILENAME
        };
        File rootFolder = getApplicationRootFolder();

        Main.myLog.info(TAG, "Checking root files...");
        boolean success = true;
        File file;
        String info;
        for (String filename : rootFileNames)
        {
            file = new File(rootFolder, filename);
            info = "    - " + filename + "...  ";
            if (!testFileExists(file))
            {
                info += "Creating...  ";
                boolean writeSuccessful = writeInternalFileToDisk(rootFolder, filename);
                if (!writeSuccessful)
                {
                    info += "FAILED:\n\t" + file.getAbsolutePath();
                    Main.myLog.error(TAG, info);
                    success = false;
                }
                else
                {
                    info += "Success";
                    Main.myLog.verbose(TAG, info);
                }
            }
            else
            {
                info += "OK";
                Main.myLog.verbose(TAG, info);
            }
        }
        if (success)
        {
            Main.myLog.info(TAG, "Root files ok.");
        }
        else
        {
            Main.myLog.error(TAG, "Failed to gather all root files.");
        }
        return success;
    }

    /**
     * Prepare the current working directory if valid Minecraft Server
     * instance
     * 
     * @return
     */
    public static boolean prepThisFolder()
    {
        File cwd = getCurrentWorkingDirectory();
        return prepServerInstance(cwd);
    }

    //// private methods

    /**
     * Retrieve a File representation of the folder this application is
     * located in.
     * 
     * @return
     */
    private static File getApplicationRootFolder()
    {
        String path = FileGetter.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        try
        {
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            File jarfile = new File(decodedPath);
            return jarfile.getParentFile();
        }
        catch (UnsupportedEncodingException e)
        {
            Main.myLog.error(TAG, "Unencoding jar path failed on:\n\t" + path);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the directory the jar was invoked from.
     * 
     * @return
     */
    private static File getCurrentWorkingDirectory()
    {
        String cwdString = System.getProperty("user.dir");
        File cwd = new File(cwdString);
        if (testFileExists(cwd))
        {
            return cwd;
        }
        else
        {
            return null;
        }
    }

    /**
     * Retrieve a File from disk or null on failure.
     * 
     * @param parentFolder
     * @param filename
     * @return
     */
    private static File getFileFromDisk(File parentFolder, String filename)
    {
        File file = new File(parentFolder, filename);
        if (testFileExists(file))
        {
            return file;
        }
        else
        {
            return null;
        }
    }

    /**
     * Get a File from the Application's root folder.
     * 
     * @param filename
     * @return
     */
    private static File getFileFromRoot(String filename)
    {
        File applicationRoot = getApplicationRootFolder();
        return getFileFromDisk(applicationRoot, filename);
    }

    /**
     * Tests if a File on disk exists and can be read.
     * 
     * @param file
     * @return
     */
    private static boolean testFileExists(File file)
    {
        return file.exists() && file.canRead();
    }

    /**
     * Get an InputStream of the contents of a file located inside this jar.
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    private static InputStream
            getInternalFileInputStream(String filename) throws FileNotFoundException
    {
        URL resUrl = FileGetter.class.getResource(RESOURCE_FOLDER + filename);
        String badPath = resUrl.getPath();
        String goodPath = badPath.substring(badPath.indexOf("!") + 1);
        //System.out.println("    - Trying to reach: " + goodPath);
        InputStream input = FileGetter.class.getResourceAsStream(goodPath);

        return input;
        //        if (input == null)
        //        {
        //            throw new FileNotFoundException("Could not read internal file: " + filename);
        //        }
        //        else
        //        {
        //            return input;
        //        }
    }

    /**
     * Get an InputStream of the contents of a file located on disk.
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    private static InputStream
            getRootFileInputStream(String filename) throws FileNotFoundException
    {
        File rootFolder = getApplicationRootFolder();
        File rootFile = new File(rootFolder, filename);

        if (testFileExists(rootFile))
        {
            InputStream input = new FileInputStream(rootFile);
            return input;
        }
        else
        {
            throw new FileNotFoundException("Could not read file from disk:\n\t"
                    + rootFile.getAbsolutePath());
        }
    }

    /**
     * Make a copy of a file contained in this jar to the disk.
     * 
     * @param destinationFolder
     * @param filename
     * @return
     */
    private static boolean
            writeInternalFileToDisk(File destinationFolder, String filename)
    {
        InputStream input = null;
        OutputStream output = null;
        try
        {
            input = getInternalFileInputStream(filename);
            output = new FileOutputStream(new File(destinationFolder, filename));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, bytesRead);
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            Main.myLog.error(TAG, "Error retrieving internal file: " + filename);
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            Main.myLog.error(TAG, "Error reading internal file: " + filename);
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (output != null)
            {
                try
                {
                    output.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Make a copy of a file contained in this jar to the disk.
     * 
     * @param destinationFolder
     * @param filename
     * @return
     */
    private static boolean writeRootFileToDisk(File destinationFolder, String filename)
    {
        InputStream input = null;
        OutputStream output = null;
        try
        {
            input = getRootFileInputStream(filename);
            output = new FileOutputStream(new File(destinationFolder, filename));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, bytesRead);
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (output != null)
            {
                try
                {
                    output.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Prepare a Server instance for use with this tool.
     * 
     * @param serverInstanceFolder
     * @return
     */
    private static boolean prepServerInstance(File serverInstanceFolder)
    {
        System.out.println("Testing for valid Minecraft Server instance...");
        MinecraftServer serverInstance = new MinecraftServer(serverInstanceFolder);
        if (!serverInstance.isServer())
        {
            System.out.println("Not a valid Minecraft Server folder:\n\t"
                    + serverInstanceFolder.getAbsolutePath());
            System.out
                    .println("If this is a Minecraft Server please ensure you have run it at least once to generate the server files.");
            return false;
        }
        else
        {
            File testFile = new File(serverInstanceFolder, INFO_FILENAME);
            if (!testFile.exists())
            {
                return writeRootFileToDisk(serverInstanceFolder, INFO_FILENAME);
            }
            else
            {
                return true;
            }
        }
    }
}
