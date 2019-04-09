package ru.org.autotest.utils;

import java.util.Random;

public class WrikeUtils {

    private static final String EMAIL_ALLOWED_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String EMAIL_ALLOWED_SYMBOLS = "!#$%&'*+-/=?^_`{|}~";
    private static final String EMAIL_ALLOWED_NUMBERS = "0123456789";
    private static final String LETTERS_AND_NUMBERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private WrikeUtils() {}

    // generate random answers
    public static int getRandomAnswer(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static char[] getValidEmailChars() {
        return (EMAIL_ALLOWED_LETTERS + EMAIL_ALLOWED_SYMBOLS + EMAIL_ALLOWED_NUMBERS).toCharArray();
    }

    public static String generateValidEmailName() {
        char[] chars = getValidEmailChars();
        StringBuilder email= new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            email.append(c);
        }
        return email.toString();
    }

    public static String generateAnswerText() {
        char[] LETTERS_AND_NUMBERS_ARRAY = LETTERS_AND_NUMBERS.toCharArray();
        StringBuilder answer= new StringBuilder(10);
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            char c = LETTERS_AND_NUMBERS_ARRAY[rnd.nextInt(LETTERS_AND_NUMBERS_ARRAY.length)];
            answer.append(c);
        }
    return answer.toString();
    }
}
