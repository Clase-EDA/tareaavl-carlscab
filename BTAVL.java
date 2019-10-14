package Arbol;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author carla
 */
public class BTAVL<T extends Comparable<T>>{ //extends LinkedBT<T> implements BSTADT<T> {
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
        if (termine) 
            rotacion(actual);
        
    }


    
    public boolean borra(T dato){
        NodoAVL<T>  act=new NodoAVL(dato);    //el que queremos borrar
        NodoAVL<T>  papa=act.getPapa();
        boolean band = false;
        
        while(!band && papa!=null){
            if(act==act.getPapa().getIzq())
                act.getPapa().fe+=1;
            else
                act.getPapa().fe-=1;
        }
        if(abs(act.getPapa().fe)==2){
            act=rotacion(act.getPapa());
        }
        if(abs(act.getPapa().fe)==1)
            band=true;
        act=act.getPapa();
        return band;
    }
    
    public void remove(T elem){
        NodoAVL<T> temp=busca(elem, raiz);
        NodoAVL<T> pap=null;
        
        if(temp!=null){
            cont--;
            
            if(temp==raiz){
                //hay hd
                if(temp.getDer()!=null){
                    NodoAVL<T> aux=temp.getDer();
                    NodoAVL<T> hi=raiz.getIzq();
                    NodoAVL<T> hd=raiz.getDer();
                    
                    while(aux.getIzq()!=null){
                        aux=aux.getIzq();
                    }
                    //si no tiene hijos
                    if(aux.getDer()==null && aux.getIzq()==null){
                        aux.getPapa().setIzq(null);
                        rotacion(aux.getPapa());
                    }//hijo der
                    if(aux.getDer()!=null){
                        //si el aux hd es menor al papá del aux
                        if(aux.getDer().getElem().compareTo(aux.getPapa().getElem())<=0){
                            aux.getPapa().setIzq(aux.getDer());
                            rotacion(aux.getPapa());
                        }else{
                            //si el aux hd es mayor al papá del aux
                            NodoAVL<T> paps=aux.getPapa();
                            NodoAVL<T> abue=paps.getPapa();
                            abue.cuelga(aux.getDer(), abue);
                            aux.getDer().cuelga(paps, aux.getDer());
                            rotacion(abue);
                        }
                    }
                    raiz=aux;
                    raiz.cuelga(hi, raiz);
                    raiz.cuelga(hd, raiz);
                    temp.setIzq(null);
                    temp.setDer(null);
                    return;
                }
                //no hay hd
                else{
                    if(temp.getIzq()!=null){
                        raiz=temp.getIzq();
                        rotacion(raiz);
                    }
                    else
                        raiz=null;
                    temp.setIzq(null);
                    return;
                }
            }
            else{
                //el que buscamos no es la raiz
                pap=temp.getPapa();
                
                //si no tiene hijos
                if(temp.getDer()==null && temp.getIzq()==null){
                    if(pap.getIzq()==temp)
                        pap.setIzq(null);
                    else
                        pap.setDer(null);
                    temp.setPapa(null);
                    rotacion(pap);
                }
                //hi sí hd nel
                if(temp.getIzq()!=null && temp.getDer()==null){
                    pap.setIzq(temp.getIzq());
                    temp.setPapa(null);
                    temp.setIzq(null);
                    rotacion(pap);
                }
                //hi no hd sí
                if(temp.getIzq()==null && temp.getDer()!=null){
                    //hd mayor a papa temp
                    if(temp.getDer().getElem().compareTo(pap.getElem())<=0){
                        pap.getPapa().cuelga(temp.getDer(), pap.getPapa());
                        temp.getDer().cuelga(pap, temp.getDer());
                        rotacion(temp.getDer());
                        temp.setPapa(null);
                        temp.setDer(null);
                        return;
                    }else{
                        //hd menor a pap temp
                        pap.cuelga(temp.getDer(), pap);
                        temp.setPapa(null);
                        temp.setDer(null);
                        rotacion(pap);
                        return;
                    }
                }
                
            }
        }
        else
            return;
        
    }
    
    private NodoAVL<T> rotacion(NodoAVL<T> n){
        //izq-izq
        if(n.getFe()==-2 && n.getIzq().getFe()<=0){
            
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=gamma.getIzq();
            NodoAVL<T> B=gamma.getDer();
            NodoAVL<T> C=beta.getDer();
            NodoAVL<T> D=alfa.getDer();
            
            gamma.cuelga(A, gamma);
            gamma.cuelga(B, gamma);
            alfa.cuelga(C, alfa);
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
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getIzq();
            NodoAVL<T> gamma=beta.getDer();
            NodoAVL<T> D= alfa.getDer();
            NodoAVL<T> A=beta.getIzq();
            NodoAVL<T> B=gamma.getIzq();
            NodoAVL<T> C=gamma.getDer();
            
            beta.cuelga(A, beta);
            beta.cuelga(B, beta);
            alfa.cuelga(C, alfa);
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
                beta.setPapa(null);
            }
            if(C!=null){
                gamma.cuelga(C, gamma);
            }
            if(D!=null){
                gamma.cuelga(D, gamma);
            }
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
         //der-izq
        if(n.getFe()==2 && n.getDer().getFe()<0){
            NodoAVL<T> alfa=n;
            NodoAVL<T> papa=n.getPapa();
            NodoAVL<T> beta=alfa.getDer();
            NodoAVL<T> gamma=beta.getIzq();
            NodoAVL<T> A=alfa.getIzq();
            NodoAVL<T> B=gamma.getIzq();
            NodoAVL<T> C=gamma.getDer();
            NodoAVL<T> D=beta.getDer();
            
            alfa.cuelga(A, alfa);
            alfa.cuelga(B, alfa);
            beta.cuelga(C, beta);
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
            lista.add(temp.getElem());//problema
            lista2.add(temp.getFe());
            if (temp.getIzq() != null) {
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
        
    /*    //    // mÃ©todo para acomodar los elementos - LEVELORDER
    public Iterator<T> levelOrder(){
        ArrayList<T> lista= new ArrayList<T>();
        ColaA <NodoBT<T>> cola= new ColaA();
        cola.agrega(root);
        while(!cola.estaVacia()){
           NodoBT<T> actual=cola.quita();
            lista.add(actual.getElement());
            if(actual.getDer()!=null)
                cola.agrega(actual.getDer());
            if(actual.getIzq()!=null)
                cola.agrega(actual.getIzq()); 
        }
        return lista.iterator();
    }//end levelOrder
*/

}

    //a partir del nodo insertado
//    public boolean insertar(T dato){
//        NodoAVL<T>  act=new NodoAVL(dato);    //el que insertamos
//        NodoAVL<T>  papa=act.getPapa();
//        boolean band = false;
//        
//        if (raiz == null) {
//            raiz = act;
//            cont++;
//            return true;
//        }
//        
//        
//        while(!band && papa!=null){
//            if(act==act.getPapa().getIzq())
//                act.getPapa().fe-=1;
//            else
//                act.getPapa().fe+=1;
//        }
//        if(abs(act.getPapa().fe)==2){
//            act=rotacion(act.getPapa());
//            band=true;
//        }
//        if(act.getPapa().fe==0)
//            band=true;
//        act=act.getPapa();
//        return band;
//    }
//    
//  

//    public Iterator<T> imprimeLevelOrden(){
//        ArrayList<T> lista= new ArrayList<T>();
//        ColaA<NodoAVL<T>> cola= new ColaA();
//        cola.agrega(raiz);
//        while(!cola.estaVacia()){
//           NodoAVL<T> actual=cola.quita();
//            lista.add(actual.getElem());
//            if(actual.getDer()!=null)
//                cola.agrega(actual.getDer());
//            if(actual.getIzq()!=null)
//                cola.agrega(actual.getIzq()); 
//        }
//        return lista.iterator();
//    }