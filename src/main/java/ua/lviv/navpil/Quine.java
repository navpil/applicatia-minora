package ua.lviv.navpil;

public class Quine {
    public static void main(String[] args) {
        String s = "package ua.lviv.navpil;XXpublic class Quine {X    public static void main(String[] args) {X        String s = Z%SUBSTITUTE%Z;X        String s2 = s.replace((char)88, (char) 10).replace((char) 90, (char)34).replaceFirst(Z%SUBSTITUTE%Z, s);X        System.out.println(s2);X    }X}";
        String s2 = s.replace((char)88, (char) 10).replace((char) 90, (char)34).replaceFirst("%SUBSTITUTE%", s);
        System.out.println(s2);
    }
}