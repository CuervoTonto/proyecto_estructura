package co.edu.uniquindio.utilidades;

import java.util.Iterator;

import co.edu.uniquindio.estructuras.listas.ILista;

public class UtilidadILista
{
    private UtilidadILista() {}

    public static boolean iguales(ILista<?> lista1, ILista<?> lista2)
    {
        if (lista1.getLongitud() != lista2.getLongitud()) return false;

        Iterator<?> it1 = lista1.iterator();
        Iterator<?> it2 = lista2.iterator();

        while (it1.hasNext()) {
            if (! it1.next().equals(it2.next())) return false;
        }

        return true;
    }
}