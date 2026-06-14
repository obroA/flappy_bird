package pkg2dgamesframework;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Predstavlja en animacijski okvir (frame) izrezan iz sprite slike.
 * Hrani koordinate in dimenzije izreza ter podpira risanje z vrtenjem.
 * 
 */
public class OkvirNaSliki {

    /** Ali se izriše pravokotnik za odpravljanje napak */
    private boolean prikaziMejniPravokotnik = false;

    /** Meje okvirja: [xNaSliki, yNaSliki, sirina, visina] */
    private int[] mejePravokotnika = new int[4];

    /**
     * Ustvari okvir na podlagi položaja in dimenzij na izvorni sliki.
     * @param xNaSliki začetna koordinata X na izvorni sliki
     * @param yNaSliki začetna koordinata Y na izvorni sliki
     * @param sirina   širina izreza
     * @param visina   višina izreza
     */
    public OkvirNaSliki(int xNaSliki, int yNaSliki, int sirina, int visina){
        mejePravokotnika[0] = xNaSliki;
        mejePravokotnika[1] = yNaSliki;
        mejePravokotnika[2] = sirina;
        mejePravokotnika[3] = visina;
    }
    
    /**
     * Vklopi ali izklopi prikaz mejnega pravokotnika (za odpravljanje napak).
     * @param vklopi true = prikaži, false = skrij
     */
    public void prikaziMejeOdpravljanjeNapak(boolean vklopi){
        prikaziMejniPravokotnik = vklopi;
    }
    
    /**
     * Vrne meje tega okvirja kot polje [x, y, sirina, visina].
     * @return polje z mejami
     */
    public int[] vrniMeje(){
        return mejePravokotnika;
    }
    
    /**
     * Nariše ta okvir na dano grafično površino z možnostjo vrtenja.
     * @param x        položaj X risanja
     * @param y        položaj Y risanja
     * @param slika    izvorna sprite slika
     * @param g2       grafični kontekst
     * @param sidro    sidro (zaenkrat neuporabljeno)
     * @param rotacija kot vrtenja v radianih
     */
    public void narisi(int x, int y, BufferedImage slika, Graphics2D g2, int sidro, float rotacija){
        BufferedImage izrezSlika = slika.getSubimage(
            mejePravokotnika[0], mejePravokotnika[1],
            mejePravokotnika[2], mejePravokotnika[3]
        );

        AffineTransform transform = new AffineTransform();
        transform.rotate(rotacija, izrezSlika.getWidth() / 2, izrezSlika.getHeight() / 2);

        AffineTransformOp operacija = new AffineTransformOp(transform,
            AffineTransformOp.TYPE_BILINEAR);
        izrezSlika = operacija.filter(izrezSlika, null);

        g2.drawImage(izrezSlika, x, y, null);

        if(prikaziMejniPravokotnik) narisiMeje(g2);
    }
    
    /**
     * Interna metoda za risanje mejnega pravokotnika (zaenkrat prazna).
     * @param g grafični kontekst
     */
    private void narisiMeje(Graphics2D g){
        // TODO: implementacija prikaza mej za odpravljanje napak
    }
}
