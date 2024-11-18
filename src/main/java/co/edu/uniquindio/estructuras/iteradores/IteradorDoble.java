package co.edu.uniquindio.estructuras.iteradores;

import java.util.Iterator;

public interface IteradorDoble<E> extends Iterator<E>
{
    public boolean hasPrevious();
    public E previous();
}