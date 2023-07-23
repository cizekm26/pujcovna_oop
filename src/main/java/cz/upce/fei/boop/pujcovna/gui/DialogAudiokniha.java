package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public final class DialogAudiokniha extends DialogKniha {

    private TextField textFieldDelka;
    private TextField textFieldVelikost;

    public DialogAudiokniha(Audiokniha kniha) {
        super(kniha);
    }

    @Override
    protected int pridejAVyplnDalsiRadky(GridPane grid, int radek) {
        textFieldDelka = DialogKniha.pridejTextField(grid, "Délka", null, radek++);
        textFieldVelikost = DialogKniha.pridejTextField(grid, "Velikost", "MB", radek++);
        textFieldDelka.setText(String.valueOf(((Audiokniha) kniha).getDelka()));
        textFieldVelikost.setText(String.valueOf(((Audiokniha) kniha).getVelikost()));
        return radek;
    }

    @Override
    protected void ulozDalsiPole() {
        String delka = textFieldDelka.getText();
        String velikost = textFieldVelikost.getText();
        if (!platnyVstup(delka, velikost)) {
            throw new NullPointerException();
        }
        ((Audiokniha) kniha).setDelka(Integer.parseInt(delka));
        ((Audiokniha) kniha).setVelikost(Integer.parseInt(velikost));
    }
    
    private boolean platnyVstup(String delka, String velikost){
        return delka != null
                && delka.length() > 0
                && velikost != null 
                && velikost.length() > 0;
    }
}
