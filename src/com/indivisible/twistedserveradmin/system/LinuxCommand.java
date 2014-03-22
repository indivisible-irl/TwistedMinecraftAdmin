package com.indivisible.twistedserveradmin.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LinuxCommand
{

    public static List<String> runCmdForOutput(String cmdString)
    {
        Runtime runtime = Runtime.getRuntime();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try
        {
            Process process = runtime.exec(cmdString);
            isr = new InputStreamReader(process.getInputStream());
            br = new BufferedReader(isr);
            List<String> output = new ArrayList<String>();
            String inputLine = null;
            while ((inputLine = br.readLine()) != null)
            {
                output.add(inputLine);
            }
            return output;
        }
        catch (IllegalThreadStateException e)
        {
            System.out.println("!!! IllegalThreadStateException");
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            System.out.println(String.format("!!! IOException running [%s]", cmdString));
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (isr != null)
            {
                try
                {
                    isr.close();
                }
                catch (IOException e)
                {}
            }
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {}
            }
            runtime = null;
        }
    }
}
