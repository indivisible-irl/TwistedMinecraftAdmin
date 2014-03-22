package com.indivisible.twistedserveradmin.menu;

import java.util.Deque;
import java.util.LinkedList;
import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Class to manage collection of Menus and the order of their deployment.
 * 
 * @author indiv
 */
public class MenuStack
{

    //// data

    private Deque<Menu> menuStack = null;
    private static int[] consoleDims = new int[] {
            90, 50
    };
    private static final String TAG = "MenuStack";

    //ASK: boolean for compact display? i.e. no divs?


    //// constructors

    /**
     * Create a new, empty MenuHandeler stack.
     */
    public MenuStack()
    {
        menuStack = new LinkedList<Menu>();
    }

    /**
     * Create a new MenuHandeler stack with the supplied Menu as the root.
     * 
     * @param menu
     */
    public MenuStack(Menu menu)
    {
        menuStack = new LinkedList<Menu>();
        this.add(menu);
    }


    //// gets & sets

    /**
     * Get the current console width.
     * 
     * @return
     */
    public static int getConsoleWidth()
    {
        return consoleDims[0];
    }

    /**
     * Get the current console height.
     * 
     * @return
     */
    public static int getConsoleHeight()
    {
        return consoleDims[1];
    }

    //// list methods

    /**
     * Gets but doesn't remove top Menu
     * 
     * @return
     */
    public Menu peek()
    {
        if (menuStack != null)
        {
            return menuStack.peekLast();
        }
        else
        {
            return null;
        }

    }

    /**
     * Gets and removes top Menu.
     * 
     * @return
     */
    public Menu pop()
    {
        if (menuStack != null)
        {
            return menuStack.pollLast();
        }
        return null;
    }

    /**
     * Add a Menu to the stack.
     * 
     * @param menu
     * @return
     */
    public boolean add(Menu menu)
    {
        Main.myLog.debug(TAG, "Adding menu to stack: " + menu.getTitle());
        return menuStack.offerLast(menu);
    }

    /**
     * Get the menu stack size.
     * 
     * @return
     */
    public int size()
    {
        if (menuStack == null)
        {
            Main.myLog.debug(TAG, "menuStack null");
            return -1;
        }
        else
        {
            int stackSize = menuStack.size();
            Main.myLog.debug(TAG, "menuStackSize: " + stackSize);
            return stackSize;
        }
    }

    /**
     * Test whether the stack has any Menus.
     * 
     * @return
     */
    public boolean isEmpty()
    {
        return size() <= 0;
    }

    /**
     * Check whether there is more than one menu in the stack.
     * 
     * @return
     */
    public boolean hasParent()
    {
        return (size() > 1);
    }

    /**
     * Test whether the current Menu is the last in the stack.
     * 
     * @return
     */
    public boolean isRootMenu()
    {
        return (size() == 1);
    }


    //// menu bits


}
