package com.indivisible.twistedserveradmin.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Convenience Class to manage looping through a text file's contents.
 * 
 * @author indiv
 */
public class FileIterator
        implements Iterator<String>
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private BufferedReader br;
    private String currentLine;

    private static final String TAG = "FileIterator";


    ///////////////////////////////////////////////////////
    ////    constructor & init
    ///////////////////////////////////////////////////////

    /**
     * Convenience Class to manage looping through a text file's contents.
     * 
     * @param sourceFile
     * @throws IOException
     */
    public FileIterator(File sourceFile) throws IOException
    {
        Main.myLog.debug(TAG, "Iterating through file: " + sourceFile.getAbsolutePath());
        br = getFileLineBuffer(sourceFile);
    }

    /**
     * Open a handle to the desired file and queue it for the Buffer
     * 
     * @param sourceFile
     * @throws IOException
     */
    private BufferedReader getFileLineBuffer(File sourceFile) throws IOException
    {
        BufferedReader br = null;
        try
        {
            InputStream input = new FileInputStream(sourceFile);
            br = new BufferedReader(new InputStreamReader(input));
            return br;
        }
        catch (FileNotFoundException e)
        {
            Main.myLog.error(TAG, "Failed to read file: " + sourceFile.getAbsolutePath());
            throw e;
        }
    }

    ///////////////////////////////////////////////////////
    ////    iterator methods
    ///////////////////////////////////////////////////////

    @Override
    public boolean hasNext()
    {
        while (true)
        {
            try
            {
                currentLine = br.readLine();
            }
            catch (IOException e)
            {
                Main.myLog.error(TAG, "Error while iterating line from file");
                return false;
            }
            if (currentLine == null)
            {
                return false;
            }
            else
            {
                currentLine = currentLine.trim();
            }
            if (currentLine.equals(""))
            {
                continue;
            }
            else if (currentLine.trim().startsWith("#"))
            {
                continue;
            }
            return currentLine != null;
        }
    }

    @Override
    public String next()
    {
        return currentLine;
    }

    @Override
    public void remove()
    {
        // nothing to do
    }


    /**
     * Close any open file handles.
     */
    public void close()
    {
        try
        {
            br.close();
        }
        catch (IOException e)
        {
            Main.myLog.warning(TAG, "File already closed or error.");
        }
    }

}
