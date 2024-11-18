package co.edu.uniquindio.excepciones.validaciones;

public class ValidacionException extends Exception
{
    public ValidacionException() {}

    public ValidacionException(String mensaje)
    {
        super(mensaje);
    }
}