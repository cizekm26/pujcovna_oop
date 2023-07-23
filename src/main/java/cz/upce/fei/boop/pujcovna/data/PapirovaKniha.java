package cz.upce.fei.boop.pujcovna.data;

import cz.upce.fei.boop.pujcovna.util.TypKnihy;
import java.util.Locale;

public final class PapirovaKniha extends Kniha{
    private int pocetStran;
    private double hmotnost;
    
    public PapirovaKniha(){
        super(TypKnihy.PAPIROVA);
    }

    public PapirovaKniha(long id, String nazev, String autor, int pocetStran, double hmotnost) {
        super(id, TypKnihy.PAPIROVA, nazev, autor);
        this.hmotnost = hmotnost;
        this.pocetStran = pocetStran;
    }
    
    public PapirovaKniha(String nazev, String autor, int pocetStran, double hmotnost) {
        super(TypKnihy.PAPIROVA, nazev, autor);
        this.hmotnost = hmotnost;
        this.pocetStran = pocetStran;
    }

    public int getPocetStran() {
        return pocetStran;
    }

    public void setPocetStran(int pocetStran) {
        this.pocetStran = pocetStran;
    }

    public double getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(double hmotnost) {
        this.hmotnost = hmotnost;
    }

    @Override
    public String toString() {
        return super.toString() + " počet stran=" + pocetStran + ", hmotnost=" + String.format(Locale.ENGLISH,"%.1fg",hmotnost);
    }
    
    
}
