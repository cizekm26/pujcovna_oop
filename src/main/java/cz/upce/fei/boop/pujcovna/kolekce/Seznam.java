package cz.upce.fei.boop.pujcovna.kolekce;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Seznam<E> extends Iterable<E> {

    void nastavPrvni() throws KolekceException;

    void nastavPosledni() throws KolekceException;

    void dalsi() throws KolekceException;

    boolean jeDalsi();

    void vlozPrvni(E data);

    void vlozPosledni(E data);

    void vlozZaAktualni(E data) throws KolekceException;

    boolean jePrazdny();

    E dejPrvni() throws KolekceException;

    E dejPosledni() throws KolekceException;

    E dejAktualni() throws KolekceException;

    E dejZaAktualnim() throws KolekceException;

    E odeberPrvni() throws KolekceException;

    E odeberPosledni() throws KolekceException;

    E odeberAktualni() throws KolekceException;

    E odeberZaAktualnim() throws KolekceException;

    int size();

    void zrus();

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
