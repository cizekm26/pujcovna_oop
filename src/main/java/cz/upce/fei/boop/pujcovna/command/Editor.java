package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import java.util.function.Function;

public class Editor implements Function<Kniha, Kniha> {

    @Override
    public Kniha apply(Kniha kniha) {
        if(kniha.getNazev() != null)
            System.out.println(kniha);
        kniha.setNazev(Keyboard.getStringItem("Zadejte název: "));
        kniha.setAutor(Keyboard.getStringItem("Zadejte jméno autora: "));
        switch (kniha.getTyp()) {
            case AUDIO -> {
                ((Audiokniha) kniha).setDelka(Keyboard.getIntItem("Zadejte délku (minuty): "));
                ((Audiokniha) kniha).setVelikost(Keyboard.getIntItem("Zadejte velikost (MB): "));
            }
            case ELEKTRONICKA -> {
                ((EKniha) kniha).setFormat(Keyboard.getFormatItem("Zadejte formát (pdf/mobi/epub): "));
                ((EKniha) kniha).setPocetStran(Keyboard.getIntItem("Zadejte počet stran: "));
            }
            case PAPIROVA -> {
                ((PapirovaKniha) kniha).setHmotnost(Keyboard.getDoubleItem("Zadejte hmotnost (g): "));
                ((PapirovaKniha) kniha).setPocetStran(Keyboard.getIntItem("Zadejte počet stran: "));
            }
        }
        return kniha;
    }
}
