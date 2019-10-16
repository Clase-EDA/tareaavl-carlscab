package Arbol;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author carla
 */
public class BTAVL<T extends Comparable<T>> {
    protected int cont;
    protected NodoAVL<T> raiz;
    
    public BTAVL(){
        cont = 0;
        raiz=null;
    }
    
     public void add(T elem) {
        NodoAVL<T> actual, papa, nuevo;
        nuevo = new NodoAVL(elem);
        actual = raiz;
        papa = actual;
        boolean termine;
        if (raiz == null) {
            raiz = nuevo;
            cont++;
            return;
        }
        while (actual != null) {
            papa = actual;
            if (actual.getElem().compareTo(elem) > 0) {
                actual = actual.getIzq();
            } else {
                actual = actual.getDer();
            }
        }
        papa.cuelga(nuevo, papa);
        cont++;
        actual = nuevo;
        termine = false;
        while (actual != raiz && !termine) {
            if (actual.getFe() == 0 && actual.getDer() != null && actual.getIzq() != null) {
                actual = papa;
                papa = papa.getPapa();
                if (Math.abs(actual.getFe()) == 2) {
                    termine = true;
                }
            } else {
                if (papa.getDer() == actual) {
                    papa.setFe(papa.getFe() + 1);
                } else {
                    papa.setFe(papa.getFe() - 1);
                }
                actual = papa;
                papa = papa.getPapa();
                if (Math.abs(actual.getFe()) == 2) {
                    termine = true;
                }
            }
        }
        if (termine) {
            rotacion(actual);
        }
        
    }
     
//    public boolean borra(T dato){
//        NodoAVL<T>  act=new NodoAVL(dato);    //el que queremos borrar
//        NodoAVL<T>  papa=act.getPapa();
//        boolean band = false;
//        
//        while(!band && papa!=null){
//            if(act==act.getPapa().getIzq())
//                act.getPapa().fe+=1;
//            else
//                act.getPapa().fe-=1;
//        }
//        if(abs(act.getPapa().fe)==2){
//            act=rotacion(act.getPapa());
//        }
//        if(abs(act.getPapa().fe)==1)
//            band=true;
//        act=act.getPapa();
//        return band;
//    }
    
