package com.indivisible.twistedserveradmin.files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


public class FileIterator
        implements Iterator<String>
{

    private BufferedReader br;
    private String currentLine;


    public FileIterator(String sourceFile) throws IOException
    {
        openFile(sourceFile);
    }


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
                //RET
                e.printStackTrace();
            }

            if (currentLine == null)
            {
                System.out.println("line reads null");
                return false;
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
        // do nothing
    }

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
