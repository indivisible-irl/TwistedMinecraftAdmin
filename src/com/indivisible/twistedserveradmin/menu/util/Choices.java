package com.indivisible.twistedserveradmin.menu.util;

import java.util.ArrayList;
import java.util.List;


public class Choices
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private List<Choice> choices;
    private int totalSelectable;

    public static final int SELECTION_RETURN_NUM = 0;
    public static final String SELECTION_RETURN_TEXT = "Go back";


    ///////////////////////////////////////////////////////
    ////    constructor
    ///////////////////////////////////////////////////////

    public Choices()
    {
        choices = new ArrayList<Choice>();
        totalSelectable = 0;
    }


    ///////////////////////////////////////////////////////
    ////    gets
    ///////////////////////////////////////////////////////

    /**
     * Get the number of Choices (not includes dividers and header lines).
     * 
     * @return
     */
    public int size()
    {
        return choices.size();
    }

    /**
     * Test if there are no Choices (selectable or not).
     * 
     * @return
     */
    public boolean isEmpty()
    {
        return choices.size() != 0;
    }

    /**
     * Get the number of User selectable Choices.
     * 
     * @return
     */
    public int numSelectableChoices()
    {
        return totalSelectable;
    }

    /**
     * Test if there is at least one User selectable Choice
     * 
     * @return
     */
    public boolean hasSelectableChoices()
    {
        return numSelectableChoices() != 0;
    }


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Add a Choice to the List of Choices. <br />
     * Allows for divider / header lines that do not increment indexes.
     * 
     * @param text
     * @param isSelectable
     */
    public void add(String text, EChoiceType choiceType)
    {
        Choice choice;
        switch (choiceType)
        {
            case selectable:
                totalSelectable++;
                choice = new Choice(totalSelectable, text);
                break;
            case unselectable:
            case header:
            case divider:
            case subchoice:
                choice = new Choice(text, choiceType);
                break;
            default:
                throw new IllegalArgumentException("Not a valid EChoiceType: "
                        + choiceType.name());
        }
        choices.add(choice);
    }

    /**
     * Get a List of Strings to print. <br />
     * Includes padding.
     * 
     * @return
     */
    public List<String> getPrintableChoices()
    {
        List<String> printableChoices = new ArrayList<String>();
        for (Choice choice : choices)
        {
            printableChoices.add(choice.toString());
        }
        // add return option
        printableChoices.add("");
        printableChoices.add(new Choice(SELECTION_RETURN_NUM, SELECTION_RETURN_TEXT)
                .toString());
        return printableChoices;
    }
}
