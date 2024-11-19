package co.edu.uniquindio.utilidades.persistencia;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.Statement;

import co.edu.uniquindio.estructuras.listas.ILista;

public class ListaDelegate extends PersistenceDelegate
{
    @Override
    protected Expression instantiate(Object old, Encoder out)
    {
        return new Expression(old, old.getClass(), "new", new Object[0]);
    }

    @Override
    protected void initialize(Class<?> type, Object old, Object newInstance, Encoder out)
    {
        ILista<?> lista = (ILista<?>) old;
        
        for (Object item : lista) {
            out.writeStatement(
                new Statement(lista, "agregar", new Object[] { item })
            );
        }
    }
}