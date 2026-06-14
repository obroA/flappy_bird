package flappybirds;

import java.awt.Rectangle;
import pkg2dgamesframework.Objekti;

/**
 * Razred, ki predstavlja en dimnik (oviro) v igri.
 * Dimnik se premika od desne proti levi za 2 piksla na posodobitev.
 * Hrani pravokotnik za zaznavanje trkov in informacijo, ali je ptica že prešla mimo.
 * 
 */
public class Dimnik extends Objekti {
    
    /** Pravokotnik za zaznavanje trkov */
    private Rectangle pravokotnik;
    
    /** Ali je ptica že prišla za ta dimnik (za štetje točk) */
    private boolean jeZaPtico = false;
    
    /**
     * Ustvari dimnik na določeni poziciji z dimenzijami.
     * @param x začetni položaj X
     * @param y začetni položaj Y
     * @param s širina dimnika
     * @param v višina dimnika
     */
    public Dimnik(int x, int y, int s, int v){
        super(x, y, s, v);
        pravokotnik = new Rectangle(x, y, s, v);
    }
    
    /**
     * Posodobi položaj dimnika – premakne ga 2 piksla v levo.
     * Pravokotnik trka se posodobi na novo pozicijo.
     */
    public void posodobi(){
        nastaviPolozajX(vrniPolozajX() - 2);
        pravokotnik.setLocation((int) this.vrniPolozajX(), (int) this.vrniPolozajY());
    }
    
    /**
     * Vrne pravokotnik za zaznavanje trkov.
     * @return pravokotnik dimnika
     */
    public Rectangle vrniPravokotnik(){
        return pravokotnik;
    }
    
    /**
     * Nastavi, ali je ptica prešla mimo tega dimnika.
     * @param b true = ptica je prešla
     */
    public void nastaviJeZaPtico(boolean b){
        jeZaPtico = b;
    }
    
    /**
     * Vrne, ali je ptica že prešla mimo tega dimnika.
     * @return true, če je ptica prešla
     */
    public boolean jeZaPtico(){
        return jeZaPtico;
    }
}
