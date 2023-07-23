package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.KnihaKlic;
import cz.upce.fei.boop.pujcovna.generator.Generator;
import cz.upce.fei.boop.pujcovna.kolekce.KolekceException;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;
import cz.upce.fei.boop.pujcovna.perzistence.Perzistence;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class SpravaKnih implements Ovladani {

    private Seznam<Kniha> seznamKnih;
    private Comparator<? super Kniha> comparator;
    private Consumer<String> errorlog;

    private SpravaKnih(Consumer<String> errorlog) {
        this.errorlog = errorlog;
    }
    
    public static SpravaKnih vytvorSpravce(Consumer<String> errorlog,
            Supplier<Seznam<Kniha>> creator) {
        SpravaKnih spravce = new SpravaKnih(errorlog);
        spravce.vytvorSeznam(creator);
        return spravce;
    }
    
    @Override
    public void vytvorSeznam(Supplier<Seznam<Kniha>> supplier) {
        seznamKnih = supplier.get();
    }
    
    @Override
    public void nastavKomparator(Comparator<? super Kniha> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void nastavErrorLog(Consumer<String> errorlog) {
        this.errorlog = errorlog;
    }

    @Override
    public void vlozPrvek(Kniha kniha) {
        try{
            seznamKnih.vlozPosledni(kniha);
        }catch(Exception ex){
            error(ex);
        }
    }

    @Override
    public void edituj(Function<Kniha, Kniha> editor) {
        try {
            editor.apply(seznamKnih.dejAktualni());
        } catch (KolekceException ex) {
            error(ex);
        }
    }

    @Override
    public Kniha vyjmiAktualni() {
        try {
            return seznamKnih.odeberAktualni();
        } catch (KolekceException ex) {
            error(ex);
        }
        return null;
    }

    @Override
    public Kniha odeber(int id) {
        try {
            seznamKnih.nastavPrvni();
            while (seznamKnih.dejAktualni() != null) {
                if (seznamKnih.dejAktualni().getId() == id) {
                    return seznamKnih.odeberAktualni();
                }
                seznamKnih.dalsi();
            }
            return null;
        } catch (KolekceException ex) {
            return null;
        }
    }

    @Override
    public int dejPocet() {
        return seznamKnih.size();
    }

    @Override
    public void obnov(String nazev) {
        try {
            Perzistence.nacti(nazev, seznamKnih);
        } catch (IOException ex) {
            error(ex);
        }
    }

    @Override
    public void nactiText(String soubor, Function<String, Kniha> mapper) {
        try {
            seznamKnih.zrus();
            Files.lines(Paths.get(soubor), StandardCharsets.UTF_8)
                    .filter(k -> k != null)
                    .map(mapper)
                    .forEach((Kniha k) -> {
                        seznamKnih.vlozPosledni(k);
                    });
        } catch (IOException ex) {
            error(ex);
        }
    }

    @Override
    public void zalohuj(String nazev) {
        try {
            Perzistence.uloz(nazev, seznamKnih);
        } catch (IOException ex) {
            error(ex);
        }
    }

    @Override
    public void ulozText(String nazevSouboru, Function<Kniha,String> mapper) {
        try (PrintWriter pw = new PrintWriter(nazevSouboru, "UTF-8")) {
            seznamKnih.stream()
                    .map(mapper)
                    .forEachOrdered(pw::println);
        } catch (IOException ex) {
            error(ex);
        }
    }

    @Override
    public void generuj(int pocetPrvku) {
        if (pocetPrvku > 0) {
            seznamKnih = Generator.generujSeznam(pocetPrvku);
        }else{
            error(new Exception("Neplatný počet prvků"));
        }
    }

    @Override
    public void zrus() {
        seznamKnih.zrus();
    }

    @Override
    public Stream<Kniha> dejPrvky() {
        return seznamKnih.stream();
    }

    @Override
    public Kniha najdiPrvek(KnihaKlic klic) {
        Kniha kniha = null;
        try {
            kniha = seznamKnih.stream().filter((p) -> comparator.compare(p, klic) == 0)
                    .findFirst().get();
        } catch (NoSuchElementException ex) {
            error(ex);
        }
        return kniha;
    }

    @Override
    public void nastavPrvni() {
        try {
            seznamKnih.nastavPrvni();
        } catch (KolekceException ex) {
            error(ex);
        }
    }

    @Override
    public void nastavPosledni() {
        try {
            seznamKnih.nastavPosledni();
        } catch (KolekceException ex) {
            error(ex);
        }
    }

    @Override
    public void nastavDalsi() {
        try {
            seznamKnih.dalsi();
        } catch (KolekceException ex) {
            error(ex);
        }
    }

    @Override
    public Kniha dejAktualni() {
        try {
            return seznamKnih.dejAktualni().clone();
        } catch (KolekceException | CloneNotSupportedException ex) {
            error(ex);
        }
        return null;
    }

    @Override
    public Iterator<Kniha> iterator() {
        return seznamKnih.iterator();
    }
    
    private void error(Exception ex) {
        if (errorlog == null) {
            Logger.getLogger(SpravaKnih.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } else {
            errorlog.accept(SpravaKnih.class.getName() + " " + ex.getMessage());
        }

    }
}
