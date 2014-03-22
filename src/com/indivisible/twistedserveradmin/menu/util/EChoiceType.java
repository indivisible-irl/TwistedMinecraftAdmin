package com.indivisible.twistedserveradmin.menu.util;

/**
 * Enumerator for different line types for display in the main body of a Menu
 * 
 * @author indiv
 */
public enum EChoiceType
{
    selectable,     // is user selectable option, get's an index
    unselectable,   // is not selectable option but should be printed in line with them.
    header,         // for clarifying data columns
    divider,        // for splitting on data states 
    subchoice;      // a further indented, selectable choice to go under a parent Choice

}
