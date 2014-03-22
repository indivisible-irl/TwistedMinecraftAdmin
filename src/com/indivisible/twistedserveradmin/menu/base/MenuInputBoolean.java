package com.indivisible.twistedserveradmin.menu.base;

import com.indivisible.twistedserveradmin.menu.util.Choices;


public class MenuInputBoolean
        extends MenuInput
{

    protected MenuInputBoolean(String title, String description, String question,
            String prompt, Choices choices)
    {
        super(title, description, question, prompt, choices);
        // TODO: Implement MenuInputBoolean
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
