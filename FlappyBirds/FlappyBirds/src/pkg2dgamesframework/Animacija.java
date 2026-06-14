package pkg2dgamesframework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Razred za upravljanje sprite animacije.
 * Hrani zaporedje okvirjev (OkvirNaSliki) in jih izmenično prikazuje
 * glede na pretečeni čas.
 * 
 */
public class Animacija {
    
    /** Čas začetka zadnje menjave okvirja */
    private long casZacetka = 0;
    
    /** Trajanje prikaza vsakega okvirja v milisekundah */
    private long trajanje = 20;
    
    /** Polje animacijskih okvirjev */
    private OkvirNaSliki[] okvirji;
    
    /** Skupno število okvirjev */
    private int steviloOkvirjev = 0;
    
    /** Indeks trenutno prikazanega okvirja */
    private int trenutniOkvir = 0;
    
    /**
     * Ustvari animacijo z določenim trajanjem posameznega okvirja.
     * @param trajanje čas prikaza enega okvirja v milisekundah
     */
    public Animacija(long trajanje){
        this.trajanje = trajanje;
    }
    
    /**
     * Posodobi animacijo – preveri, ali je čas za nasslednji okvir.
     * @param deltaCas trenutni čas sistema v milisekundah
     */
    public void posodobi(long deltaCas){
        if(steviloOkvirjev > 0){
            if(deltaCas - casZacetka > trajanje){
                trenutniOkvir++;
                if(trenutniOkvir >= steviloOkvirjev) 
                    trenutniOkvir = 0;
                casZacetka = deltaCas;
            }
        }
    }
    
    /**
     * Doda nov okvir na konec animacije.
     * @param okvir animacijski okvir tipa OkvirNaSliki
     */
    public void dodajOkvir(OkvirNaSliki okvir){
        OkvirNaSliki[] zacasniOkvirji = okvirji;
        okvirji = new OkvirNaSliki[steviloOkvirjev + 1];
        for(int i = 0; i < steviloOkvirjev; i++) okvirji[i] = zacasniOkvirji[i];
        okvirji[steviloOkvirjev] = okvir;
        steviloOkvirjev++;
    }
    
    /**
     * Nariše trenutni okvir animacije na zaslon.
     * @param x        položaj X risanja
     * @param y        položaj Y risanja
     * @param slika    izvorna sprite slika
     * @param g2       grafični kontekst
     * @param sidro    sidro (zaenkrat neuporabljeno)
     * @param rotacija kot vrtenja v radianih
     */
    public void narisiAnimacijo(int x, int y, BufferedImage slika, Graphics2D g2, int sidro, float rotacija){
        okvirji[trenutniOkvir].narisi(x, y, slika, g2, sidro, rotacija);
    }
}
