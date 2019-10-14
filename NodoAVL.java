package BinaryTrees;

public class NodoAVL <T extends Comparable <T>> {
    protected int fe;
    
    //constructor --> hacerlo bien
    public NodoAVL(T element) {
        super(element);
        this.fe=0;
    }//end constructor

    //get y set
    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    
    
}//end class
