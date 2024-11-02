package co.edu.uniquindio.estructuras.nodos;

public class NodoSimple<T>
{
    /**
     * valor del nodo
     */
    private T valor;

    /**
     * proximo nodo
     */
    private NodoSimple<T> prox;

    /**
     * construye una nueva instancia
     * 
     * @param valor valor para el nodo
     */
    public NodoSimple(T valor)
    {
        this(valor, null);
    }

    /**
     * construye una nueva instancia
     * 
     * @param valor valor para el nodo
     * @param prox proximo nodo
     */
    public NodoSimple(T valor, NodoSimple<T> proximo)
    {
        this.valor = valor;
        this.prox = proximo;
    }

    public T getValor()
    {
        return this.valor;
    }

    public void setValor(T valor)
    {
        this.valor = valor;
    }

    public NodoSimple<T> getProximo()
    {
        return this.prox;
    }

    public void setProximo(NodoSimple<T> prox)
    {
        this.prox = prox;
    }
}