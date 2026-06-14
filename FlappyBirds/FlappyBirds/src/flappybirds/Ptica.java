package flappybirds;

import java.awt.Rectangle;
import pkg2dgamesframework.Objekti;

/**
 * Razred, ki predstavlja ptico (glavnega lika) v igri Flappy Bird.
 * Ptica je podvržena gravitaciji in lahko leti navzgor ob pritisku na tipko.
 * Hrani svoje stanje (živa/mrtva, leti/pada) in pravokotnik za zaznavanje trkov.
 * 
 */
public class Ptica extends Objekti {
    
    /** Trenutna vertikalna hitrost (negativna = navzgor, pozitivna = navzdol) */
    private float vertikalnaHitrost = 0;
    
    /** Ali ptica trenutno leti navzgor */
    private boolean jeVLetenju = false;
    
    /** Pravokotnik za zaznavanje trkov */
    private Rectangle pravokotnik;
    
    /** Ali je ptica še živa */
    private boolean jeZiva = true;
    
    /**
     * Ustvari ptico na določeni poziciji z določenimi dimenzijami.
     * @param x začetni položaj X
     * @param y začetni položaj Y
     * @param s širina ptiče
     * @param v višina ptiče
     */
    public Ptica(int x, int y, int s, int v){
        super(x, y, s, v);
        pravokotnik = new Rectangle(x, y, s, v);
    }
    
    /**
     * Nastavi, ali je ptica živa.
     * @param b true = živa, false = mrtva
     */
    public void nastaviZivo(boolean b){
        jeZiva = b;
    }
    
    /**
     * Vrne, ali je ptica živa.
     * @return true, če je ptica živa
     */
    public boolean jeZiva(){
        return jeZiva;
    }
    
    /**
     * Vrne pravokotnik za zaznavanje trkov s stenami.
     * @return pravokotnik ptiče
     */
    public Rectangle vrniPravokotnik(){
        return pravokotnik;
    }
    
    /**
     * Nastavi vertikalno hitrost ptiče.
     * @param vH nova vertikalna hitrost
     */
    public void nastaviVertikalnoHitrost(float vH){
        this.vertikalnaHitrost = vH;
    }
    
    /**
     * Posodobi stanje ptiče za en časovni korak.
     * Gravitacija poveča vertikalno hitrost, položaj se posodobi,
     * pravokotnik trka se premakne na novo pozicijo.
     * @param deltaCas pretečeni čas (neuporabljen, a ohranjen za kompatibilnost)
     */
    public void posodobi(long deltaCas){
        // Gravitacija poveča hitrost navzdol
        vertikalnaHitrost += FlappyBirds.GRAVITACIJA;
        
        // Premakni ptico navzdol/navzgor
        this.nastaviPolozajY(this.vrniPolozajY() + vertikalnaHitrost);
        this.pravokotnik.setLocation((int) this.vrniPolozajX(), (int) this.vrniPolozajY());
        
        // Določi, ali ptica leti navzgor
        if(vertikalnaHitrost < 0) jeVLetenju = true;
        else jeVLetenju = false;
    }
    
    /**
     * Ptica skoči navzgor – nastavi negativno vertikalno hitrost.
     */
    public void leti(){
        vertikalnaHitrost = -3;
    }
    
    /**
     * Vrne, ali ptica trenutno leti navzgor.
     * @return true, če se ptica giblje navzgor
     */
    public boolean jeVLetenju(){
        return jeVLetenju;
    }
}
