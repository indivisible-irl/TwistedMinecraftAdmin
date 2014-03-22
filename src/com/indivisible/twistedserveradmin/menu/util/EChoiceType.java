package com.indivisible.twistedserveradmin.menu.util;

/**
 * Enumerator for different line types for display in the main body of a Menu.
 * 
 * <ul>
 * <li><b>selectable</b> - a user selectable choice that get's an index number
 * </li>
 * <li><b>unselectable</b> - a non selectable text line with the same
 * indentation and formatting as selectable Choices</li>
 * <li><b>header</b> - a header line for describing the columns for the main
 * listing</li>
 * <li><b>divider</b> - a divider line for breaking up the listing into
 * blocks. lesser indentation</li>
 * <li><b>blank</b> - an empty line</li>
 * </ul>
 * 
 * @author indiv
 */
public enum EChoiceType
{
    selectable,     // is user selectable option, get's an index
    unselectable,   // is not selectable option but should be printed in line with them.
    subchoice,      // a further indented, selectable choice to go under a parent Choice
    header,         // for clarifying data columns
    divider,        // for splitting on data states
    blank;          // an empty line

}
