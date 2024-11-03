package co.edu.uniquindio.excepciones.autenticacion;

public class AutenticacionException extends Exception
{
    public AutenticacionException() {}

    public AutenticacionException(String mensaje)
    {
        super(mensaje);
    }
}