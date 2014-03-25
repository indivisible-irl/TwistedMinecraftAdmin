package com.indivisible.twistedserveradmin.util;

/**
 * <h3>Based on Santhosh Kumar T ANSI library distributed under GPL</h3>
 * 
 * <h6>Original copyright (C) 2009 Santhosh Kumar T</h6>
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * <h6>Edited by indivisible for TwistedMinecraftAdmin tool.
 * http://twisted.cat</h6>
 */
public class StringColor
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String PREFIX = "\u001b["; //NOI18N
    private static final String SUFFIX = "m";
    private static final String SEPARATOR = ";";
    private static final String END = PREFIX + SUFFIX;


    ///////////////////////////////////////////////////////
    ////    enums
    ///////////////////////////////////////////////////////

    /**
     * Choose the text style.
     * 
     * <ul>
     * <li><b>NORMAL</b> - Regular text</li>
     * <li><b>BRIGHT</b> - Bold text</li>
     * <li><b>DIM</b> - Muted colour</li>
     * <li><b>UNDERLINE</b> - Underlined text</li>
     * <li><b>BLINK</b> - Flashing text, Not always supported</li>
     * <li><b>REVERSE</b> - Invert foreground and background?</li>
     * <li><b>HIDDEN</b> - Invisible text</li>
     * </ul>
     * 
     * @author indiv
     * 
     */
    public enum Attribute
    {
        NORMAL(0), BRIGHT(1), DIM(2), UNDERLINE(4), BLINK(5), REVERSE(7), HIDDEN(8);

        private String value;

        private Attribute(int value)
        {
            this.value = String.valueOf(value);
        }

        public String toString()
        {
            return "" + value;
        }
    }

    /**
     * Available colours. <br />
     * Can be used as foreground (text colour). <br />
     * or background colours.
     * 
     * <ul>
     * <li><b>BLACK</b></li>
     * <li><b>RED</b></li>
     * <li><b>GREEN</b></li>
     * <li><b>YELLOW</b></li>
     * <li><b>BLUE</b></li>
     * <li><b>MAGENTA</b></li>
     * <li><b>CYAN</b></li>
     * <li><b>WHITE</b></li>
     * </ul>
     * 
     * @author indiv
     * 
     */
    public enum Color
    {
        BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE
    }


    ///////////////////////////////////////////////////////
    ////    basic formatting
    ///////////////////////////////////////////////////////

    /**
     * Modify a String for printing only changing foreground colour.
     * 
     * @param text
     * @param foreground
     * @return
     */
    public static String format(String text, Color foreground)
    {
        return format(text, foreground, null, null);
    }

    /**
     * 
     * @param text
     * @param foreground
     * @param attr
     * @return
     */
    public static String format(String text, Color foreground, Attribute attr)
    {
        return format(text, foreground, null, attr);
    }

    /**
     * Modify a String for printing changing foreground and background
     * colours.
     * 
     * @param text
     * @param foreground
     * @param background
     * @return
     */
    public static String format(String text, Color foreground, Color background)
    {
        return format(text, foreground, background, null);
    }

    /**
     * Modify a String for printing text style along with both forground and
     * background colours.
     * 
     * @param text
     * @param attr
     * @param foreground
     * @param background
     * @return
     */
    public static String format(String text,
                                Color foreground,
                                Color background,
                                Attribute attr)
    {
        StringBuilder buff = new StringBuilder();
        if (attr != null) buff.append(attr);

        if (foreground != null)
        {
            if (buff.length() > 0) buff.append(SEPARATOR);
            buff.append(30 + foreground.ordinal());
        }
        if (background != null)
        {
            if (buff.length() > 0) buff.append(SEPARATOR);
            buff.append(40 + background.ordinal());
        }
        buff.insert(0, PREFIX);
        buff.append(SUFFIX);
        buff.append(text);
        buff.append(END);
        return buff.toString();
    }


    ///////////////////////////////////////////////////////
    ////    other formatting
    ///////////////////////////////////////////////////////

    //TODO: Multi coloured single lines
    //  Need a new class for fgnd, bgnd, attr?
    //  Ordered Map?
}
