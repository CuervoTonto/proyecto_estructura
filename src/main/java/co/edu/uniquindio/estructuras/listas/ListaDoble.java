package co.edu.uniquindio.estructuras.listas;

import java.util.Iterator;

import co.edu.uniquindio.estructuras.nodos.NodoDoble;

public class ListaDoble<E> implements ILista<E>
{
    private NodoDoble<E> primero;
    private NodoDoble<E> ultimo;
    private int longitud;

    public ListaDoble()
    {
        this.primero = this.ultimo = null;
        this.longitud = 0;
    }

    @Override
    public void agregar(E valor)
    {
        enlazarNodoUltimo(new NodoDoble<E>(null, valor, null));
        longitud++;
    }

    public void agregar(int indice, E valor)
    {
        agregarNodo(indice, new NodoDoble<E>(null, valor, null));
    }

    @Override
    public void remover(int indice)
    {
        if (indice < 0 || indice >= longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        removerNodo(indice);
    }

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
            NodoDoble<E> nodo = obtenerNodo(indice);
            elemento = nodo.getValor();
            nodo.getPrevio().setProximo(nodo.getProximo());
            nodo.getProximo().setPrevio(nodo.getPrevio());
        }

        longitud--;

        return elemento;
    }

    @Override
    public E obtener(int indice)
    {
        if (indice < 0 || indice >= longitud) {
            throw new IndexOutOfBoundsException(indice);
        }

        return obtenerNodo(indice).getValor();
    }

    private NodoDoble<E> obtenerNodo(int indice)
    {
        return indice > longitud >> 1
            ? obtenerNodoDerecha(indice)
            : obtenerNodoIzquierda(indice);
    }

    private NodoDoble<E> obtenerNodoDerecha(int indice)
    {
        NodoDoble<E> nodo = ultimo;

        for (int i = longitud - 1; i > indice && nodo != null; i--) {
            nodo = nodo.getPrevio();
        }

        return nodo;
    }

    private NodoDoble<E> obtenerNodoIzquierda(int indice)
    {
        NodoDoble<E> nodo = primero;

        for (int i = 0; i < indice && nodo != null; i++) {
            nodo = nodo.getProximo();
        }

        return nodo;
    }

    private void removerNodo(int indice)
    {
        if (indice == 0) {
            desenlazarPrimero();
        } else if (indice == longitud - 1) {
            desenlazarUltimo();
        } else {
            NodoDoble<E> nodo = obtenerNodo(indice);
            nodo.getPrevio().setProximo(nodo.getProximo());
            nodo.getProximo().setPrevio(nodo.getPrevio());
        }

        longitud--;
    }

    private E desenlazarPrimero()
    {
        E elemento = null;

        if (longitud == 1) {
            elemento = primero.getValor();
            primero = ultimo = null;
        } else {
            elemento = primero.getValor();
            primero.getProximo().setPrevio(primero.getPrevio());
            primero = primero.getProximo();
        }

        return elemento;
    }

    private E desenlazarUltimo()
    {
        E elemento = null;

        if (longitud == 1) {
            elemento = ultimo.getValor();
            primero = ultimo = null;
        } else {
            elemento = ultimo.getValor();
            ultimo.getPrevio().setProximo(ultimo.getProximo());
            ultimo = ultimo.getPrevio();
        }

        return elemento;
    }

    private void agregarNodo(int indice, NodoDoble<E> nuevo)
    {
        if (indice == 0) {
            enlazarNodoPrimero(nuevo);
        } else if (indice == longitud) {
            enlazarNodoUltimo(nuevo);
        } else {
            NodoDoble<E> nodo = obtenerNodo(indice);
            nuevo.setProximo(nodo);
            nuevo.setPrevio(nodo.getPrevio());
            nodo.getPrevio().setProximo(nuevo);
            nodo.setPrevio(nuevo);
        }

        longitud++;
    }

    private void enlazarNodoPrimero(NodoDoble<E> nuevo)
    {
        if (primero == null) {
            primero = ultimo = nuevo;
        } else {
            primero.setPrevio(nuevo);
            nuevo.setProximo(primero);
            primero = nuevo;
        }
    }

    private void enlazarNodoUltimo(NodoDoble<E> nuevo)
    {
        if (ultimo == null) {
            ultimo = primero = nuevo;
        } else {
            ultimo.setProximo(nuevo);
            nuevo.setPrevio(ultimo);
            ultimo = nuevo;
        }
    }

    public int getLongitud()
    {
        return this.longitud;
    }

    /**
     * crea un clon de la lista
     */
    @Override
    protected ListaDoble<E> clone() throws CloneNotSupportedException
    {
        ListaDoble<E> clon = new ListaDoble<>();

        for (NodoDoble<E> n = primero; n != null; n = n.getProximo()) {
            clon.agregar(n.getValor());
        }

        return clon;
    }

    @Override
    public Iterator<E> iterator() 
    {
        return new IteradorLista();
    }

    private class IteradorLista implements Iterator<E>
    {
        private NodoDoble<E> actual;

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