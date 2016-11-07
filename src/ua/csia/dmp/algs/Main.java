package ua.csia.dmp.algs;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890/*-+={}[],.";
        System.out.println(str.length());

        String g = "";
        Random r = new Random();
        for (int i = 0; i < 12; i++) {
            g += str.charAt(r.nextInt(str.length()));
        }
        System.out.println(g);

    }
}
