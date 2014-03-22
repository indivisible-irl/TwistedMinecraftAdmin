package com.indivisible.twistedserveradmin.menu.base;

import com.indivisible.twistedserveradmin.menu.util.Choices;


/**
 * Class to display choices to a user and receive an integer input
 * 
 * @author indiv
 * 
 */
public abstract class MenuInputInteger
        extends MenuInput
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////


    private int response = Integer.MIN_VALUE;


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new Menu with an Integer user prompt. Remember to set the
     * available choices after!
     * 
     * @param title
     * @param description
     * @param question
     * @param prompt
     */
    protected MenuInputInteger(String title, String description, String question,
            String prompt)
    {
        super(title, description, question, prompt);
    }

    /**
     * Create a new Menu with an Integer user input using supplied choices.
     * 
     * @param title
     * @param description
     * @param question
     * @param prompt
     * @param choices
     */
    protected MenuInputInteger(String title, String description, String question,
            String prompt, Choices choices)
    {
        super(title, description, question, prompt, choices);
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    // super.setChoices(String[] choices)

    /**
     * Get the saved, valid response.
     * 
     * @return
     */
    protected int getResponse()
    {
        return response;
    }


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    @Override
    public Menu invoke()
    {
        super.display();
        do
        {
            super.promptAndReadResponse();
        }
        while (!isValidResponse());

        return null;
    }


    @Override
    protected boolean isValidResponse()
    {
        if (super.doGoBack())
        {
            response = 0;
            return true;
        }
        String raw = super.getRawResponse();
        if (raw == null)
        {
            super.printErrorInput("Response was null.");
            return false;
        }
        else if (raw.equals(""))
        {
            response = 0;
            return true;
        }
        else
        {
            try
            {
                int num = Integer.parseInt(raw);
                if (num < 0 || num > super.getNumChoices())
                {
                    super.printErrorInput("Outside range. Please enter a number between 0 and "
                            + super.getNumChoices() + " inclusive.");
                    return false;
                }
                else
                {
                    response = num;
                    return true;
                }
            }
            catch (NumberFormatException e)
            {
                super.printErrorInput("Not a number: " + raw);
                return false;
            }
        }

    }

}
