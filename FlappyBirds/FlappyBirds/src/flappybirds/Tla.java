package flappybirds;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Razred, ki predstavlja premikajoča se tla v igri.
 * Dve kopiji slike tal se pomikata v levo in se ciklično izmenjujeta,
 * kar ustvari iluzijo neskončnega gibanja.
 * 
 */
public class Tla {
    
    /** Slika tal */
    private BufferedImage slikaTal;
    
    /** Položaji obeh kopij slike tal */
    private int x1, y1, x2, y2;
    
    /**
     * Konstruktor – naloži sliko tal in nastavi začetne položaje.
     */
    public Tla(){
        try {
            slikaTal = ImageIO.read(new File("Assets/ground.png"));
        } catch(IOException ex){}
        
        x1 = 0;
        y1 = 500;
        x2 = x1 + 830;
        y2 = 500;
    }
    
    /**
     * Posodobi položaj tal – premakne obe kopiji 2 piksla v levo.
     * Ko kopija zapusti zaslon na levi, jo postavi desno od druge.
     */
    public void posodobi(){
        x1 -= 2;
        x2 -= 2;
        
        // Recikliraj, ko kopija zapusti zaslon
        if(x2 < 0) x1 = x2 + 830;
        if(x1 < 0) x2 = x1 + 830;
    }
    
    /**
     * Nariše obe kopiji tal na zaslon.
     * @param g2 grafični kontekst
     */
    public void narisi(Graphics2D g2){
        g2.drawImage(slikaTal, x1, y1, null);
        g2.drawImage(slikaTal, x2, y2, null);
    }
    
    /**
     * Vrne Y koordinato vrha tal (za zaznavanje trka ptiče s tlemi).
     * @return Y koordinata tal
     */
    public int vrniYTal(){
        return y1;
    }
}
