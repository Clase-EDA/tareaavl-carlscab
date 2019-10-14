
package BinaryTrees;

import Importados.EmptyCollectionException;

public class AVLTree <T extends Comparable <T>> extends SearchBinaryTree {
    
    public void addAVL (T elem) {
        //Verificar que dato no sea nulo 
        if (elem!=null){
            //crear el nodo
            NodoAVL <T> nuevo = new NodoAVL(elem);
            NodoAVL <T> aux=(NodoAVL <T>) root;
            //caso de que no haya datos --> lo mete en la raíz
            if(aux==null){
                root=nuevo;
                count++;
                return;
            }//cierre if 
            //ciclo para recorrer el árbol
            while (aux.getDer()!=null && aux.getIzq()!=null){
                if (aux.getElement().compareTo(elem)>0){
                    //elemento es más pequeño --> se va a la izquierda
                    aux=(NodoAVL<T>) aux.getIzq();
                } else {
                    //elemento es más grande --> se va a la derecha
                    aux=(NodoAVL<T>) aux.getDer();
                }//cierre del else
            }//cierre del while
            //para meter el dato 
            if (aux.getElement().compareTo(elem)>0){
                //elemento es más pequeño --> se va a la izquierda
                aux.setIzq(nuevo);
                //conectar el papá
                nuevo.setPapa(aux);
                //aumentar número de nodos
                count++;
            } else {
                //elemento es más grande --> se va a la derecha
                aux.setDer(nuevo);
                //conectar el papá
                nuevo.setPapa(aux);
                //aumentar número de nodos
                count++;
            }//cierre del else
            //empieza parte diferente para árboles avl -------------------------
            //a partir del nodo insertado 
            //CASO 1 -----------------------------------------------------------
            NodoAVL <T> actual = nuevo;
            NodoAVL <T> papa = (NodoAVL <T>) actual.getPapa();
            boolean termine=false;
            while (!termine && papa!=null){
                if (actual==actual.getPapa().getElement())
                    actual.getPapa().fe-=1;
                else
                    actual.getPapa().fe+=1;
                if (Math.abs(actual.getPapa().getFe)==2){
                    actual=rotacion(actual.getPapa());
                    termine=true;
                }//cierre del if
                if (actual.getPapa().getFe()==0)
                    termine=true;
                actual=actual.getPapa();
            }//cierre del while
            //CASO 2 -----------------------------------------------------------
            NodoAVL <T> actual = nuevo;
            NodoAVL <T> papa = (NodoAVL <T>) actual.getPapa();
            boolean termine=false;
            while (!termine && papa!=null){
                if (actual==actual.getPapa().getElement())
                    actual.getPapa().fe+=1;
                else
                    actual.getPapa().fe-=1;
                if (Math.abs(actual.getPapa().getFe())==2){
                    actual=rotacion(actual.getPapa());
                    termine=true;
                }//cierre del if
                if (Math.abs(actual.getPapa().getFe())==1)
                    termine=true;
                actual=actual.getPapa();
            }//cierre del while
        } else {
            throw new EmptyCollectionException ("\nDato null");
        }//cierre del else 
    }//end add
    
    private NodoAVL <T> rotación (NodoAVL <T> n){
        //rotación izq-izq
        if (n.fe==-2 && (n.izq.fe==-1) || (n.izq.fe==0)){
            alfa=n;
            papa=n.getPapa();
            beta=alfa.getIzq();
            gamma=beta.getIzq();
            a=gamma.getIzq();
            b=gamma.getDer();
            c=beta.getDer();
            d=alfa.getDer();
            
            gamma.cuelga(A);
            gamma.cuelga(B);
            alfa.cuelga(C);
            alfa.cuelga(D);
            beta.cuelga(alfa);
            beta.cuelga(gamma);
            
            if (papa!=null)
                papa.cuelga(beta);
            else {
                beta.setPapa(null);
                root=beta;
            }//cierre del else
            
            //factor de equilibrio es altura del derecho menos altura del izquierdo 
            alfa.setFe(altura(alfa.getDer())-altura(alfa.getIzq()));
            beta.setFe(altura(alfa)-altura(gamma));
            return beta;    
            
        }//cierre del if
    } //end private
    
}//end class
