package co.edu.uniquindio.autenticacion;

import co.edu.uniquindio.excepciones.autenticacion.AutenticacionException;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioClaveIncorrecta;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.modelos.Usuario.TipoUsuario;

public class Credenciales
{
    private String correo;
    private String clave;
    private TipoUsuario tipo;

    public Credenciales(String correo, String clave)
    {
        this.correo = correo;
        this.clave = clave;
    }

    public boolean comprobar(Usuario usuario) throws AutenticacionException
    {
        if (usuario == null) return false;

        if (tipo != null) {
            if (! tipo.equals(usuario.getTipo())) return false;
        }

        if (! usuario.getCorreo().equalsIgnoreCase(correo)) return false;

        if (! usuario.getClave().equals(clave)) {
            throw new UsuarioClaveIncorrecta();
        }

        return true;
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
}