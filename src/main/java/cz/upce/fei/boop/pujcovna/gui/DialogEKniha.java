package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.util.Format;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public final class DialogEKniha extends DialogKniha {

    private TextField textFieldPocetStran;
    private ComboBox<Format> comboBoxFormat;

    public DialogEKniha(EKniha kniha) {
        super(kniha);
    }

    @Override
    protected void ulozDalsiPole() {
        String pocetStran = textFieldPocetStran.getText();
        Format format = comboBoxFormat.getValue();
        if(!platnyVstup(pocetStran, format))
            throw new NullPointerException();
        
        ((EKniha)kniha).setPocetStran(Integer.parseInt(pocetStran));
        ((EKniha)kniha).setFormat(format);
    }

    @Override
    protected int pridejAVyplnDalsiRadky(GridPane grid, int radek) {
        textFieldPocetStran = DialogKniha.pridejTextField(grid, "Počet stran",null, radek++);
        comboBoxFormat = DialogKniha.pridejComboBox(grid, "Formát", radek, Format.values());
        textFieldPocetStran.setText(String.valueOf(((EKniha)kniha).getPocetStran()));
        comboBoxFormat.getSelectionModel().select(((EKniha)kniha).getFormat());
        return radek;
    }
    
    private boolean platnyVstup(String pocetStran, Format format){
        return pocetStran != null
                && pocetStran.length() > 0
                && format != null;
    }
}
