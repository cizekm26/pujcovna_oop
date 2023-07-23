package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.util.TypKnihy;
import java.util.function.Predicate;

class Filtr implements Predicate<TypKnihy>{
    private final TypKnihy typFiltru;

    public Filtr(TypKnihy typFiltru) {
        this.typFiltru = typFiltru;
    }

    @Override
    public boolean test(TypKnihy t) {
        return typFiltru == TypKnihy.NON_FILTER || t == typFiltru;
    }
    
}
