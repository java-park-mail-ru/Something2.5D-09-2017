package com.tp.tanks.factories;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generators {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase();

    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static String generateString(int length)
    {
        final char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = alphanum.charAt(threadLocalRandom.nextInt(alphanum.length()));
        }
        return new String(text);
    }

    public static Double generateDouble() {
        double startVal = 0;
        double endVal = 10000;

        double randomDouble = new Random().nextDouble();
        return startVal + (randomDouble * (endVal - startVal));
    }

    public static Boolean generateBoolean() {
        return new Random().nextBoolean();
    }

    public static Integer geterateInteger() {
        return ThreadLocalRandom.current().nextInt(0, 10000);
    }

    public static Long generateLong() {
        return new Random().nextLong();
    }
}
