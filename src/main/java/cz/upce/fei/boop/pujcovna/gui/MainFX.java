package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.KnihaKlic;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import cz.upce.fei.boop.pujcovna.generator.Generator;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.perzistence.KnihyMapper;
import cz.upce.fei.boop.pujcovna.spravce.Ovladani;
import cz.upce.fei.boop.pujcovna.spravce.SpravaKnih;
import cz.upce.fei.boop.pujcovna.util.TypKnihy;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainFX extends Application {

    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 700;
    private static final String SOUBOR_BIN = "zaloha.bin";
    private static final String SOUBOR_TXT = "soubor.txt";
    public static Stage primaryStage;
    
    private final int POCET_GENEROVANYCH = 10;

    Consumer<String> alert = t -> {
        Alert alertWindow = new Alert(Alert.AlertType.INFORMATION, t);
        alertWindow.setTitle("Chyba");
        alertWindow.showAndWait();
    };
    private static final Comparator<Kniha> komparator = (p1, p2) -> Long.compare(p1.getId(), p2.getId());

    private Ovladani spravce;
    private ListView<Kniha> listView;
    private Filtr filtr = new Filtr(TypKnihy.NON_FILTER);

    private final EventHandler<ActionEvent> generujHandler = event -> {
        Generator.generujSeznam(POCET_GENEROVANYCH).forEach(v -> spravce.vlozPrvek(v));
        obnovListView();
    };
    private final EventHandler<ActionEvent> prejdiNaPrvniHandler = event -> {
        spravce.nastavPrvni();
        Kniha kniha = spravce.dejAktualni();
        obnovListKniha(kniha);
    };
    private final EventHandler<ActionEvent> prejdiNaDalsiHandler = event -> {
        spravce.nastavDalsi();
        Kniha kniha = spravce.dejAktualni();
        obnovListKniha(kniha);
    };
    private final EventHandler<ActionEvent> prejdiNaPosledniHandler = event -> {
        spravce.nastavPosledni();
        Kniha kniha = spravce.dejAktualni();
        obnovListKniha(kniha);
    };
    private final EventHandler<ActionEvent> filterHandler = event -> {
        TypKnihy typ = ((ComboBox<TypKnihy>) event.getSource()).getValue();
        filtr = new Filtr(typ);
        obnovListView();
    };
    private final EventHandler<ActionEvent> editujHandler = event -> {
        Function<Kniha, Kniha> editor = new Editor();
        spravce.edituj(editor);
        obnovListView();
    };
    private final EventHandler<ActionEvent> vyjmiHandler = event -> {
        spravce.vyjmiAktualni();
        obnovListView();
    };
    private final EventHandler<ActionEvent> zobrazHandler = event -> {
        obnovListView();
    };
    private final EventHandler<ActionEvent> clearHandler = event -> {
        listView.getItems().clear();
    };
    private final EventHandler<ActionEvent> ulozHandler = event -> {
        spravce.ulozText(SOUBOR_TXT, KnihyMapper.mapperOutput);
        obnovListView();
    };
    private final EventHandler<ActionEvent> nactiHandler = event -> {
        spravce.nactiText(SOUBOR_TXT, KnihyMapper.mapperInput);
        obnovListView();
    };
    private final EventHandler<ActionEvent> novaHandler = event -> {
        TypKnihy typ = ((ComboBox<TypKnihy>) event.getSource()).getValue();
        Function<Kniha, Kniha> editor = new Editor();
        Kniha novaKniha = null;
        switch(typ){
            case AUDIO -> novaKniha = new Audiokniha();
            case ELEKTRONICKA ->  novaKniha = new EKniha();
            case PAPIROVA -> novaKniha = new PapirovaKniha();
        }
        if(novaKniha != null)
            spravce.vlozPrvek(editor.apply(novaKniha));
        obnovListView();
    };
    private final EventHandler<ActionEvent> najdiHandler = event -> {
        int id = zobrazDialogId();
        Kniha kniha = (Kniha) spravce.najdiPrvek(new KnihaKlic(id));
        if (kniha != null) 
            listView.getSelectionModel().select(kniha);
    };
    private final EventHandler<ActionEvent> zalohujHandler = event -> {
        spravce.zalohuj(SOUBOR_BIN);
    };
    private final EventHandler<ActionEvent> obnovHandler = event -> {
        spravce.obnov(SOUBOR_BIN);
    };
    private final EventHandler<ActionEvent> zrusHandler = event -> {
        spravce.zrus();
    };
    private final EventHandler<ActionEvent> odeberHandler = event ->{
        int id = zobrazDialogId();
        Kniha kniha = (Kniha) spravce.odeber(id);
        if(kniha != null)
           obnovListView();
    };

    public MainFX() {
        spravce = SpravaKnih.vytvorSpravce(alert,
                SpojovySeznam<Kniha>::new);
        spravce.nastavKomparator(komparator);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        VBox root = new VBox();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        listView = createListView();
        ControlPanelVBox controlPanelMove = createPanelMoveCommands();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(listView, controlPanelMove);
        ControlPanelHBox controlPanel = createControlPanelCommands();
        root.getChildren().addAll(hbox, controlPanel);
        
        stage.setScene(scene);
        stage.setTitle("Správa knih");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private ListView<Kniha> createListView() {
        ListView<Kniha> list = new ListView<>();
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.setMinHeight(SCENE_HEIGHT - 60);
        list.setMinWidth(SCENE_WIDTH - 100);
        list.setCellFactory(cell -> {
            return new ListCell<Kniha>() {
                @Override
                protected void updateItem(Kniha item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
                        setText(item.toString());
                        setFont(Font.font("Monospaced", 20));
                    } else {
                        setText("");
                    }
                }
            };

        });
        return list;
    }

    private ControlPanelVBox createPanelMoveCommands() {
        ControlPanelVBox controlPanel = new ControlPanelVBox();
        controlPanel.pridejLabel("Procházení");
        controlPanel.pridejButton("první", prejdiNaPrvniHandler);
        controlPanel.pridejButton("dalsi", prejdiNaDalsiHandler);
        controlPanel.pridejButton("posledni", prejdiNaPosledniHandler);
        controlPanel.pridejLabel("Příkazy");
        controlPanel.pridejButton("Edituj", editujHandler);
        controlPanel.pridejButton("Vyjmi", vyjmiHandler);
        controlPanel.pridejButton("Zobraz", zobrazHandler);
        controlPanel.pridejButton("Clear", clearHandler);
        controlPanel.pridejButton("Odeber", odeberHandler);
        return controlPanel;
    }

    private ControlPanelHBox createControlPanelCommands() {
        ControlPanelHBox controlPanel = new ControlPanelHBox();
        controlPanel.pridejButton("Generuj", generujHandler);
        controlPanel.pridejButton("Ulož", ulozHandler);
        controlPanel.pridejButton("Načti", nactiHandler);
        controlPanel.pridejLabel("Nový");
        controlPanel.pridejComboBox("Nový", TypKnihy.getTypy(), novaHandler);
        controlPanel.pridejLabel("Filtr");
        controlPanel.pridejComboBox("Filtr", TypKnihy.values(), filterHandler);
        controlPanel.pridejButton("Najdi", najdiHandler);
        controlPanel.pridejButton("Zálohuj", zalohujHandler);
        controlPanel.pridejButton("Obnov", obnovHandler);
        controlPanel.pridejButton("Zruš", zrusHandler);
        return controlPanel;
    }

    private void obnovListView() {
        listView.getItems().clear();
        spravce.dejPrvky()
                .filter(t -> filtr.test(t.getTyp()))
                .forEach(listView.getItems()::add);
    }

    private void obnovListKniha(Kniha kniha) {
        obnovListView();
        listView.getSelectionModel().select(kniha);
    }

    private int zobrazDialogId() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(null);
        dialog.setHeaderText(null);
        dialog.setContentText("Zadejte id knihy:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int id = Integer.parseInt(result.get());
                return id;
            } catch (NumberFormatException ex) {
            }
        }
        return -1;
    }
}
