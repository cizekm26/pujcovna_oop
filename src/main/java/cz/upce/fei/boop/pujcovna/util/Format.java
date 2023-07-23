package cz.upce.fei.boop.pujcovna.util;

public enum Format {
    EPUB("epub"), MOBI("mobi"), PDF("pdf");
    
    
    private final String nazev;
    
    private Format(String format){
        this.nazev = format;
    }

    public String getNazev() {
        return nazev;
    }
    
    public static Format dekoduj(String format){
        switch(format){
            case "epub" -> {
                return EPUB;
            }
            case "mobi" -> {
                return MOBI;
            }
            case "pdf" -> {
                return PDF;
            }
        }
        return null;
    }
}
