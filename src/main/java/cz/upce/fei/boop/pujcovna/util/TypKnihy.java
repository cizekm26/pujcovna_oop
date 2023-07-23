package cz.upce.fei.boop.pujcovna.util;

public enum TypKnihy {
    ELEKTRONICKA("elektronická"),
    AUDIO("audio"),
    PAPIROVA("papírová"),
    NON_FILTER("nefiltruj");
    
    private final String nazev;
    
    private TypKnihy(String nazev){
        this.nazev = nazev;
    }
    
    public String getNazev(){
        return nazev;
    }
    
    public static Enum[] getTypy(){
        Enum[] vycet = {ELEKTRONICKA, AUDIO, PAPIROVA};
        return vycet;
    }
}
