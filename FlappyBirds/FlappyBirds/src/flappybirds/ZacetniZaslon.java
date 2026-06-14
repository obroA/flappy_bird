package flappybirds;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Začetni zaslon igre Flappy Bird.
 * Prikaže domač zaslon z logotipom in gumbom za zagon igre.
 * Ob kliku na gumb se odpre glavni igrani zaslon (FlappyBirds).
 * 
 */
public class ZacetniZaslon extends javax.swing.JFrame {
    
    /**
     * Konstruktor – inicializira komponente in nastavi ikono okna.
     */
    public ZacetniZaslon(){
        inicializirajKomponente();
        Image logo = new ImageIcon(this.getClass().getResource("/gamelogo.png")).getImage();
        this.setIconImage(logo);
    }
    
    /**
     * Inicializira grafične komponente okna.
     * POZOR: Ta metoda je samodejno generirana s strani NetBeans Form Editorja.
     * Ne spreminjaj je ročno.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void inicializirajKomponente() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flappy Bird");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flappybirds/playbutton.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                obGumbuZaZacetek(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 160, 90));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flappybirds/homescreen.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Ob pritisku na gumb "Igraj" se odpre igra in zapre začetni zaslon.
     * @param evt dogodek miške
     */
    private void obGumbuZaZacetek(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        new FlappyBirds();
        dispose();
    }//GEN-LAST:event_jLabel2MousePressed

    /**
     * Vstopna točka programa – zažene začetni zaslon.
     * @param args argumenti ukazne vrstice (neuporabljeni)
     */
    public static void main(String args[]) {
        // Nastavi izgled Nimbus, če je na voljo
        try {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex){
            java.util.logging.Logger.getLogger(ZacetniZaslon.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Prikaži začetni zaslon v AWT niti
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new ZacetniZaslon().setLocationRelativeTo(null);
                new ZacetniZaslon().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
