package co.edu.uniquindio.estructuras.pilas;

import java.util.NoSuchElementException;

import co.edu.uniquindio.estructuras.nodos.NodoSimple;

public class Pila<E>
{
    /**
     * ultimo nodo de la pila (cima)
     */
    private NodoSimple<E> ultimo;

    /**
     * longitud de la pila
     */
    private int longitud;

    /**
     * construye una nueva instancia
     */
    public Pila()
    {
        this.ultimo = null;
    }

    /**
     * agrega un elemento a la pila
     * 
     * @param e elemento a agregar
     */
    public void apilar(E e)
    {
        NodoSimple<E> nuevo = new NodoSimple<E>(e);

        if (this.estaVacia()) {
            this.ultimo = nuevo;
        } else {
            nuevo.setProximo(this.ultimo);
            this.ultimo = nuevo;
        }

        longitud++;
    }

    /**
     * obtiene y remueve el ultimo elemento de la pila
     * 
     * @return ultimo elemento agregado
     * 
     * @throws NoSuchElementException
     */
    public E desapilar()
    {
        if (this.estaVacia()) {
            throw new NoSuchElementException();
        }

        E valor = this.ultimo.getValor();
        this.ultimo = ultimo.getProximo();
        longitud--;

        return valor;
    }

    /**
     * obtiene el elemento en la cima
     * 
     * @return ultimo elemento agregado
     */
    public E cima()
    {
        if (this.estaVacia()) {
            throw new NoSuchElementException();
        }

        return this.ultimo.getValor();
    }

    /**
     * comprueba si la estrutura esta vacia
     * 
     * @return la pila esta vacia
     */
    public boolean estaVacia()
    {
        return this.longitud == 0;
    }

    /**
     * obtiene la longitud de la pila
     * 
     * @return numero de elementos
     */
    public int longitud()
    {
        return this.longitud;
    }
}