package flappybirds;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pkg2dgamesframework.OkvirNaSliki;
import pkg2dgamesframework.Animacija;
import pkg2dgamesframework.IgralniZaslon;

/**
 * Glavni razred igre Flappy Bird.
 * Deduje od IgralniZaslon in implementira logiko igre:
 * - začetni zaslon, igranje in zaslon ob koncu igre.
 * Upravlja ptico, tla, skupino dimnikov ter animacijo ptice.
 * 
 */
public class FlappyBirds extends IgralniZaslon {

    /** Sprite slika ptiče */
    private BufferedImage spritePtica;
    
    /** Slike za ozadje, game over in naslov igre */
    private Image ozadje;
    private Image konecIgre;
    private Image naslovIgre;
    
    /** Animacija ptiče */
    private Animacija animacijaPtice;
    
    /** Gravitacijska konstanta (pospešek navzdol na vsak korak) */
    public static float GRAVITACIJA = 0.1f;
    
    /** Objekt ptiče (glavni lik) */
    private Ptica ptica;
    
    /** Objekt tal */
    private Tla tla;
    
    /** Skupina dimnikov (ovir) */
    private SkupinaDimnikov skupinaDimnikov;
    
    /** Trenutni rezultat (točke) */
    private int tocke = 0;
    
    /** Konstante za stanja zaslona */
    private final int ZACETNI_ZASLON    = 0;
    private final int ZASLON_IGRANJA    = 1;
    private final int ZASLON_KONCA_IGRE = 2;
    
    /** Trenutno aktiven zaslon */
    private int trenutniZaslon = ZACETNI_ZASLON;
    
    /**
     * Konstruktor – ustvari okno 800×600, naloži vire in zažene igro.
     */
    public FlappyBirds(){
        super(800, 600);
        
        // Naloži sprite sliko ptiče
        try {
            spritePtica = ImageIO.read(new File("Assets/bird_sprite.png"));
        } catch(IOException ex){}
        
        // Naloži ozadje
        try {
            ozadje = ImageIO.read(new File("Assets/background.png"));
        } catch(IOException ex){}
        
        // Naloži sliko konca igre
        try {
            konecIgre = ImageIO.read(new File("Assets/gameover.png"));
        } catch(IOException ex){}
        
        // Naloži naslov igre
        try {
            naslovIgre = ImageIO.read(new File("Assets/gametitle.png"));
        } catch(IOException ex){}
        
        // Nastavi animacijo ptiče s 4 okvirji
        animacijaPtice = new Animacija(70);
        OkvirNaSliki okvir;
        okvir = new OkvirNaSliki(0,   0, 60, 60); animacijaPtice.dodajOkvir(okvir);
        okvir = new OkvirNaSliki(60,  0, 60, 60); animacijaPtice.dodajOkvir(okvir);
        okvir = new OkvirNaSliki(120, 0, 60, 60); animacijaPtice.dodajOkvir(okvir);
        okvir = new OkvirNaSliki(60,  0, 60, 60); animacijaPtice.dodajOkvir(okvir);
        
        // Ustvari objekte igre
        ptica         = new Ptica(350, 250, 50, 50);
        tla           = new Tla();
        skupinaDimnikov = new SkupinaDimnikov();
        
        // Zaženi igro
        zacniIgro();
    }
    
    /**
     * Ponastavi igro na začetno stanje:
     * ptico postavi na začetek, točke na 0, dimnike ponastavi.
     */
    private void ponastaviIgro(){
        ptica.nastaviPolozaj(350, 250);
        ptica.nastaviVertikalnoHitrost(0);
        ptica.nastaviZivo(true);
        tocke = 0;
        skupinaDimnikov.ponastaviDimnike();
    }
    
