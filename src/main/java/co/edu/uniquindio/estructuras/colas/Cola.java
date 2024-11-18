package co.edu.uniquindio.estructuras.colas;

import java.util.NoSuchElementException;

import co.edu.uniquindio.estructuras.nodos.NodoSimple;

public class Cola<E>
{
    /**
     * primer nodo de la estructura
     */
    private NodoSimple<E> primero;

    /**
     * ultimo nodo de la estructura
     */
    private NodoSimple<E> ultimo;

    /**
     * numero de elementos en la estructura
     */
    private int longitud;

    /**
     * construye una nueva instancia
     */
    public Cola()
    {
        this.primero = this.ultimo = null;
    }

    /**
     * agrega un elemento al final
     * 
     * @param e elemento a agregar
     */
    public void encolar(E e)
    {
        NodoSimple<E> nuevo = new NodoSimple<>(e);

        if (this.estaVacia()) {
            this.primero = this.ultimo = nuevo;
        } else {
            ultimo.setProximo(nuevo);
            ultimo = nuevo;
        }

        longitud++;
    }

    /**
     * agrega un elemento en un posicion especifica (empieza en 0)
     * 
     * @param posicion posicion (indice) para el elemento
     * @param e elemento a agregar
     */
    public void encolar(int posicion, E e)
    {
        if (posicion < 0 || posicion > longitud) {
            throw new IndexOutOfBoundsException();
        }

        NodoSimple<E> nuevo = new NodoSimple<E>(e);

        if (this.estaVacia()) {
            this.primero = this.ultimo = nuevo;
        } else if (posicion == 0) {
            nuevo.setProximo(primero);
            primero = nuevo;
        } else if (posicion == longitud) {
            ultimo.setProximo(nuevo);
            ultimo = nuevo;
        } else {
            NodoSimple<E> pre = obtenerNodo(posicion - 1);
            nuevo.setProximo(pre.getProximo());
            pre.setProximo(nuevo);
        }

        longitud++;
    }

    /**
     * obtiene un nodo dado su indice
     * 
     * @param indice un indice valido
     * 
     * @return nodo
     */
    private NodoSimple<E> obtenerNodo(int indice)
    {
        NodoSimple<E> nodo = primero;

        for (int i = 0; i < indice; i++) nodo = nodo.getProximo();

        return nodo;
    }

    /**
     * obtiene y remueve el primer elemento
     * 
     * @return primer elemento en la estructura
     * 
     * @throws NoSuchElementException
     */
    public E desencolar()
    {
        if (this.estaVacia()) {
            throw new NoSuchElementException();
        }

        E valor = this.primero.getValor();
        this.primero = this.primero.getProximo();
        longitud--;

        return valor;
    }

    /**
     * obtiene el primero elemento
     * 
     * @return primer elemento en la estructura
     * 
     * @throws NoSuchElementException
     */
    public E elemento()
    {
        if (this.estaVacia()) {
            throw new NoSuchElementException();
        }

        return this.primero.getValor();
    }

    /**
     * comprueba si la estrutura esta vacia
     * 
     * @return la cola esta vacia
     */
    public boolean estaVacia()
    {
        return this.longitud == 0;
    }

    /**
     * obtiene la longitud de la cola
     * 
     * @return numero de elementos
     */
    public int longitud()
    {
        return this.longitud;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Cola<?> other = (Cola<?>) obj;

        if (longitud() != other.longitud()) return false;

        NodoSimple<E> aux1 = primero;
        NodoSimple<?> aux2 = other.primero;

        while (aux1 != null) {
            if (! aux1.getValor().equals(aux2.getValor())) return false;
            aux1 = aux1.getProximo();
            aux2 = aux2.getProximo();
        }

        return true;
    }

    @Override
    public Cola<E> clone()
    {
        Cola<E> clon = new Cola<>();
        NodoSimple<E> actual = primero;

        while (actual != null) {
            clon.encolar(actual.getValor());
            actual = actual.getProximo();
        }

        return clon;
    }
}