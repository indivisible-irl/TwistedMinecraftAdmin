package com.indivisible.twistedserveradmin.menu.util;


public enum EScreenMenuType
{
    list,           // list all running screens
    create_att,     // create a new screen and attach
    create_det,     // create a new screen and detach
    attach,         // attach to a screen
    detach,         // forcibly detach a screen
    kill,           // forcibly kill a screen
    kill_confirm    // get user's confirmation before taking action
}