    public void remove(T elem){
        NodoAVL<T> temp=busca(elem, raiz);
        NodoAVL<T> pap=null;
        boolean ban=false;
        
        if(temp!=null){
            cont--;
            NodoAVL<T> hi=temp.getIzq();
            NodoAVL<T> hd=temp.getDer();
            
            
            //nodo no tiene hijos
            if(hi==null && hd==null){
                if(raiz==temp){
                    raiz=null;
                    raiz.setFe(0);
                    raiz.setPapa(null);
                }
                else{
                    pap=temp.getPapa();
                       //no es la raiz y no tiene hijos
                    if(pap.getDer()==temp){
                        pap.setDer(null);
                        //pap.setFe(--
                    }
                    else{
                        pap.setIzq(null);
                        //pap.setFe(++
                    }
                }
            }else{
                //tiene algún esquincle
                
                //tiene izq, pero no der
                if(hd==null && hi!=null){
                    if(raiz==temp){
                        raiz=hi;
                        raiz.setPapa(null);
                        //raiz.setFe(0);
                    }
                    else{
                        pap=temp.getPapa();
                        pap.cuelga(hi, pap);
                        //pap.setFe++
                    }
                }else{
                    //tiene der, pero no izq
                    if(hd!=null && hi==null){
                        if(raiz==temp){
                            raiz=hd;
                            raiz.setPapa(null);
                            //raiz.setFe(0);
                        }
                        else{
                            pap=temp.getPapa();
                            pap.cuelga(hd, pap);
                            //pap.setFe--
                        }
                    }
                    else{
                        //tiene los dos izq/der
                        pap=temp.getDer();
                        NodoAVL<T> act=pap.getIzq();

                        while(act!=null){
                            pap=act;
                            act=pap.getIzq();
                        }
                        T ndato=pap.getElem();
                        
                        if(pap.getDer()==null){
                            if(ndato.compareTo(pap.getPapa().getElem())>0)
                                pap.getPapa().setDer(null);
                            else
                                pap.getPapa().setIzq(null);
                        }
                        else{
                            if(ndato.compareTo(pap.getPapa().getElem())>0)
                                pap.getPapa().setDer(pap.getDer());
                            else
                                pap.getPapa().setIzq(pap.getDer());
                        }
                        pap.getPapa().setElem(ndato);
                    }
                }
            }
            
            if(pap!=null){
                while(!ban && pap!=null){
                    if(pap.getElem().compareTo(elem)>0){
                        pap.setFe(pap.getFe()+1);
                    }
                    else{
                        pap.setFe(pap.getFe()-1);
                    }
                    if(Math.abs(pap.getFe())>=2){
                        rotacion(pap.getPapa());
                        ban=true;
                    }
                    if(Math.abs(pap.getFe())==1){
                        ban=true;
                    }
                    pap=pap.getPapa();
                }
            }
            return;
        }
        else
            return;
            

    }
    
         
    private NodoAVL<T> rotacion(NodoAVL<T> n){
        //izq-izq
        if(n.getFe()==-2 && n.getIzq().getFe()<=0){
            System.out.println("izqizq");
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=gamma.getIzq();
            NodoAVL<T> B=gamma.getDer();
            NodoAVL<T> C=beta.getDer();
            NodoAVL<T> D=alfa.getDer();
            
            
            if(A!=null)
                gamma.cuelga(A, gamma);
            if(B!=null)
                gamma.cuelga(B, gamma);
            if(C!=null)
                alfa.cuelga(C, alfa);
            else
                alfa.setIzq(C);
            if(D!=null)
                alfa.cuelga(D, alfa);
            beta.cuelga(alfa, beta);
            beta.cuelga(gamma, beta);
            if(papa!=null)
                papa.cuelga(beta, papa);
            else{
                beta.setPapa(null);
                raiz=beta;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return beta;
        }
        //izq-der
        if(n.getFe()==-2 && n.getIzq().getFe()>0){
            System.out.println("izqder");
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getDer();
            NodoAVL<T> D= alfa.getDer();
            NodoAVL<T> A=beta.getIzq();
            NodoAVL<T> B=gamma.getIzq();
            NodoAVL<T> C=gamma.getDer();
            
            if(A!=null)
                beta.cuelga(A, beta);
            if(B!=null)
                beta.cuelga(B, beta);
            else
                beta.setDer(null);
            if(C!=null)
                alfa.cuelga(C, alfa);
            else
                alfa.setIzq(null);
            if(D!=null)
                alfa.cuelga(D, alfa);
            gamma.cuelga(beta, gamma);
            gamma.cuelga(alfa, gamma);
            
            if(papa!=null)
                papa.cuelga(gamma, papa);
            else{
                gamma.setPapa(null);
                raiz=gamma;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return gamma;   
        }
        
        //der-der
        if(n.getFe()==2 && n.getDer().getFe()>=0){
            System.out.println("derder");
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getDer();
            NodoAVL<T> gamma=beta.getDer();
            NodoAVL<T> A=alfa.getIzq();
            NodoAVL<T> B=beta.getIzq();
            NodoAVL<T> C=gamma.getIzq();
            NodoAVL<T> D=gamma.getDer();
            
            if(A!=null){
                alfa.cuelga(A, alfa);
            }
            if(B!=null){
                alfa.cuelga(B, alfa);
            }
            else{
               alfa.setDer(null);
            }
            if(C!=null){
                gamma.cuelga(C, gamma);
            }
            if(D!=null){
                gamma.cuelga(D, gamma);
            }
            beta.cuelga(alfa, beta);
            beta.cuelga(gamma, beta);
            
            if(papa!=null){
                papa.cuelga(beta, papa);
                papa.setFe();
            }else{
                beta.setPapa(null);
                raiz=beta;
                raiz.setFe();
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            
            return beta;   
        }
         //der-izq
        if(n.getFe()==2 && n.getDer().getFe()<0){
            System.out.println("derizq");
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getDer();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=alfa.getIzq();
            NodoAVL<T> B=gamma.getIzq();
            NodoAVL<T> C=gamma.getDer();
            NodoAVL<T> D=beta.getDer();
            
            if(A!=null)
                alfa.cuelga(A, alfa);
            if(B!=null)
                alfa.cuelga(B, alfa);
            else
                alfa.setDer(null);
            if(C!=null)
                beta.cuelga(C, beta);
            else
                beta.setIzq(null);
            if(D!=null)
                beta.cuelga(D, beta);
            gamma.cuelga(alfa, gamma);
            gamma.cuelga(beta, gamma);
            
            if(papa!=null)
                papa.cuelga(gamma, papa);
            else{
                gamma.setPapa(null);
                raiz=gamma;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return gamma;   
              
        }
        else
            return null;
    }
    
    public T find(T elem) {
        if (busca(elem, raiz) == null) {
            String a="No se encontró el elemento.";
            return (T) a;
        }
        return elem;
    }

    private NodoAVL busca(T elem, NodoAVL actual) {
        NodoAVL<T> temp;
        if (actual == null) {
            return null;
        }
        if (actual.getElem().equals(elem)) {
            return actual;
        }
        temp = busca(elem, actual.getIzq());
        if (temp == null) {
            temp = busca(elem, actual.getDer());
        }
        return temp;
    }

    public void imprime() {
        ArrayList<NodoAVL<T>> aux = new ArrayList<NodoAVL<T>>();
        ArrayList<T> lista = new ArrayList<T>();
        ArrayList<Integer> lista2 = new ArrayList<>();
        aux.add(raiz);

        NodoAVL<T> temp;
        while (!aux.isEmpty()) {
            temp = aux.remove(0);
            
            //System.out.println(temp.getElem());
            
            if(temp.getElem()!=null){
                lista2.add(temp.getFe());   //problema
                lista.add(temp.getElem());//problema
            }
            if (temp.getIzq() != null) {
                //quitar al agregar
                aux.add(temp.getIzq());
            }
            if (temp.getDer() != null) {
                aux.add(temp.getDer());
            }
        }
        while (lista.iterator().hasNext()) {
            T x = lista.remove(0);
            int y = lista2.remove(0);
            System.out.println("Elemento almacenado: " + x + "  Factor de equilibrio: " + y + "\n");
        }
    }
        
    public void levelOrder(){
        ArrayList<T> lista= new ArrayList<T>();
        ColaA <NodoAVL<T>> cola= new ColaA();
        cola.agrega(raiz);
        ArrayList<Integer> lista2= new ArrayList<>();
        
        while(!cola.estaVacia()){
           NodoAVL<T> actual=cola.quita();
            lista.add(actual.getElem());   
            lista2.add(actual.getFe());     
            if(actual.getDer()!=null)
                cola.agrega(actual.getDer());
            if(actual.getIzq()!=null)
                cola.agrega(actual.getIzq()); 
        }
        while(lista.iterator().hasNext()){
            T x = lista.remove(0);
            int y = lista2.remove(0);
            System.out.println("Elemento almacenado: " + x + "  Factor de equilibrio: " + y + "\n");
        }
    }
}
