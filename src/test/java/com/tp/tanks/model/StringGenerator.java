package com.tp.tanks.model;

import java.util.concurrent.ThreadLocalRandom;

class StringGenerator {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase();

    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    static String generateString(int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = alphanum.charAt(threadLocalRandom.nextInt(alphanum.length()));
        }
        return new String(text);
    }
}