    /**
     * Posodobi stanje igre vsak obhod zanke.
     * Na začetnem zaslonu ponastavi igro.
     * Med igranjem posodobi ptico, tla in dimnike ter preverja trke in točke.
     * @param deltaCas trenutni čas v milisekundah
     */
    @Override
    public void POSODOBI_IGRO(long deltaCas){
        
        if(trenutniZaslon == ZACETNI_ZASLON){
            ponastaviIgro();
            
        } else if(trenutniZaslon == ZASLON_IGRANJA){
            
            // Posodobi animacijo, če je ptica živa
            if(ptica.jeZiva()) animacijaPtice.posodobi(deltaCas);
            
            // Posodobi fiziko ptiče, tla in dimnike
            ptica.posodobi(deltaCas);
            tla.posodobi();
            skupinaDimnikov.posodobi();
            
            // Preveri trke ptiče z dimniki
            for(int i = 0; i < SkupinaDimnikov.STEVILO; i++){
                if(ptica.vrniPravokotnik().intersects(skupinaDimnikov.vrniDimnik(i).vrniPravokotnik())){
                    ptica.nastaviZivo(false);
                }
            }
            
            // Štej točke – vsak par dimnikov, ki ga ptica preide
            for(int i = 0; i < SkupinaDimnikov.STEVILO; i++){
                if(ptica.vrniPolozajX() > skupinaDimnikov.vrniDimnik(i).vrniPolozajX()
                        && !skupinaDimnikov.vrniDimnik(i).jeZaPtico()
                        && i % 2 == 0){
                    tocke++;
                    skupinaDimnikov.vrniDimnik(i).nastaviJeZaPtico(true);
                }
            }
            
            // Trk s tlemi → konec igre
            if(ptica.vrniPolozajY() + ptica.vrniVisino() > tla.vrniYTal()){
                trenutniZaslon = ZASLON_KONCA_IGRE;
            }
        }
    }

    /**
     * Nariše vse elemente igre na zaslon.
     * Glede na trenutni zaslon nariše ustrezna sporočila (začetek, konec igre).
     * @param g2 grafični kontekst za risanje
     */
    @Override
    public void NARISI_IGRO(Graphics2D g2){
        
        g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        
        // Ozadje
        g2.drawImage(ozadje, 0, 0, GLAVNA_SIRINA, GLAVNA_VISINA, rootPane);
        
        // Dimniki, tla
        skupinaDimnikov.narisi(g2);
        tla.narisi(g2);
        
        // Ptica – naklon navzgor, če leti
        if(ptica.jeVLetenju())
            animacijaPtice.narisiAnimacijo((int) ptica.vrniPolozajX(), (int) ptica.vrniPolozajY(), spritePtica, g2, 0, -1);
        else
            animacijaPtice.narisiAnimacijo((int) ptica.vrniPolozajX(), (int) ptica.vrniPolozajY(), spritePtica, g2, 0, 0);
        
        // Začetni zaslon
        if(trenutniZaslon == ZACETNI_ZASLON){
            g2.setColor(Color.black);
            g2.drawImage(naslovIgre, 250, 300, null);
            g2.drawString("PRITISNI PRESLEDNICO ZA ZACETEK", 125, 560);
        }
        
        // Zaslon konca igre
        if(trenutniZaslon == ZASLON_KONCA_IGRE){
            g2.setColor(Color.red);
            g2.drawImage(konecIgre, 250, 240, null);
            g2.drawString("PRITISNI PRESLEDNICO ZA ZACETEK ZNOVA", 50, 560);
            g2.drawString("Tvoj rezultat: " + tocke, 300, 350);
        }
        
        // Rezultat med igro
        g2.setColor(Color.red);
        g2.drawString("Tocke: " + tocke, 20, 50);
    }

    /**
     * Obravnava pritisk tipkovnice glede na trenutni zaslon.
     * Preslednica: začne igro, naredi ptičin skok ali se vrne na začetek.
     * @param e     dogodek tipkovnice
     * @param stanje TIPKA_PRITISNJENA ali TIPKA_SPROSCENA
     */
    @Override
    public void TIPKA_AKCIJA(KeyEvent e, int stanje){
        if(stanje == TIPKA_PRITISNJENA){
            
            if(trenutniZaslon == ZACETNI_ZASLON){
                // Začni igro
                trenutniZaslon = ZASLON_IGRANJA;
                
            } else if(trenutniZaslon == ZASLON_IGRANJA){
                // Ptičin skok (samo, če je živa)
                if(ptica.jeZiva()) ptica.leti();
                
            } else if(trenutniZaslon == ZASLON_KONCA_IGRE){
                // Vrni se na začetni zaslon
                trenutniZaslon = ZACETNI_ZASLON;
            }
        }
    }
}
