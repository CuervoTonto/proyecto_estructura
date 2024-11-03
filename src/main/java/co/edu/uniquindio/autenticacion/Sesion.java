package co.edu.uniquindio.autenticacion;

import co.edu.uniquindio.modelos.Usuario;

public class Sesion
{
    private Usuario usuario;

    private static Sesion instancia;

    protected Sesion() {}

    public boolean autenticado()
    {
        return this.usuario != null;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public static Sesion getInstancia()
    {
        if (instancia == null) instancia = new Sesion();

        return instancia;
    }
}