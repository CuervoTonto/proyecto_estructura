package co.edu.uniquindio.persistencias;

import java.io.File;
import java.io.IOException;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.utilidades.Persistencia;

public class UsuarioPersistencia
{
    private String path = "resources/persistencia/users.xml";

    public ListaSimple<Usuario> todos() throws IOException
    {
        crearSiNoExiste();
        Object leido = Persistencia.leerXML(path);
        @SuppressWarnings("unchecked")
        ListaSimple<Usuario> usuarios = (ListaSimple<Usuario>) leido;

        return usuarios;
    }

    public void sobreescribir(ListaSimple<Usuario> lista) throws IOException
    {
        if (lista == null) lista = new ListaSimple<>();

        Persistencia.escribirXML(path, lista);
    }

    private void crearSiNoExiste() throws IOException
    {
        File file = new File(path);

        if (! file.exists()) {
            file.getParentFile().mkdirs();
            sobreescribir(null);
        }
    }
}