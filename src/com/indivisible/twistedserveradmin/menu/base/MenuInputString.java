package com.indivisible.twistedserveradmin.menu.base;


public class MenuInputString
        extends MenuInput
{

    public MenuInputString(String title, String description, String question,
            String prompt)
    {
        super(title, description, question, prompt);
        // TODO: Implement MenuInputString
        // Guess it doesn't have choices but has extended description
    }

    @Override
    public boolean isValidResponse()
    {
        return false;
    }

    @Override
    public Menu invoke()
    {
        return null;
    }

}
