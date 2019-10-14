/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

/**
 *
 * @author carla
 * @param <T>
 */
public class NodoAVL<T extends Comparable>{  
    
    protected NodoAVL<T> izq,der,papa;
    protected int fe;
    protected T elem;

    public NodoAVL(T element) {
        elem=element;
        fe=0;
        izq=null;
        der=null;
        papa=null;
    }
    
    public void cuelga(NodoAVL<T> nodo, NodoAVL<T> otroyo){
        if(nodo==null)
            return;
        if(elem.compareTo(nodo.getElem()) > 0)
            izq=nodo;
        else
            der=nodo;
        nodo.setPapa(otroyo);
        
    }
    
    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public int getFe() {
        return fe;
    }

    public void setFe() {
        fe= getAltura(der)-getAltura(izq);
    }
    
    public int getAltura(NodoAVL<T> actual){
        if(actual == null)
            return 0;
        return Math.max(numDescendientesIzq(), numDescendientesDer());
    }
    
    public int numDescendientes(){
        int resp=0;
        if(izq!=null)
            resp=izq.numDescendientes()+1;
        if(der!=null)
            resp=der.numDescendientes()+1;
        return resp;
    }
    
    public int numDescendientesIzq(){
        int resp=0;
        if(izq!=null)
            resp=izq.numDescendientesIzq()+1;
        return resp;
    }
    
    public int numDescendientesDer(){
        int resp=0;
        if(der!=null)
            resp=der.numDescendientesDer()+1;
        return resp;
    }
    

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    @Override
    public String toString(){
        return "Elemento: " + elem.toString() + "Factor de Equilibrio: " + fe;
    }
 
    
    
}
