package co.edu.uniquindio.excepciones.usuarios;

public class CorreoEnUsoException extends Exception
{
    public CorreoEnUsoException() {}

    public CorreoEnUsoException(String mensaje)
    {
        super(mensaje);
    }
}