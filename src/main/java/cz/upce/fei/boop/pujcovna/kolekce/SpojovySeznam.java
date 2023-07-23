package cz.upce.fei.boop.pujcovna.kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SpojovySeznam<E> implements Seznam<E> {

    private class Prvek {
        E data;
        Prvek dalsi;

        public Prvek(E data, Prvek dalsi) {
            this.data = data;
            this.dalsi = dalsi;
        }
    }
        
    private Prvek prvni;
    private Prvek posledni;
    private Prvek aktualni;
    private int pocet = 0;
    
    @Override
    public void nastavPrvni() throws KolekceException {
        if(prvni == null)
            throw new KolekceException("Seznam je prázdný");
        aktualni = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if(posledni == null)
            throw new KolekceException("Seznam je prázdný");
        aktualni = posledni;
    }

    @Override
    public void dalsi() throws KolekceException {
        if (!jeDalsi()) {
            throw new KolekceException("Neexistuje další prvek");
        }
        aktualni = aktualni.dalsi;
    }

    @Override
    public boolean jeDalsi() {
        return aktualni != null && aktualni != posledni;
    }

    @Override
    public void vlozPrvni(E data) {
        if(data == null){
            throw new NullPointerException();
        }
        if (prvni == null) {
            prvni = new Prvek(data, null);
            posledni = prvni;
        } else {
            prvni = new Prvek(data, prvni);
        }
        pocet++;
    }

    @Override
    public void vlozPosledni(E data) {
        if(data == null)
            throw new NullPointerException();
        if (prvni == null) {
            prvni = new Prvek(data, null);
            posledni = prvni;
        } else {
            posledni.dalsi = new Prvek(data, null);
            posledni = posledni.dalsi;
        }
        pocet++;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if(data == null)
            throw new NullPointerException();
        if (aktualni == null) 
            throw new KolekceException("Není nastaven aktuální");
        if (aktualni.dalsi == null) {
            aktualni.dalsi = new Prvek(data, null);
            posledni = aktualni.dalsi;
        } else {
            Prvek novyPrvek = new Prvek(data, aktualni.dalsi);
            aktualni.dalsi = novyPrvek;
        }
        pocet++;
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Seznam je prázdný");
        return prvni.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Seznam je prázdný");
        return posledni.data;
    }
    @Override
    public E dejAktualni() throws KolekceException {
        if(jePrazdny() || aktualni == null)
            throw new KolekceException("Není nastaven aktuální");
        return aktualni.data;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        if(jePrazdny() || !jeDalsi())
            throw new KolekceException("Není nastaven aktuální");
        return aktualni.dalsi.data;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Seznam je prázdný");
        E odebrany = prvni.data;
        if(prvni == posledni){
            zrus();
            return odebrany;
        }
        if(prvni == aktualni)
            aktualni = null;
        prvni = prvni.dalsi;
        pocet--;
        return odebrany;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Seznam je prázdný");
        E odebrany = posledni.data;
        if(prvni == posledni){
            zrus();
            return odebrany;
        }
        if(posledni == aktualni)
            aktualni = null;
        Prvek predposledni = dejPrvekPred(posledni);
        predposledni.dalsi = null;
        posledni = predposledni;
        pocet--;
        return odebrany;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if(jePrazdny() || aktualni == null)
            throw new KolekceException("Není nastaven aktuální");
        E odebrany;
        if(aktualni == prvni)
            odebrany = odeberPrvni();
        else if(aktualni == posledni)
            odebrany = odeberPosledni();
        else{
            Prvek predAktualnim = dejPrvekPred(aktualni);
            predAktualnim.dalsi = aktualni.dalsi;
            odebrany = aktualni.data;   
            aktualni = null;
            pocet--;
        }
        return odebrany;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if(jePrazdny() || !jeDalsi())
            throw new KolekceException("Není nastaven aktuální");
        if(aktualni.dalsi == posledni)
            posledni = aktualni;
        E odebrany = aktualni.dalsi.data;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        pocet--;
        return odebrany;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        aktualni = null;
        posledni = null;
        prvni = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Prvek aktualni = prvni;

            @Override
            public boolean hasNext() {
                return aktualni != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E data = aktualni.data;
                    aktualni = aktualni.dalsi;
                    return data;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
    
    private Prvek dejPrvekPred(Prvek prvek){
        Prvek pred = prvni;
        while(pred.dalsi != prvek)
            pred = pred.dalsi;
        return pred;
    }
}
