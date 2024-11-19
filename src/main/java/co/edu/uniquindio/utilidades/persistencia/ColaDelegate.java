package co.edu.uniquindio.utilidades.persistencia;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.Statement;

import co.edu.uniquindio.estructuras.colas.Cola;

public class ColaDelegate extends PersistenceDelegate
{
    @Override
    protected Expression instantiate(Object old, Encoder out)
    {
        return new Expression(old, old.getClass(), "new", new Object[0]);
    }

    @Override
    protected void initialize(Class<?> type, Object old, Object newInstance, Encoder out)
    {
        @SuppressWarnings("unchecked")
        Cola<Object> cola = (Cola<Object>) old;

        for (int i = 0; i < cola.longitud(); i++) {
            Object object = cola.desencolar();

            out.writeStatement(
                new Statement(old, "encolar", new Object[] { object })
            );

            cola.encolar(object);
        }
    }
}