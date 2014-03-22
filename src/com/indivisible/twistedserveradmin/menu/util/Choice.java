package com.indivisible.twistedserveradmin.menu.util;

import com.indivisible.twistedserveradmin.menu.base.Menu;


/**
 * Menu choices for MenuInput to use. <br />
 * Allows for dividers to not mess up indexing.
 * 
 * @author indiv
 */
public class Choice
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private EChoiceType choiceType = null;
    private int index = -1;
    private String text;
    //private Object object;

    // formatting and padding
    protected static final String FORMAT_NUMBERING = "(%2s)";
    protected static final String PADDING_NUMBERING = "    ";   // to match width of FORMAT_NUMBERING
    protected static final String FORMAT_CHOICE = Menu.PADDING_LARGE + "%s"
            + Menu.PADDING_SMALL + "%s";
    protected static final String FORMAT_SUBCHOICE = Menu.PADDING_LARGE + FORMAT_CHOICE;
    protected static final String FORMAT_UNSELECTABLE = Menu.PADDING_LARGE
            + Menu.PADDING_SMALL + PADDING_NUMBERING + "%s";
    protected static final String FORMAT_HEADER = Menu.PADDING_LARGE + Menu.PADDING_SMALL
            + PADDING_NUMBERING + Menu.ANSI_YELLOW + "%s";
    protected static final String FORMAT_DIVIDER = Menu.PADDING_SMALL + Menu.ANSI_CYAN
            + "%s" + Menu.ANSI_RESET;


    //Enum Types: header, divider, selectable, unselectable


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new User selectable Choice.
     * 
     * @param selectionIndex
     * @param choiceText
     */
    public Choice(int selectionIndex, String choiceText)
    {
        choiceType = EChoiceType.selectable;
        this.index = selectionIndex;
        this.text = choiceText;
    }

    /**
     * Create a new non-selectable Choice of the supplied EChoiceType type.
     * 
     * @param dividerText
     * @param choiceType
     */
    public Choice(String dividerText, EChoiceType choiceType)
    {
        this.choiceType = choiceType;
        this.text = dividerText;
    }


    ///////////////////////////////////////////////////////
    ////    gets
    ///////////////////////////////////////////////////////

    /**
     * Check whether this Choice is User selectable.
     * 
     * @return
     */
    public boolean isSelectable()
    {
        return choiceType.equals(EChoiceType.selectable);
    }

    public EChoiceType getType()
    {
        return choiceType;
    }

    /**
     * Get the Choice's index. <br />
     * Returns -1 if none is set.
     * 
     * @return
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Get's the text that will be displayed without standard formatting. <br />
     * Use Choice.toString() to get the printable version.
     * 
     * @return
     */
    public String getText()
    {
        return text;
    }


    ///////////////////////////////////////////////////////
    ////    override
    ///////////////////////////////////////////////////////

    @Override
    public String toString()
    {
        switch (choiceType)
        {
            case selectable:
                String num = String.format(FORMAT_NUMBERING, index);
                return String.format(FORMAT_CHOICE, num, text);
            case unselectable:
                return String.format(FORMAT_UNSELECTABLE, text);
            case divider:
                return String.format(FORMAT_DIVIDER, text);
            case header:
                return String.format(FORMAT_HEADER, text);
            case subchoice:
                return String.format(FORMAT_SUBCHOICE, text);
            default:
                return "Error on Choice EChoiceType: " + choiceType.toString();
        }
    }

}
