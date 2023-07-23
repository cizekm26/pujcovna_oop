package cz.upce.fei.boop.pujcovna.data;

import cz.upce.fei.boop.pujcovna.util.TypKnihy;
import java.io.Serializable;

public abstract class Kniha implements Serializable, Cloneable{
    private static long pocet = 0;
    private final long id;
    private String nazev;
    private TypKnihy typ;
    private String autor;
    
    public Kniha(TypKnihy typ){
        this.typ = typ;
        this.id = pocet;
        pocet = this.id + 1;
    }

    public Kniha(long id, TypKnihy typ, String nazev, String autor){
        this.nazev = nazev;
        this.typ = typ;
        this.autor = autor;
        this.id = id;
        pocet = this.id + 1;
    }
    
    public Kniha(long id){
        this.id = id;
    }
    
    public Kniha(TypKnihy typ, String nazev, String autor) {
        this(pocet, typ, nazev, autor);
    }
    
    public long getId(){
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public TypKnihy getTyp() {
        return typ;
    }

    public void setTyp(TypKnihy typ) {
        this.typ = typ;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", název=" + nazev + ", typ=" + typ.getNazev() + ", autor=" + autor;
    }

    @Override
    public Kniha clone() throws CloneNotSupportedException {
        return (Kniha)super.clone();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kniha other = (Kniha) obj;
        return this.id == other.id;
    }
    
    
    
    
}
