package ua.lviv.navpil;

import java.util.Random;

public class RandomTextSequenceGenerator {

    public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890/*-+={}[],.";

    public static void main(String[] args) {
        String g = generate(12);
        System.out.println(g);
    }

    private static String generate(int length) {
        StringBuilder g = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            g.append(CHARS.charAt(r.nextInt(CHARS.length())));
        }
        return g.toString();
    }
}
