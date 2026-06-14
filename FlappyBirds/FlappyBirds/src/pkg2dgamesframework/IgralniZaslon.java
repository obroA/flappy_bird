package pkg2dgamesframework;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Abstraktni osnovni razred za igralni zaslon.
 * Skrbi za inicializacijo okna, tipkovnice in igralne niti.
 * Podrazredi morajo implementirati POSODOBI_IGRO, NARISI_IGRO in TIPKA_AKCIJA.
 * 
 */
public abstract class IgralniZaslon extends JFrame implements KeyListener {

    /** Konstanti za stanje tipke */
    public static int TIPKA_PRITISNJENA = 0;
    public static int TIPKA_SPROSCENA   = 1;

    /** Dimenzije okna po meri */
    public int SIRINA_PO_MERI  = 500;
    public int VISINA_PO_MERI  = 500;
    
    /** Referenca na igralno nit */
    private IgralnaNit igralnaNit;
    
    /** Globalne (master) dimenzije zaslona */
    public static int GLAVNA_SIRINA = 500, GLAVNA_VISINA = 500;
    
    /** Privzeti konstruktor z dimenzijami 500×500 */
    public IgralniZaslon(){
        inicializirajNit();
        inicializirajZaslon();
        Image logo = new ImageIcon(this.getClass().getResource("/gamelogo.png")).getImage();
        setIconImage(logo);
        setTitle("Flappy Bird");
    }
    
    /**
     * Konstruktor z določenimi dimenzijami okna.
     * @param sirina širina okna v pikslih
     * @param visina višina okna v pikslih
     */
    public IgralniZaslon(int sirina, int visina){
        setLocationByPlatform(true);
        this.SIRINA_PO_MERI = sirina;
        this.VISINA_PO_MERI = visina;
        GLAVNA_SIRINA = SIRINA_PO_MERI;
        GLAVNA_VISINA = VISINA_PO_MERI;
        inicializirajNit();
        inicializirajZaslon();
        Image logo = new ImageIcon(this.getClass().getResource("/gamelogo.png")).getImage();
        this.setIconImage(logo);
        setTitle("Flappy Bird");
    }
    
    /**
     * Inicializira in nastavi okno (JFrame).
     */
    private void inicializirajZaslon(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        setSize(SIRINA_PO_MERI, VISINA_PO_MERI);
        setVisible(true);
    }
    
    /**
     * Zažene igralno nit (začne igro).
     */
    public void zacniIgro(){
        igralnaNit.zazeniNit();
    }
    
    /**
     * Ustvari in doda igralno nit na zaslon.
     */
    private void inicializirajNit(){
        igralnaNit = new IgralnaNit(this);
        add(igralnaNit);
    }
    
    /** Neuporabljeno – zahteva vmesnik KeyListener */
    @Override
    public void keyTyped(KeyEvent e){}

    /** Posreduje pritisk tipke v KEY_ACTION */
    @Override
    public void keyPressed(KeyEvent e){
        TIPKA_AKCIJA(e, IgralniZaslon.TIPKA_PRITISNJENA);
    }

    /** Posreduje sprostitev tipke v KEY_ACTION */
    @Override
    public void keyReleased(KeyEvent e){
        TIPKA_AKCIJA(e, IgralniZaslon.TIPKA_SPROSCENA);
    }
    
    /**
     * Posodobi stanje igre. Kliče se vsak obhod igrine zanke.
     * @param deltaCas trenutni čas v milisekundah
     */
    public abstract void POSODOBI_IGRO(long deltaCas);
    
    /**
     * Nariše igro na zaslon. Kliče se vsak obhod igrine zanke.
     * @param g2 grafični kontekst za risanje
     */
    public abstract void NARISI_IGRO(Graphics2D g2);
    
    /**
     * Obravnava dogodek tipkovnice.
     * @param e     dogodek tipkovnice
     * @param stanje TIPKA_PRITISNJENA ali TIPKA_SPROSCENA
     */
    public abstract void TIPKA_AKCIJA(KeyEvent e, int stanje);
}
