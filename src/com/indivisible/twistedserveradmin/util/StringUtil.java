package com.indivisible.twistedserveradmin.util;


public class StringUtil
{

    //TODO: CHAR_DIV_MAJOR and CHAR_DIV_MINOR
    public static final char CHAR_DIV = '-';
    public static final char CHAR_SEP = '|';
    //TODO: DIV_MAJOR(int termWidth) and DIV_MINOR(int termWidth) instead of hard-coding these divs
    public static final String DIV_60 = "------------------------------------------------------------";
    public static final String DIV_70 = "----------------------------------------------------------------------";

    private static final int MIN_WIDTH = 2;

    /**
     * Return a space padded, left aligned String of 'width' length.
     * 
     * @param text
     * @param width
     * @return
     */
    public static String leftAlign(String text, int width)
    {
        if (width < MIN_WIDTH) width = MIN_WIDTH;
        String format = "%1$-" + width + "s";
        return String.format(format, text);
    }

    /**
     * Return a space padded, right aligned String of 'width' length.
     * 
     * @param text
     * @param width
     * @return
     */
    public static String rightAlign(String text, int width)
    {
        if (width < MIN_WIDTH) width = MIN_WIDTH;
        String format = "%1$" + width + "s";
        return String.format(format, text);
    }

}
