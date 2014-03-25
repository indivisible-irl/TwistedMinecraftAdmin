package com.indivisible.twistedserveradmin.menu.base;

import com.indivisible.twistedserveradmin.menu.MenuStack;

/**
 * Abstract base Menu class.
 * 
 * @author indiv
 */
public abstract class Menu
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private String title = null;
    private String description = null;

    private static final char CHAR_DIV_MINOR = '-';
    private static final char CHAR_DIV_MAJOR = '=';
    public static final String PADDING_SMALL = "  ";
    public static final String PADDING_LARGE = "    ";

    private static final String FORMAT_TITLE = Menu.PADDING_LARGE + "--=|  %s  |=--";
    private static final String FORMAT_DESCRIPTION = Menu.PADDING_SMALL + "%s";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new Menu. Must have a title, description may be null.
     * 
     * @param title
     * @param description
     * @param waitToReturn
     */
    public Menu(String title, String description)
    {
        this.title = title;
        this.description = description;
    }


    ///////////////////////////////////////////////////////
    ////    abstract methods
    ///////////////////////////////////////////////////////

    /**
     * Display the required bits and pieces associated with the Menu.
     */
    public abstract void display();

    /**
     * Invoke the Menu's functionality. Should be overridden by child classes.
     * Returns a Menu to go to next or null to go back to parent.
     * 
     * @return
     */
    public abstract Menu invoke();


    ///////////////////////////////////////////////////////
    ////    public methods
    ///////////////////////////////////////////////////////

    /**
     * Retrieve the Menu's title.
     * 
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Print the Menu's title and optional description.
     */
    public void printHead()
    {
        printMajorDiv();
        System.out.println(String.format(FORMAT_TITLE, this.title.toUpperCase()));
        if (description != null)
        {
            //TODO: better printing of longer descriptions. paragraphs with indentation
            System.out.println(String.format(FORMAT_DESCRIPTION, this.description));
        }
        printMinorDiv();
    }


    ///////////////////////////////////////////////////////
    ////    static methods
    ///////////////////////////////////////////////////////

    /**
     * Print a major divider. Uses console width.
     */
    public static void printMajorDiv()
    {
        StringBuilder sb = new StringBuilder();
        //FIXME: better way to access non-static width
        for (int i = 0; i <= MenuStack.getConsoleWidth(); i++)
        {
            sb.append(CHAR_DIV_MAJOR);
        }
        System.out.println(sb.toString());
    }

    /**
     * Print a minor divider. Uses console width.
     */
    public static void printMinorDiv()
    {
        StringBuilder sb = new StringBuilder();
        //FIXME: better way to access non-static width
        for (int i = 0; i <= MenuStack.getConsoleWidth(); i++)
        {
            sb.append(CHAR_DIV_MINOR);
        }
        System.out.println(sb.toString());
    }

    /**
     * Clear the console screen
     */
    public static void clearScreen()
    {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
    }
}
