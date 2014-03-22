package com.indivisible.twistedserveradmin.menu;

import com.indivisible.twistedserveradmin.menu.base.Menu;
import com.indivisible.twistedserveradmin.menu.common.MainMenu;
import com.indivisible.twistedserveradmin.system.Main;

/**
 * Class to handle Menu ordering and calling
 * 
 * @author indiv
 * 
 */
public class MenuManager
{

    //// data

    private MenuStack menuStack = null;
    private static final String TAG = "MenuManager";


    //// constructors

    /**
     * Create a default MenuStack starting with the MainMenu
     */
    public MenuManager()
    {
        menuStack = new MenuStack(new MainMenu());
    }

    public MenuManager(Menu rootMenu)
    {
        menuStack = new MenuStack(rootMenu);
    }


    /**
     * Loop and run through a Menu stack until no Menus left.
     */
    public void run()
    {
        Menu thisMenu = null;
        Menu nextMenu = null;
        while (!menuStack.isEmpty())
        {
            thisMenu = menuStack.peek();
            if (thisMenu != null)
            {
                nextMenu = thisMenu.invoke();
                if (nextMenu == null)
                {
                    menuStack.pop();
                }
                else
                {
                    menuStack.add(nextMenu);
                }
            }
            else
            {
                Main.myLog.warning(TAG, "Uh oh, tried to use a null Menu...");
                break;
            }
        }
    }

}
