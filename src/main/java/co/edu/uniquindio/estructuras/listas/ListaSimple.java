package co.edu.uniquindio.estructuras.listas;

import java.util.Iterator;

import co.edu.uniquindio.estructuras.nodos.NodoSimple;

public class ListaSimple<E> implements ILista<E>, Cloneable
{
    /**
     * primer nodo de la lista
     */
    private NodoSimple<E> primero;

    /**
     * ultimo nodo de la lista
     */
    private NodoSimple<E> ultimo;

    /**
     * numero de elementos en la lista
     */
    private int longitud;

    /**
     * construye una nueva instancia
     */
    public ListaSimple()
    {
        this.primero = this.ultimo = null;
        this.longitud = 0;
    }

    /**
     * enlaza un nodo (esto no aumenta la longitud)
     */
    private void enlazarNodoPrimero(NodoSimple<E> nodo)
    {
        if (primero == null) {
            primero = ultimo = nodo;
        } else {
            nodo.setProximo(primero);
            primero = nodo;
        }
    }

    /**
     * enlaza un nodo (esto no aumenta la longitud)
     */
    private void enlazarNodoUltimo(NodoSimple<E> nodo)
    {
        if (ultimo == null) {
            primero = ultimo = nodo;
        } else {
            ultimo.setProximo(nodo);
            ultimo = nodo;
        }
    }

    /**
     * agrega un nodo en una posicion dada
     */
    private void agregarNodo(int indice, NodoSimple<E> nuevo)
    {
        if (indice == 0) {
            enlazarNodoPrimero(nuevo);
        } else if (indice == longitud) {
            enlazarNodoUltimo(nuevo);
        } else {
            NodoSimple<E> aux = obtenerNodo(indice - 1);
            nuevo.setProximo(aux.getProximo());
            aux.setProximo(nuevo);
        }

        longitud++;
    }

    /**
     * obtiene un nodo dada su posicion
     */
    private NodoSimple<E> obtenerNodo(int indice)
    {
        NodoSimple<E> aux = primero;

        for (int i = 0; i < indice; i++) aux = aux.getProximo();

        return aux;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void agregar(E elemento)
    {
        enlazarNodoUltimo(new NodoSimple<E>(elemento, null));
        longitud++;
    }

    /**
     * agrega un elemento en un indice dado
     * 
     * @param indice un indice valido
     * @param e elemento para agregar
     */
    public void agregar(int indice, E e)
    {
        if (indice < 0 || indice > longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        agregarNodo(indice, new NodoSimple<E>(e, null));
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public E obtener(int indice)
    {
        if (indice < 0 || indice >= longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        return obtenerNodo(indice).getValor();
    }

    /**
     * quita las referencias del primero nodo (obtiene su valor)
     */
    private E desenlazarPrimero()
    {
        E valor = primero.getValor();

        if (longitud == 1) {
            primero = ultimo = null;
        } else {
            primero = primero.getProximo();
        }

        return valor;
    }

    /**
     * quita las referencias al ultimo nodo (obtiene su valor)
     */
    private E desenlazarUltimo()
    {
        E valor = ultimo.getValor();

        if (longitud == 1) {
            primero = ultimo = null;
        } else  {
            ultimo = obtenerNodo(longitud - 2);
            ultimo.setProximo(null);
        }

        return valor;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void remover(int indice)
    {
        if (indice < 0 || indice >= longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        removerNodo(indice);
    }

    /**
     * obtiene y remueve un elemento segun su indice
     * 
     * @param indice indice valido
     * 
     * @return elemento del indice
     */
    public E sacar(int indice)
    {
        if (indice < 0 || indice >= longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        E elemento = null;

        if (indice == 0) {
            elemento = desenlazarPrimero();
        } else if (indice == longitud - 1) {
            elemento = desenlazarUltimo();
        } else {
            NodoSimple<E> nodo = obtenerNodo(indice - 1);
            elemento = nodo.getProximo().getValor();
            nodo.setProximo(nodo.getProximo().getProximo());
        }

        longitud--;

        return elemento;
    }

    /**
     * remueve el elemento del indice
     * 
     * @param indice indice valido
     */
    private void removerNodo(int indice)
    {
        if (indice == 0) {
            desenlazarPrimero();
        } else if (indice == longitud - 1) {
            desenlazarUltimo();
        } else {
            NodoSimple<E> aux = obtenerNodo(indice - 1);
            aux.setProximo(aux.getProximo().getProximo());
        }

        longitud--;
    }

    /**
     * crea un clon de la lista
     */
    @Override
    protected ListaSimple<E> clone() throws CloneNotSupportedException
    {
        ListaSimple<E> clon = new ListaSimple<>();

        for (NodoSimple<E> n = primero; n != null; n = n.getProximo()) {
            clon.agregar(n.getValor());
        }

        return clon;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new IteradorLista();
    }

    /**
     * obtiene la cantidad de elementos en la lista
     */
    public int getLongitud()
    {
        return this.longitud;
    }

    private class IteradorLista implements Iterator<E>
    {
        private NodoSimple<E> actual;

        public IteradorLista()
        {
            this.actual = primero;
        }

        @Override
        public boolean hasNext()
        {
            return this.actual != null;
        }

        @Override
        public E next()
        {
            E valor = this.actual.getValor();
            this.actual = actual.getProximo();

            return valor;
        }
    }
}