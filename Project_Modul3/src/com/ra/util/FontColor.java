package com.ra.util;

public class FontColor {
    public static String err(String mess){
        return "\033[0;31m"+ mess + "\033[0m";
    }
    public static String success(String mess){
        return "\033[0;32m" + mess + "\033[0m";
    }
    public static String warning(String mess){
        return "\033[0;33m"+ mess +"\033[0m";
    }
    public static String info(String mess){
        return "\033[0;34m"+ mess +"\033[0m";
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
