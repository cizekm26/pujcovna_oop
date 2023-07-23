package cz.upce.fei.boop.pujcovna.data;

import cz.upce.fei.boop.pujcovna.util.Format;
import cz.upce.fei.boop.pujcovna.util.TypKnihy;

public final class EKniha extends Kniha{
    private int pocetStran;
    private Format format;
    
    public EKniha(){
        super(TypKnihy.ELEKTRONICKA);
    }
    
    public EKniha(long id, String nazev, String autor, int pocetStran, Format format){
        super(id, TypKnihy.ELEKTRONICKA,nazev,autor);
        this.format = format;
        this.pocetStran = pocetStran;
    }
    
    public EKniha(String nazev, String autor, int pocetStran, Format format){
        super(TypKnihy.ELEKTRONICKA,nazev,autor);
        this.format = format;
        this.pocetStran = pocetStran;
    }

    public int getPocetStran() {
        return pocetStran;
    }

    public void setPocetStran(int pocesStran) {
        this.pocetStran = pocesStran;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return super.toString() + ", počet stran=" + pocetStran + ", formát=" + format.getNazev();
    }
    
    
}
