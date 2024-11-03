package co.edu.uniquindio.excepciones.autenticacion;

public class UsuarioClaveIncorrectaException extends AutenticacionException
{
    public UsuarioClaveIncorrectaException() {}

    public UsuarioClaveIncorrectaException(String mensaje)
    {
        super(mensaje);
    }
}