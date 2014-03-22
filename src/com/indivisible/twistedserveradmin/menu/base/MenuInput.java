package com.indivisible.twistedserveradmin.menu.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.indivisible.twistedserveradmin.menu.util.Choices;
import com.indivisible.twistedserveradmin.system.Main;


/**
 * Abstract Class to handle Menus that take an input from the user.
 * 
 * @author indiv
 */
public abstract class MenuInput
        extends Menu
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private String question = null;
    private Choices choices = null;
    private String prompt = null;
    private String rawResponse = null;

    private static final String NO_CHOICES_FOUND = "No choices found...";

    public static final String FORMAT_QUESTION = Menu.PADDING_SMALL + "%s:";
    private static final String FORMAT_PROMPT = Menu.PADDING_SMALL + "%s: ";
    private static final String FORMAT_ERROR_INPUT = "!!      %s";
    // choice String formatting in Choices.java

    private static final String TAG = "MenuInput";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new MenuInput. Remember to set choices before invoking.
     * 
     * @param title
     * @param description
     * @param question
     * @param prompt
     */
    protected MenuInput(String title, String description, String question, String prompt)
    {
        super(title, description);
        this.question = question;
        this.prompt = prompt;
    }

    /**
     * Create a new MenuInput.
     * 
     * @param title
     * @param description
     * @param question
     * @param prompt
     * @param choices
     */
    protected MenuInput(String title, String description, String question, String prompt,
            Choices choices)
    {
        this(title, description, question, prompt);
        this.choices = choices;
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    /**
     * Set the choices offered to the user.
     * 
     * @param choices
     */
    protected void setChoices(Choices choices)
    {
        this.choices = choices;
    }

    /**
     * Get the number of choices supplied or MIN_VALUE if none set.
     * 
     * @return
     */
    protected int getNumChoices()
    {
        if (choices == null)
        {
            return Integer.MIN_VALUE;
        }
        else
        {
            return choices.size();
        }
    }

    /**
     * Get the user's response as a String. May be null if user not yet
     * prompted for input.
     * 
     * @return
     */
    protected String getRawResponse()
    {
        return rawResponse;
    }


    ///////////////////////////////////////////////////////
    ////    abstract methods
    ///////////////////////////////////////////////////////

    /**
     * Test User input is valid for continuing with.
     * 
     * @return
     */
    abstract boolean isValidResponse();


    ///////////////////////////////////////////////////////
    ////    protected methods
    ///////////////////////////////////////////////////////

    public void display()
    {
        Menu.clearScreen();
        printHead();
        printChoices();
    }

    public void promptAndReadResponse()
    {
        if (getNumChoices() == Integer.MIN_VALUE)
        {
            Main.myLog.error(TAG, "Choices never set...");
            rawResponse = "" + Choices.SELECTION_RETURN_NUM;
        }
        else if (getNumChoices() == 0)
        {
            Main.myLog.warning(TAG, "No choices, returning...");
            rawResponse = "" + Choices.SELECTION_RETURN_NUM;
        }
        else
        {
            System.out.print(String.format(FORMAT_PROMPT, prompt));
            rawResponse = readUserInput();
        }
    }

    public boolean doGoBack()
    {
        return rawResponse.equals(Choices.SELECTION_RETURN_NUM);
    }

    public void printErrorInput(String error)
    {
        System.out.println(String.format(FORMAT_ERROR_INPUT, error));
    }


    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * Display choices to the user.
     */
    private void printChoices()
    {
        if (choices == null)
        {
            System.out.println("!! CHOICES NOT SET. Tsk tsk");
        }
        else if (getNumChoices() == 0)
        {
            System.out.println(NO_CHOICES_FOUND);
        }
        else
        {
            System.out.println(String.format(FORMAT_QUESTION, question));
            System.out.println();
            for (String str : choices.getPrintableChoices())
            {
                System.out.println(str);
            }
        }
        printMinorDiv();
    }

    /**
     * Get user entered String input.
     * 
     * @return
     */
    private String readUserInput()
    {
        InputStreamReader isr = null;
        BufferedReader br = null;
        String strInput = "";
        try
        {
            isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);
            strInput = br.readLine();
            return strInput;
        }
        catch (IOException e)
        {
            Main.myLog.error(TAG, "IOException reading user input");
            e.printStackTrace();
            return null;
        }
    }

}
