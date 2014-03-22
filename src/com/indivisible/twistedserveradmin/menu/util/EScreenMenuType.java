package com.indivisible.twistedserveradmin.menu.util;

/**
 * Enumerator for differentiation of Screen Menu Types.
 * 
 * <ul>
 * <li><b>list</b> - plain listing</li>
 * <li><b>create_att</b> - create and attach to a new Screen</li>
 * <li><b>create_det</b> - create but do not attach to a new Screen</li>
 * <li><b>attach</b> - attach to an existing Screen</li>
 * <li><b>detach</b> - forcibly detach all users from a Screen</li>
 * <li><b>kill</b> - forcibly kill an existing Screen. Use with caution</li>
 * <li><b>kill_confirm</b> - confirm your intent to kill a Screen</li>
 * </ul>
 * 
 * @author indiv
 */
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
