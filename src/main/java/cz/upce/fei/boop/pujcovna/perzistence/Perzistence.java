package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.kolekce.Seznam;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Objects;

public final class Perzistence {

    public static <E> void uloz(String soubor, Seznam<E> seznam) throws IOException {
        try {
            Objects.requireNonNull(seznam);

            try (ObjectOutputStream vystup = new ObjectOutputStream(
                    new FileOutputStream(soubor))) {
                vystup.writeInt(seznam.size());

                Iterator<E> it = seznam.iterator();
                while (it.hasNext()) {
                    vystup.writeObject(it.next());
                }
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    public static <E> Seznam<E> nacti(String soubor, Seznam<E> seznam)
            throws IOException {
        try {
            Objects.requireNonNull(seznam);
            try (ObjectInputStream vstup = new ObjectInputStream(
                    new FileInputStream(soubor))) {
                seznam.zrus();

                int pocet = vstup.readInt();
                for (int i = 0; i < pocet; i++) {
                    seznam.vlozPosledni((E) vstup.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new IOException(ex);
        }
        return seznam;
    }
}
