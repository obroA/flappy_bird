package pkg2dgamesframework;

/**
 * Generična vrsta (queue) implementirana kot enosmerno povezan seznam.
 * Podpira dodajanje na konec (vstavi), jemanje s spredaj (vzami) in dostop po indeksu.
 * 
 * @param <T> tip elementov v vrsti
 * 
 */
public class VrstaSeznam<T> {
    
    /** Kazalec na prvi in zadnji element */
    private Clen glava, rep;
    
    /** Število elementov v vrsti */
    private int velikost = 0;
    
    /** Konstruktor – ustvari prazno vrsto */
    public VrstaSeznam(){
        glava = rep = null;
    }
    
    /** @return število elementov v vrsti */
    public int vrniVelikost(){
        return velikost;
    }
    
    /**
     * Vstavi element na konec vrste.
     * @param t element, ki ga vstavljamo
     */
    public void vstavi(T t){
        Clen e = new Clen(t);
        if(glava == null){
            glava = rep = e;
        } else {
            rep.naslednji = e;
            rep = e;
        }
        velikost++;
    }
    
    /**
     * Vzame in vrne element s sprednje strani vrste (FIFO).
     * @return prvi element v vrsti
     */
    public T vzami(){
        T vrednost = glava.vrednost;
        glava = glava.naslednji;
        velikost--;
        return vrednost;
    }
    
    /**
     * Vrne element na določenem indeksu (brez odstranitve).
     * @param id indeks elementa (0 = prvi)
     * @return element na danem indeksu, ali null, če ne obstaja
     */
    public T vrni(int id){
        Clen e = glava;
        if(glava == null) return null;
        for(int i = 0; i < id; i++){
            e = e.naslednji;
            if(e == null) return null;
        }
        return e.vrednost;
    }
    
    /**
     * Notranji razred, ki predstavlja en člen povezanega seznama.
     */
    private class Clen {
        
        /** Konstruktor člena z vrednostjo */
        Clen(T vrednost){
            this.vrednost = vrednost;
        }
        
        /** Vrednost, ki jo člen hrani */
        T vrednost;
        
        /** Kazalec na naslednji člen */
        Clen naslednji;
    }
}
