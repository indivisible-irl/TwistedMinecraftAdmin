package com.indivisible.twistedserveradmin.files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Convenience Class to manage looping through a text file's contents.
 * 
 * @author indiv
 */
public class FileIterator
        implements Iterator<String>
{

    //// data

    private BufferedReader br;
    private String currentLine;


    //// constructor

    /**
     * Convenience Class to manage looping through a text file's contents.
     * 
     * @param sourceFile
     * @throws IOException
     */
    public FileIterator(String sourceFile) throws IOException
    {
        openFile(sourceFile);
    }


    //// public methods

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
                System.out.println("Error while retrieving line from file");
                return false;
            }
            if (currentLine == null)
            {
                return false;
            }
            else if (currentLine.equals(""))
            {
                continue;
            }
            else if (currentLine.trim().startsWith("#"))
            {
                continue;
            }
            return true;
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
            System.out.println(" === File already closed or error.");
        }
    }


    //// private methods

    /**
     * Open a handle to the desired file and queue it for the Buffer
     * 
     * @param sourceFile
     * @throws IOException
     */
    private void openFile(String sourceFile) throws IOException
    {
        try
        {
            FileInputStream fis = new FileInputStream(sourceFile);
            br = new BufferedReader(new InputStreamReader(fis));
        }
        catch (FileNotFoundException e)
        {
            throw new IOException("Failed to read file: " + sourceFile);
        }
    }


}
