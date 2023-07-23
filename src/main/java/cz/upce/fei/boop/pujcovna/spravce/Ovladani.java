package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.KnihaKlic;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Ovladani extends Iterable<Kniha>{
    /**
     * Vrací všechny prvky ze seznamu
     * @return seznam prvků převedený na datový proud
     */
    Stream<Kniha> dejPrvky();
    /**
     * Vytvoří nový objekt seznamu
     * @param supplier obsahuje instanci seznamu
     */
    void vytvorSeznam(Supplier<Seznam<Kniha>> supplier);
    /**
     * Nastaví metodu, kterou se budou porovnávat prvky seznamu
     * @param comparator 
     */
    void nastavKomparator(Comparator<? super Kniha> comparator);
    /**
     * Nastaví způsob výpisu chybových hlášení
     * @param errorlog 
     */
    void nastavErrorLog(Consumer<String> errorlog);
    /**
     * Vloží novou knihu na konec seznamu
     * @param kniha - nová kniha
     */
    void vlozPrvek(Kniha kniha);
    /**
     * Umožňuje změnit atributy aktuálního prvku
     * @param editor 
     */
    void edituj(Function<Kniha, Kniha> editor);
    /**
     * Odebere aktuální prvek ze seznamu
     * @return odebraný prvek
     */
    Kniha vyjmiAktualni();
    /**
     * Odebere prvek s vybraným id
     * @param id - id odebíraného prvku
     * @return odebraný prvek
     */
    Kniha odeber(int id);
    /**
     * Hledá prvek v seznamu, který má shodné atributy jako klíč
     * @param klic - odkaz na objekt klíče
     * @return nalezený prvek
     */
    Kniha najdiPrvek(KnihaKlic klic);
    /**
     * Nastaví první prvek jako aktuální
     */
    void nastavPrvni();
    /**
     * Nastaví poslední prvek jako aktuální
     */
    void nastavPosledni();
    /**
     * Nastaví prvek za aktuálním jako aktuální
     */
    void nastavDalsi();
    /**
     * Vrací počet prvků v seznamu
     * @return 
     */
    int dejPocet();
    /**
     * Načte položky z binárního souboru
     * @param soubor - název souboru
     */
    void obnov(String soubor);
    /**
     * Načte položky z textového souboru
     * @param soubor - nazev souboru
     * @param mapper - načítá prvkek ze zformátovaného řádku textu
     */
    void nactiText(String soubor, Function<String, Kniha> mapper);
    /**
     * Uloží položky do binárního souboru
     * @param soubor - název souboru 
     */
    void zalohuj(String soubor);
    /**
     * Uloží položky do textového souboru
     * @param soubor - název souboru
     * @param mapper - zformátuje položku do řádku textu
     */
    void ulozText(String soubor, Function<Kniha,String> mapper);
    /**
     * Vytvoří nový seznam obsahující generované prvky
     * @param pocetPrvku - počet generovaných prvků
     */
    void generuj(int pocetPrvku);
    /**
     * Vymaže všechny položky seznamu
     */
    void zrus();
    /**
     * Vrací kopii aktuálního prvku
     * @return 
     */
    Kniha dejAktualni();

}
