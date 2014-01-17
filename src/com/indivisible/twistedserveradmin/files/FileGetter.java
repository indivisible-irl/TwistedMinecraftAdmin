package com.indivisible.twistedserveradmin.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Class to handle calculation and testing for necessary files and folders.
 * 
 * @author indiv
 * 
 */
public class FileGetter
{

    //// data

    private static final String RESOURCE_FOLDER = "/res/";
    private static final String APP_SETTINGS_FILENAME = "settings.cfg";
    private static final String SERVER_LIST_FILENAME = "servers.list";
    private static final String INFO_FILENAME = "server.info";
    private static final String PROPS_FILENAME = "server.properties";


    //// public methods

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
            System.out.println("!! Error getting settings file: "
                    + settingsFile.getAbsolutePath());
            System.out.println("!! Quitting...");
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
        // try to write settings to disk and then return
        boolean writeSuccess = writeInternalFileToDisk(rootFolder, SERVER_LIST_FILENAME);
        if (writeSuccess && testFileExists(serverListFile))
        {
            return serverListFile;
        }
        else
        {
            System.out.println("!! Error getting server list file: "
                    + serverListFile.getAbsolutePath());
            System.exit(21);
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
    public static void ensureRootFilesExist()
    {
        String[] rootFileNames = new String[] {
                APP_SETTINGS_FILENAME, SERVER_LIST_FILENAME, INFO_FILENAME
        };
        File rootFolder = getApplicationRootFolder();

        System.out.println("-  Testing existance of root files...");
        for (String filename : rootFileNames)
        {
            File file = new File(rootFolder, filename);
            System.out.print("  - Testing " + filename + "...  ");
            if (!testFileExists(file))
            {
                System.out.println("not exists\n");
                boolean writeSuccessful = writeInternalFileToDisk(rootFolder, filename);
                if (!writeSuccessful)
                {
                    System.out.println("!! Failed to create file:\n\t"
                            + file.getAbsolutePath());
                }
                else
                {
                    System.out.println("  - Created successfully: " + filename);
                }
            }
            else
            {
                System.out.print("exists\n");
            }
        }
        System.out.println("... end test.");
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
            System.out.println("Unencoding jar path failed on:\n\t" + path);
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
        if (!file.exists())
        {
            System.out.println("!! File not exists:\n\t" + file.getAbsolutePath());
            return false;
        }
        else if (!file.canRead())
        {
            System.out.println("!! File cannot be read (check permissions):\n\t"
                    + file.getAbsolutePath());
            return false;
        }
        else
        {
            return true;
        }
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
        InputStream input = FileGetter.class.getClassLoader()
                .getResourceAsStream(RESOURCE_FOLDER + filename);
        if (input == null)
        {
            throw new FileNotFoundException("Could not read internal file: " + filename);
        }
        else
        {
            return input;
        }
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


}
