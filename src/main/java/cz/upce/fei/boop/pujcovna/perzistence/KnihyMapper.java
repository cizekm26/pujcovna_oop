package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import cz.upce.fei.boop.pujcovna.util.Format;
import static cz.upce.fei.boop.pujcovna.util.TypKnihy.AUDIO;
import static cz.upce.fei.boop.pujcovna.util.TypKnihy.ELEKTRONICKA;
import static cz.upce.fei.boop.pujcovna.util.TypKnihy.PAPIROVA;
import java.util.Locale;
import java.util.function.Function;

public final class KnihyMapper {

    public static final Function<String, Kniha> mapperInput = (line) -> {
        if (line.length() == 0) {
            return null;
        }
        Kniha kniha = null;
        String[] data = line.split("; ");
        String typ = data[0].trim().toLowerCase();
        long id = Long.parseLong(data[1]);
        String nazev = data[2].trim();
        String autor = data[3].trim();
        int pocetStran;
        switch (typ) {
            case "ak" -> {
                int delka = Integer.parseInt(data[4]);
                int velikost = Integer.parseInt(data[5]);
                kniha = new Audiokniha(id, nazev, autor, delka, velikost);
            }
            case "ek" -> {
                pocetStran = Integer.parseInt(data[4]);
                Format format = Format.dekoduj(data[5]);
                kniha = new EKniha(id, nazev, autor, pocetStran, format);
            }
            case "pk" -> {
                pocetStran = Integer.parseInt(data[4]);
                double hmotnost = Double.parseDouble(data[5]);
                kniha = new PapirovaKniha(id, nazev, autor, pocetStran, hmotnost);
            }
        }
        return kniha;
    };

    public static final Function<Kniha, String> mapperOutput = (kniha) -> {
        String radek = null;
        switch (kniha.getTyp()) {
            case PAPIROVA -> {
                PapirovaKniha pk = (PapirovaKniha) kniha;
                radek = String.format(Locale.ENGLISH, "pk; %d; %s; %s; %d; %.1f", pk.getId(),
                        pk.getNazev(), pk.getAutor(), pk.getPocetStran(), pk.getHmotnost());
            }
            case ELEKTRONICKA -> {
                EKniha ek = (EKniha) kniha;
                radek = String.format(Locale.ENGLISH, "ek; %d; %s; %s; %s; %s", ek.getId(), ek.getNazev(),
                        ek.getAutor(), ek.getPocetStran(), ek.getFormat().getNazev());
            }
            case AUDIO -> {
                Audiokniha ak = (Audiokniha) kniha;
                radek = String.format(Locale.ENGLISH, "ak; %d; %s; %s; %d; %d", ak.getId(), ak.getNazev(),
                        ak.getAutor(), ak.getDelka(), ak.getVelikost());
            }
        }
        return radek;
    };
}
