package co.edu.uniquindio.modelos;

import java.io.Serializable;

public class Usuario implements Serializable
{
    private String nombre;
    private String correo;
    private String clave;
    private TipoUsuario tipo;

    public Usuario() {}

    public Usuario(String nombre, String correo, String clave)
    {
        this(nombre, correo, clave, TipoUsuario.NORMAL);
    }

    public Usuario(String nombre, String correo, String clave, TipoUsuario tipo)
    {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.tipo = tipo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getClave()
    {
        return clave;
    }

    public void setClave(String clave)
    {
        this.clave = clave;
    }

    public TipoUsuario getTipo()
    {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo)
    {
        this.tipo = tipo;
    }

    public static enum TipoUsuario
    {
        NORMAL,
        ADMINISTRADOR;
    }
}