package co.edu.uniquindio.autenticacion;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioClaveIncorrectaException;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioNoRegistradoException;
import co.edu.uniquindio.gestores.GestorUsuarios;
import co.edu.uniquindio.modelos.Usuario;

public class Autenticador
{
    private Autenticador() {};

    public static boolean autenticar(Credenciales credenciales) throws UsuarioNoRegistradoException, UsuarioClaveIncorrectaException
    {
        Sesion.getInstancia().setUsuario(null);

        ListaSimple<Usuario> usuarios = GestorUsuarios.instancia().getUsuarios();

        for (Usuario usuario : usuarios) {
            if (credenciales.comprobar(usuario)) {
                Sesion.getInstancia().setUsuario(usuario);
            }
        }

        if (! Sesion.getInstancia().autenticado()) {
            throw new UsuarioNoRegistradoException();
        }

        return true;
    }
}