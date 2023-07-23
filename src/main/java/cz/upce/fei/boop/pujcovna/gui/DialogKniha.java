package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.Kniha;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class DialogKniha extends Stage {

    private int radek = 0;
    private TextField textFieldId;
    private TextField textFieldNazev;
    private TextField textFieldAutor;
    protected final Kniha kniha;
    private boolean ulozClicked = false;

    public DialogKniha(Kniha kniha) {
        this.kniha = kniha;
        setWidth(300);
        setHeight(300);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.WINDOW_MODAL);
        initOwner(MainFX.primaryStage);
        GridPane pane = getPane();
        setScene(new Scene(pane));
    }

    private GridPane getPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        textFieldId = pridejTextField(gridPane, "ID", null, radek++);
        textFieldNazev = pridejTextField(gridPane, "Název", null, radek++);
        textFieldAutor = pridejTextField(gridPane, "Autor", null, radek++);
        textFieldId.setDisable(true);
        textFieldId.setText(String.valueOf(kniha.getId()));
        textFieldNazev.setText(kniha.getNazev());
        textFieldAutor.setText(kniha.getAutor());
        radek = pridejAVyplnDalsiRadky(gridPane, radek);
        Button buttonUloz = new Button("Ulož");
        buttonUloz.setOnAction(e -> {
            uloz();
        });
        gridPane.add(buttonUloz, 0, ++radek);
        return gridPane;
    }

    public boolean isUlozClicked() {
        return ulozClicked;
    }

    protected abstract void ulozDalsiPole();

    protected abstract int pridejAVyplnDalsiRadky(GridPane grid, int radek);

    private void uloz() {
        String nazev = textFieldNazev.getText();
        String autor = textFieldAutor.getText();
        try {
            if (!platnyVstup(nazev, autor)) {
                throw new Exception();
            }
            kniha.setAutor(autor);
            kniha.setNazev(nazev);
            ulozDalsiPole();
            ulozClicked = true;
            close();
        } catch (Exception ex) {
            ulozClicked = false;
            zobrazChybu("Byly zadány neplatné údaje");
        }
    }

    protected static TextField pridejTextField(GridPane grid, String nazev, String jednotka, int radek) {
        grid.add(new Label(nazev), 0, radek);
        TextField textField = new TextField();
        grid.add(textField, 1, radek);
        if (jednotka != null) {
            grid.add(new Label(jednotka), 2, radek);
        }
        return textField;
    }

    protected static <T> ComboBox<T> pridejComboBox(GridPane grid,
            String nazev, int radek, Enum[] enumList) {
        grid.add(new Label(nazev), 0, radek);
        ComboBox<T> comboBox = new ComboBox(
                FXCollections.observableList(Arrays.asList(enumList)));
        grid.add(comboBox, 1, radek);
        return comboBox;
    }

    protected static void zobrazChybu(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Chyba");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private boolean platnyVstup(String nazev, String autor) {
        return nazev != null
                && nazev.length() > 0
                && autor != null
                && autor.length() > 0;
    }
}
