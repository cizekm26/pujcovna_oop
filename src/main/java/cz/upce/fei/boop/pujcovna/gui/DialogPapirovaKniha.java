package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DialogPapirovaKniha extends DialogKniha {

    private TextField textFieldPocetStran;
    private TextField textFieldHmotnost;

    public DialogPapirovaKniha(PapirovaKniha kniha) {
        super(kniha);
    }
    @Override
    protected int pridejAVyplnDalsiRadky(GridPane grid, int radek) {
        textFieldPocetStran = DialogKniha.pridejTextField(grid, "Počet stran",null, radek++);
        textFieldHmotnost = DialogKniha.pridejTextField(grid, "Hmotnost","g", radek++);
        textFieldPocetStran.setText(String.valueOf(((PapirovaKniha)kniha).getPocetStran()));
        textFieldHmotnost.setText(String.valueOf(((PapirovaKniha)kniha).getHmotnost()));
        return radek;
    }

    @Override
    protected void ulozDalsiPole() {
        String pocetStran = textFieldPocetStran.getText();
        String hmotnost = textFieldHmotnost.getText();
        if(!platnyVstup(pocetStran, hmotnost))
            throw new NullPointerException();
        
        ((PapirovaKniha)kniha).setPocetStran(Integer.parseInt(pocetStran));
        ((PapirovaKniha)kniha).setHmotnost(Double.parseDouble(hmotnost));
    }
    
    private boolean platnyVstup(String pocetStran, String hmotnost){
        return pocetStran != null
                && pocetStran.length() > 0
                && hmotnost != null
                && hmotnost.length() > 0;
    }
}
