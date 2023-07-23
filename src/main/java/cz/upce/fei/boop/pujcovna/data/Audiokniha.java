package cz.upce.fei.boop.pujcovna.data;

import cz.upce.fei.boop.pujcovna.util.TypKnihy;

public final class Audiokniha extends Kniha{
    private int delka;
    private int velikost;
    
    public Audiokniha(){
        super(TypKnihy.AUDIO);
    }

    public Audiokniha(String nazev, String autor, int delka, int velikost) {
        super(TypKnihy.AUDIO, nazev, autor);
        this.delka = delka;
        this.velikost = velikost;
    }
    
    public Audiokniha(long id, String nazev, String autor, int delka, int velikost) {
        super(id, TypKnihy.AUDIO, nazev, autor);
        this.delka = delka;
        this.velikost = velikost;
    }

    public int getDelka() {
        return delka;
    }

    public void setDelka(int delka) {
        this.delka = delka;
    }

    public int getVelikost() {
        return velikost;
    }

    public void setVelikost(int velikost) {
        this.velikost = velikost;
    }

    @Override
    public String toString() {
        return super.toString() + " délka=" + delka + "min, velikost=" + velikost + "MB";
    }
    
    
}
