package co.edu.uniquindio.autenticacion;

import java.io.IOException;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.autenticacion.AutenticacionException;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioNoRegistrado;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.persistencias.UsuarioPersistencia;

public class Autenticador
{
    private Autenticador() {};

    public static boolean autenticar(Credenciales credenciales) throws AutenticacionException
    {
        Sesion.getInstancia().setUsuario(null);
        UsuarioPersistencia persistencia = new UsuarioPersistencia();

        try {
            ListaSimple<Usuario> usuarios = persistencia.todos();

            for (Usuario usuario : usuarios) {
                if (credenciales.comprobar(usuario)) {
                    Sesion.getInstancia().setUsuario(usuario);
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (! Sesion.getInstancia().autenticado()) {
            throw new UsuarioNoRegistrado();
        }

        return true;
    }
}