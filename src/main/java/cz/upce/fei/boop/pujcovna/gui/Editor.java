package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.Audiokniha;
import cz.upce.fei.boop.pujcovna.data.EKniha;
import cz.upce.fei.boop.pujcovna.data.Kniha;
import cz.upce.fei.boop.pujcovna.data.PapirovaKniha;
import java.util.function.Function;

public class Editor implements Function<Kniha,Kniha>{
    
    private DialogKniha dialog = null;

    @Override
    public Kniha apply(Kniha kniha) {
        switch(kniha.getTyp()){
            case AUDIO -> dialog = new DialogAudiokniha((Audiokniha) kniha);
            case ELEKTRONICKA -> dialog = new DialogEKniha((EKniha) kniha);
            case PAPIROVA -> dialog = new DialogPapirovaKniha((PapirovaKniha) kniha);
        }
        if(dialog != null){
            dialog.showAndWait();
            if(!dialog.isUlozClicked())
                kniha = null;
        }
        return kniha;
    }
    
}
