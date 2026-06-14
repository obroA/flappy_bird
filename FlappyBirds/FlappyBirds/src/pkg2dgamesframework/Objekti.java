package pkg2dgamesframework;

/**
 * Osnovni razred za vse igrine objekte v 2D prostoru.
 * Vsebuje pozicijo (x, y) in dimenzije (širina, višina) objekta.
 * 
 */
public class Objekti {
    
    /** Položaj objekta na osi X */
    private float polozajX, polozajY;
    
    /** Širina in višina objekta */
    private float sirina, visina;
    
    /** Privzeti konstruktor – nastavi vse vrednosti na 0 */
    public Objekti(){
         polozajX = polozajY = sirina = visina = 0;
    }
    
    /**
     * Konstruktor z določeno pozicijo in dimenzijami.
     * @param x začetni položaj na osi X
     * @param y začetni položaj na osi Y
     * @param s širina objekta
     * @param v višina objekta
     */
    public Objekti(float x, float y, float s, float v){
        this.polozajX = x;
        this.polozajY = y;
        this.sirina = s;
        this.visina = v;
    }
    
    /**
     * Preveri, ali točka (x, y) leži znotraj tega objekta (trk s točko).
     * @param x koordinata X točke
     * @param y koordinata Y točke
     * @return true, če je prišlo do trka
     */
    public boolean jeTrk(float x, float y){
        if(x > polozajX && x < polozajX + sirina && y > polozajY && y < polozajY + visina) 
            return true;
        return false;
    }
    
    /**
     * Preveri, ali pravokotnik (x, y, s, v) se prekriva s tem objektom (trk s pravokotnikom).
     * @param x koordinata X drugega objekta
     * @param y koordinata Y drugega objekta
     * @param s širina drugega objekta
     * @param v višina drugega objekta
     * @return true, če je prišlo do trka
     */
    public boolean jeTrk(float x, float y, float s, float v){
        if(x < polozajX + this.sirina && x + s > polozajX && y < polozajY + this.visina && v + y > polozajY)
            return true;
        return false;
    }
    
    /**
     * Nastavi položaj objekta.
     * @param x nova koordinata X
     * @param y nova koordinata Y
     */
    public void nastaviPolozaj(float x, float y){
        polozajX = x;
        polozajY = y;
    }
    
    /**
     * Nastavi koordinato X.
     * @param x nova vrednost X
     */
    public void nastaviPolozajX(float x){
        polozajX = x;
    }
    
    /**
     * Nastavi koordinato Y.
     * @param y nova vrednost Y
     */
    public void nastaviPolozajY(float y){
        polozajY = y;
    }
    
    /** @return trenutni položaj X */
    public float vrniPolozajX(){
        return polozajX;
    }
    
    /** @return trenutni položaj Y */
    public float vrniPolozajY(){
        return polozajY;
    }
    
    /** @return širina objekta */
    public float vrniSirino(){
        return sirina;
    }
    
    /** @return višina objekta */
    public float vrniVisino(){
        return visina;
    }
    
    /**
     * Poveča položaj X za podano vrednost.
     * @param m vrednost, za katero se poveča X
     */
    public void povecajPolozajX(float m){
        polozajX += m;
    }
    
    /**
     * Poveča položaj Y za podano vrednost.
     * @param m vrednost, za katero se poveča Y
     */
    public void povecajPolozajY(float m){
        polozajY += m;
    }
}
