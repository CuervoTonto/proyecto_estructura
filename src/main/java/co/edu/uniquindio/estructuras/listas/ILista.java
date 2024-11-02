package co.edu.uniquindio.estructuras.listas;

public interface ILista<E>
{
    /**
     * agrega un elemento a la lista
     * 
     * @param valor valor a agregar
     */
    public void agregar(E valor);

    /**
     * remueve un elemento de la lista
     * 
     * @param indice indice valido de un elemento
     * 
     * @return elemento removido
     */
    public void remover(int indice);

    /**
     * obtiene un elemento de la lista
     * 
     * @param indice indice valido de un elemento
     * 
     * @return elemento en el indice
     */
    public E obtener(int indice);
}