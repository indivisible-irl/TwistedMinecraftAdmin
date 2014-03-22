package com.indivisible.twistedserveradmin.menu.base;

import java.io.IOException;
import java.util.List;
import com.indivisible.twistedserveradmin.system.Main;


public class MenuDisplay
        extends Menu
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    protected boolean waitToReturn = false;
    protected List<String> content = null;

    protected static final String WAIT_FOR_INPUT_STRING = "\tPress [Enter] to continue...  ";

    private static final String TAG = "MenuDisplay";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new display only Menu. Optional wait for response before
     * exiting Menu.
     * 
     * @param title
     * @param description
     * @param waitToReturn
     */
    public MenuDisplay(String title, String description, boolean waitToReturn)
    {
        super(title, description);
        this.waitToReturn = waitToReturn;
    }

    /**
     * Create a new display only Menu. Set or result type lines also
     * displayed. Optional wait for user response before exiting Menu.
     * 
     * @param title
     * @param description
     * @param content
     * @param waitToReturn
     */
    public MenuDisplay(String title, String description, List<String> content,
            boolean waitToReturn)
    {
        this(title, description, waitToReturn);
        this.content = content;
    }


    ///////////////////////////////////////////////////////
    ////    gets & sets
    ///////////////////////////////////////////////////////

    /**
     * Set the main body of the MenuDisplay
     * 
     * @param content
     */
    public void setContent(List<String> content)
    {
        this.content = content;
    }

    /**
     * Retrieve the main body of the MenuDisplay
     * 
     * @return
     */
    public List<String> getContent()
    {
        return content;
    }

    /**
     * Check whether the Menu should wait for user interaction before
     * finishing action.
     * 
     * @return
     */
    public boolean doWaitToReturn()
    {
        return waitToReturn;
    }


    ///////////////////////////////////////////////////////
    ////    override methods
    ///////////////////////////////////////////////////////

    @Override
    public Menu invoke()
    {
        this.display();
        this.waitForInput();
        return null;            // always go back to parent
    }


    @Override
    public void display()
    {
        Menu.clearScreen();
        super.printHead();
        this.printResults();
    }


    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * If there are results set print 'em.
     */
    private void printResults()
    {
        //TODO: paged and columned display
        if (content != null)
        {
            for (String result : content)
            {
                System.out.println(PADDING_LARGE + result);
            }
        }
    }

    /**
     * Do nothing until user hits a button if set that way.
     */
    private void waitForInput()
    {
        if (waitToReturn)
        {
            System.out.print(WAIT_FOR_INPUT_STRING);
            try
            {
                System.in.read();
            }
            catch (IOException e)
            {
                Main.myLog.error(TAG, "Failed to wait for user input");
            }
        }
    }


}
