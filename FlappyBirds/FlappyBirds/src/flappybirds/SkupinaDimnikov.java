package flappybirds;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import pkg2dgamesframework.VrstaSeznam;

/**
 * Upravlja skupino dimnikov (ovir) v igri.
 * Vzdržuje vrsto dimnikov, jih reciklira, ko zapustijo zaslon,
 * ter skrbi za naključno vertikalno postavitev.
 * Dimniki so v parih: spodnji in zgornji (obrnjeni).
 * 
 */
public class SkupinaDimnikov {
    
    /** Vrsta (queue) vseh aktivnih dimnikov */
    private VrstaSeznam<Dimnik> dimniki;
    
    /** Sliki za spodnji in zgornji dimnik */
    private BufferedImage slikaDimnika, slikaDimnika2;
    
    /** Skupno število dimnikov (parno – v parih spodnji/zgornji) */
    public static int STEVILO = 6;
    
    /** Začetna Y pozicija zgornjega dimnika */
    private int zgornjaDimnikY = -350;
    
    /** Začetna Y pozicija spodnjega dimnika */
    private int spodnjiDimnikY = 200;
    
    /**
     * Vrne dimnik na določenem indeksu v vrsti.
     * @param i indeks dimnika
     * @return dimnik na indeksu i
     */
    public Dimnik vrniDimnik(int i){
        return dimniki.vrni(i);
    }
    
    /**
     * Vrne naključni vertikalni odmik za par dimnikov.
     * Vrednost je med 0 in 315 (v korakih po 35).
     * @return naključni odmik Y
     */
    public int nakljucniOdmikY(){
        Random nakljucno = new Random();
        int a = nakljucno.nextInt(10);
        return a * 35;
    }
    
    /**
     * Konstruktor – naloži sliki dimnikov in ustvari začetne dimnikе.
     */
    public SkupinaDimnikov(){
        try {
            slikaDimnika  = ImageIO.read(new File("Assets/chimney.png"));
            slikaDimnika2 = ImageIO.read(new File("Assets/chimney2.png"));
        } catch(IOException ex){}
        
        dimniki = new VrstaSeznam<Dimnik>();
        ustvarjDimnike();
    }
    
    /**
     * Interna metoda – ustvari začetne pare dimnikov in jih vstavi v vrsto.
     */
    private void ustvarjDimnike(){
        Dimnik d;
        for(int i = 0; i < STEVILO / 2; i++){
            int odmikY = nakljucniOdmikY();
            
            // Spodnji dimnik
            d = new Dimnik(830 + i * 300, spodnjiDimnikY + odmikY, 74, 400);
            dimniki.vstavi(d);
            
            // Zgornji dimnik (obrnjen)
            d = new Dimnik(830 + i * 300, zgornjaDimnikY + odmikY, 74, 400);
            dimniki.vstavi(d);
        }
    }
    
    /**
     * Ponastavi vse dimnike na začetne položaje (ob novem zagonu igre).
     */
    public void ponastaviDimnike(){
        dimniki = new VrstaSeznam<Dimnik>();
        ustvarjDimnike();
    }
    
    /**
     * Posodobi položaje vseh dimnikov.
     * Ko dimnik zapusti zaslon na levi, ga reciklira in postavi desno.
     */
    public void posodobi(){
        // Premakni vse dimnike v levo
        for(int i = 0; i < STEVILO; i++){
            dimniki.vrni(i).posodobi();
        }
        
        // Recikliraj dimnik, ki je zapustil zaslon
        if(dimniki.vrni(0).vrniPolozajX() < -74){
            int odmikY = nakljucniOdmikY();
            
            Dimnik d;
            // Recikliraj spodnji dimnik
            d = dimniki.vzami();
            d.nastaviPolozajX(dimniki.vrni(4).vrniPolozajX() + 300);
            d.nastaviPolozajY(spodnjiDimnikY + odmikY);
            d.nastaviJeZaPtico(false);
            dimniki.vstavi(d);
            
            // Recikliraj zgornji dimnik
            d = dimniki.vzami();
            d.nastaviPolozajX(dimniki.vrni(4).vrniPolozajX());
            d.nastaviPolozajY(zgornjaDimnikY + odmikY);
            d.nastaviJeZaPtico(false);
            dimniki.vstavi(d);
        }
    }
    
    /**
     * Nariše vse dimnike na zaslon.
     * Sodi indeksi = spodnji dimnik (slika 1), lihi = zgornji (slika 2).
     * @param g2 grafični kontekst
     */
    public void narisi(Graphics2D g2){
        for(int i = 0; i < 6; i++){
            if(i % 2 == 0)
                g2.drawImage(slikaDimnika,  (int) dimniki.vrni(i).vrniPolozajX(), (int) dimniki.vrni(i).vrniPolozajY(), null);
            else
                g2.drawImage(slikaDimnika2, (int) dimniki.vrni(i).vrniPolozajX(), (int) dimniki.vrni(i).vrniPolozajY(), null);
        }
    }
}
