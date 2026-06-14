package pkg2dgamesframework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Glavna igralna nit (game loop), ki skrbi za posodabljanje in risanje igre.
 * Teče v ločeni niti in vzdržuje ciljno število sličic na sekundo (FPS).
 * Uporablja dvojno medpomnjenje (double buffering) za gladko risanje.
 * 
 */
public class IgralnaNit extends JPanel implements Runnable {
    
    /** Referenca na igralni zaslon (kontekst) */
    private IgralniZaslon kontekst;
    
    /** Java nit, ki poganja igro */
    private Thread nit;
    
    /** Ciljna hitrost osveževanja v sličicah na sekundo */
    public static int FPS = 70;
    
    /** Medpomnilna slika za dvojno medpomnjenje */
    private BufferedImage medpomnilnaSlika;
    
    /** Dimenzije platna */
    private int sirinaPlatna, visinaPlatna;
    
    /** Faktorja skaliranja (za prilagoditev velikosti okna) */
    public static float faktorX = 1, faktorY = 1;
    
    /**
     * Ustvari igralno nit vezano na dani zaslon.
     * @param kontekst igralni zaslon, ki vsebuje logiko igre
     */
    public IgralnaNit(IgralniZaslon kontekst){
        this.kontekst = kontekst;
        sirinaPlatna = kontekst.SIRINA_PO_MERI;
        visinaPlatna = kontekst.VISINA_PO_MERI;
        this.nit = new Thread(this);
    }
    
    /**
     * Zažene igralno nit.
     */
    public void zazeniNit(){
        nit.start();
    }
    
    /**
     * Nariše medpomnilno sliko na zaslon (double buffering).
     * @param g grafični kontekst
     */
    @Override
    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, kontekst.SIRINA_PO_MERI, kontekst.VISINA_PO_MERI);
        Graphics2D g2 = (Graphics2D) g;
        if(medpomnilnaSlika != null){
            g2.scale(faktorX, faktorY);
            g2.drawImage(medpomnilnaSlika, 0, 0, this);
        }
    }

    /**
     * Posodobi velikost platna glede na trenutno velikost okna.
     * Izračuna faktorja skaliranja X in Y.
     */
    private void posodobiVelikost(){
        if(this.getWidth() <= 0) return;
        kontekst.SIRINA_PO_MERI = this.getWidth();
        kontekst.VISINA_PO_MERI = this.getHeight();
        faktorX = (float) kontekst.SIRINA_PO_MERI / (float) sirinaPlatna;
        faktorY = (float) kontekst.VISINA_PO_MERI / (float) visinaPlatna;
    }

    /**
     * Glavna zanka igre – teče v ločeni niti.
     * Vsak obhod: posodobi stanje, nariše novo sličico, počaka do naslednje sličice.
     */
    @Override
    public void run(){
        long casnaDelta = 1000 / FPS;
        long casniZnesek = casnaDelta / 2;
        
        long casZacetka = System.currentTimeMillis();
        long casKonca;
        long casSpanja;
        
        while(true){
            posodobiVelikost();
            
            // Posodobi logiko igre
            kontekst.POSODOBI_IGRO(System.currentTimeMillis());
            
            try {
                // Nariši igro v medpomnilnik
                medpomnilnaSlika = new BufferedImage(sirinaPlatna, visinaPlatna, BufferedImage.TYPE_INT_ARGB);
                if(medpomnilnaSlika == null) return;
                Graphics2D g2 = (Graphics2D) medpomnilnaSlika.getGraphics();
                if(g2 != null){
                    kontekst.NARISI_IGRO(g2);
                }
            } catch(Exception napaka){
                napaka.printStackTrace();
            }
            
            // Osveži zaslon
            repaint();
            
            casKonca = System.currentTimeMillis();
            casSpanja = casnaDelta - (casKonca - casZacetka);
            if(casSpanja < 0) casSpanja = casniZnesek;
            
            try {
                Thread.sleep(casSpanja);
            } catch(InterruptedException ex){}
            
            casZacetka = System.currentTimeMillis();
        }
    }
}
