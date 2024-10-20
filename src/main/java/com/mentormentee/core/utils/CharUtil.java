package com.mentormentee.core.utils;


public class CharUtil {
    public static boolean isEnglish(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    public static boolean isKorean(char ch) {
        return ch >= 0xAC00 && ch <= 0xD7A3;
    }

    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isSpecial(char ch) {
        return (ch >= '!' && ch <= '/') // !"#$%&'()*+,-./
                || (ch >= ':' && ch <= '@') // :;<=>?@
                || (ch >= '[' && ch <= '`') // [\]^_`
                || (ch >= '{' && ch <= '~'); // {|}~
    }
}

