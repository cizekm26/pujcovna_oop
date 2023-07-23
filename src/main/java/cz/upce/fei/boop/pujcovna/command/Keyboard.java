package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.util.Format;
import java.util.Scanner;

public final class Keyboard {

    private static Scanner keyboard = new Scanner(System.in);

    private Keyboard() {

    }

    public static String getStringItem(String vyzva) {
        System.out.print(vyzva);
        String text = keyboard.nextLine().trim();
        return text;
    }

    public static int getIntItem(String vyzva) {
        boolean opakuj;
        int cislo = 0;
        do {
            opakuj = false;
            keyboard = new Scanner(System.in);
            try {
                System.out.print(vyzva);
                cislo = keyboard.nextInt();
            } catch (java.util.InputMismatchException ex) {
                opakuj = true;
                System.out.println("Neplatná hodnota");
            }
        } while (opakuj);
        keyboard.nextLine();
        return cislo;
    }
    
    public static double getDoubleItem(String vyzva) {
        boolean opakuj;
        double cislo = 0;
        do {
            opakuj = false;
            keyboard = new Scanner(System.in);
            try {
                System.out.print(vyzva);
                cislo = keyboard.nextDouble();
            } catch (java.util.InputMismatchException ex) {
                opakuj = true;
                System.out.println("Neplatná hodnota");
            }
        } while (opakuj);
        keyboard.nextLine();
        return cislo;
    }
    
    public static Format getFormatItem(String vyzva) {
        boolean opakuj;
        Format format = null;
        do {
            opakuj = false;
            keyboard = new Scanner(System.in);
            String nazev = getStringItem(vyzva);
            format = Format.dekoduj(nazev);
            if(format == null){
                opakuj = true;
                System.out.println("Zadejte formát");
            }
        } while (opakuj);
        return format;
    }
}
