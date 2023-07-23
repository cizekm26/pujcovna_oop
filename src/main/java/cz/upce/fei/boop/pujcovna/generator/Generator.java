package cz.upce.fei.boop.pujcovna.generator;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.util.Format;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import cz.upce.fei.boop.pujcovna.util.TypKnihy;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;

public final class Generator {

    private static int pocetKnih = 0;

    private Generator() {

    }

    public static SpojovySeznam<Kniha> generujSeznam(int pocet) {
        if(pocet <= 0)
            return null;
        SpojovySeznam<Kniha> seznam = new SpojovySeznam<>();
        for (int i = 0; i < pocet; i++) {
            seznam.vlozPosledni(generujKnihu());
        }
        return seznam;
    }

    private static Kniha generujKnihu() {
        Kniha kniha = null;
        int pocet = TypKnihy.getTypy().length;
        int typ = generujInt(0, pocet);
        switch (TypKnihy.values()[typ]) {
            case PAPIROVA -> kniha = generujPapirovouKnihu();
            case ELEKTRONICKA -> kniha = generujEKnihu();
            case AUDIO -> kniha = generujAudioknihu();
        }
        pocetKnih++;
        return kniha;
    }

    private static PapirovaKniha generujPapirovouKnihu() {
        String nazev = "kniha" + pocetKnih;
        String autor = "autor" + pocetKnih;
        int pocetStran = generujInt(50, 1000);
        double hmotnost = generujDouble(1000,5000);
        return new PapirovaKniha(nazev, autor, pocetStran, hmotnost);
    }

    private static EKniha generujEKnihu() {
        String nazev = "kniha" + pocetKnih;
        String autor = "autor" + pocetKnih;
        int pocetStran = generujInt(50, 1000);
        Format format = generujFormat();
        return new EKniha(nazev, autor, pocetStran, format);
    }

    private static Audiokniha generujAudioknihu() {
        String nazev = "kniha" + pocetKnih;
        String autor = "autor" + pocetKnih;
        int delka = generujInt(60, 1800);
        int velikost = generujInt(100, 1000);
        return new Audiokniha(nazev, autor, delka, velikost);
    }

    private static Format generujFormat() {
        int pocet = Format.values().length;
        int i = generujInt(0,pocet); 
        return Format.values()[i];
    }

    private static int generujInt(int minimum, int maximum) {
        return (int) (Math.random() * (maximum - minimum) + minimum);
    }
    
    private static double generujDouble(double minimum, double maximum) {
        return Math.random() * (maximum - minimum) + minimum;
    }
}
