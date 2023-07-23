package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.KnihaKlic;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import cz.upce.fei.boop.pujcovna.kolekce.KolekceException;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.perzistence.KnihyMapper;
import cz.upce.fei.boop.pujcovna.spravce.Ovladani;
import cz.upce.fei.boop.pujcovna.spravce.SpravaKnih;
import java.io.IOException;
import java.util.Comparator;
import java.util.function.Function;

public class CommandLineMain {
    
    private static CommandLineMain commandLine;
    private static final Comparator<Kniha> komparator = (p1, p2) -> Long.compare(p1.getId(),p2.getId());
    
    private static final String SOUBOR_BIN = "zaloha.bin";
    private static final String SOUBOR_TXT = "soubor.txt";
    private final Ovladani spravce;
    
    public CommandLineMain(){
        spravce = SpravaKnih.vytvorSpravce(System.out::println,
                SpojovySeznam<Kniha>::new);
        spravce.nastavKomparator(komparator);
    }
    
    public static void main(String[] args) throws KolekceException, IOException {
        commandLine = new CommandLineMain();
        commandLine.run();
    }
    
    public void run(){
       while (true) {
            String prikaz = Keyboard.getStringItem("Zadejte příkaz: ").toLowerCase().trim();
            switch (prikaz) {
                case "h", "help" -> vypisPrikazy();
                case "no", "novy" -> vlozNovou();
                case "n", "na", "najdi" -> najdi();
                case "odeber" -> {
                    int id = Keyboard.getIntItem("Zadejte id knihy: ");
                    Kniha kniha = spravce.odeber(id);
                    if(kniha != null)
                        System.out.println(kniha);
                    else
                        System.out.println("Kniha nebyla nalezena");
               }
                case "dej" -> 
                    System.out.println(spravce.dejAktualni());
                case "edit", "edituj" -> edituj();
                case "vyjmi" -> 
                    System.out.println("Odebraná: " + spravce.vyjmiAktualni());
                case "pr", "prvni" -> spravce.nastavPrvni();
                case "da", "dalsi" -> {
                     spravce.nastavDalsi();
               }
                case "po", "posledni" -> spravce.nastavPosledni();
                case "pocet" -> {
                    System.out.println("Počet knih: " + spravce.dejPocet());
                }
                case "obnov" -> spravce.obnov(SOUBOR_BIN);
                case "zalohuj" -> spravce.zalohuj(SOUBOR_BIN);
                case "vypis" -> spravce.dejPrvky().forEach(kniha -> System.out.println(kniha));
                case "nactitext", "nt" -> nactiText();
                case "uloztext", "ut" -> ulozText();
                case "generuj", "g" -> {
                    int pocet = Keyboard.getIntItem("Zadejte počet generovaných: ");
                    spravce.generuj(pocet);
                }
                case "zrus" -> spravce.zrus();
                case "exit" -> System.exit(0);
                default -> System.out.println("Neplatný příkaz");
            }
        }
    }
    
    private void vypisPrikazy() {
        System.out.println("""
                           help, h - výpis příkazů
                           novy,no - vytvoř novou instanci a vlož data na konec
                           najdi,na,n - najdi v seznamu data podle hodnoty nějakém atributu
                           dej - zobraz aktuální data v seznamu
                           edituj,edit - edituj aktuální data v seznamu
                           vyjmi - vyjmi aktuální data ze seznamu
                           prvni,pr - nastav jako aktuální první data v seznamu
                           dalsi,da - přejdi na další data
                           posledni,po - přejdi na poslední data
                           pocet - zobraz pocet polozek v seznamu
                           obnov - obnov seznam dat z binárního souboru
                           zalohuj - zalohuj seznam dat do binárního souboru
                           vypis - zobraz seznam dat
                           nactitext,nt- načti seznam dat z textového souboru
                           uloztext,ut - ulož seznam dat do textového souboru
                           generuj,g - generuj náhodné data pro testování
                           zrus - zruš všechny data v seznamu
                           exit - ukončení programu
                           """);
    }
    
    private void vlozNovou(){
        Function<Kniha, Kniha> editor = new Editor();
        Kniha kniha = null;
        String typ = Keyboard.getStringItem("Zadejte typ knihy (ak/ek/pk): ");
        switch (typ.trim().toLowerCase()) {
            case "ak" -> kniha = new Audiokniha();
            case "ek" -> kniha = new EKniha();
            case "pk" -> kniha = new PapirovaKniha();
            default -> System.out.println("Neplátná volba");
        }
        spravce.vlozPrvek(editor.apply(kniha));
    }
    
    private void najdi(){
        spravce.nastavKomparator(komparator);
        int id = Keyboard.getIntItem("Zadejte id knihy: ");
        Kniha kniha;
        kniha = spravce.najdiPrvek(new KnihaKlic(id));
        if(kniha != null)
            System.out.println(kniha);
        else
            System.out.println("Kniha nebyla nalezena");
    }
    
    private void edituj(){
        Function<Kniha, Kniha> editor = new Editor();
        spravce.edituj(editor);
    }
    
    private void nactiText() {
        spravce.nactiText(SOUBOR_TXT, KnihyMapper.mapperInput);
    }

    private void ulozText() {
        spravce.ulozText(SOUBOR_TXT, KnihyMapper.mapperOutput);
    }
}
