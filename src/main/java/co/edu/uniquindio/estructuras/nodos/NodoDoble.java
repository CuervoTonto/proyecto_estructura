package co.edu.uniquindio.estructuras.nodos;

public class NodoDoble<T>
{
    /**
     * valor del nodo
     */
    private T valor;

    /**
     * nodo previo
     */
    private NodoDoble<T> prev;

    /**
     * proximo nodo
     */
    private NodoDoble<T> prox;

    /**
     * construye una nueva instancia
     * 
     * @param prev nodo anterior
     * @param valor valor del nodo
     * @param prox siguiente nodo
     */
    public NodoDoble(NodoDoble<T> prev, T valor, NodoDoble<T> prox)
    {
        this.valor = valor;
        this.prev = prev;
        this.prox = prox;
    }

    public T getValor()
    {
        return this.valor;
    }

    public NodoDoble<T> getPrevio()
    {
        return this.prev;
    }

    public NodoDoble<T> getProximo()
    {
        return this.prox;
    }

    public void setValor(T valor)
    {
        this.valor = valor;
    }

    public void setPrevio(NodoDoble<T> anterior)
    {
        this.prev = anterior;
    }

    public void setProximo(NodoDoble<T> siguiente)
    {
        this.prox = siguiente;
    }
}