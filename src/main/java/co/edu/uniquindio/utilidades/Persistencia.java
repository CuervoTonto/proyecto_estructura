package co.edu.uniquindio.utilidades;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.estructuras.listas.ListaDobleCircular;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.utilidades.persistencia.ColaDelegate;
import co.edu.uniquindio.utilidades.persistencia.ListaDelegate;

final public class Persistencia
{
    private Persistencia() {}

    public static Object leerXML(String path) throws IOException
    {
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));
        Object object = decoder.readObject();
        decoder.close();
        
        return object;
    }

    public static void escribirXML(String path, Object object) throws IOException
    {
        XMLEncoder encoder = makeEncoder(path);
        encoder.writeObject(object);
        encoder.close();
    }

    public static XMLEncoder makeEncoder(String path) throws FileNotFoundException
    {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(path));

        encoder.setPersistenceDelegate(ListaSimple.class, new ListaDelegate());
        encoder.setPersistenceDelegate(ListaDobleCircular.class, new ListaDelegate());
        encoder.setPersistenceDelegate(Cola.class, new ColaDelegate());

        return encoder;
    }
}