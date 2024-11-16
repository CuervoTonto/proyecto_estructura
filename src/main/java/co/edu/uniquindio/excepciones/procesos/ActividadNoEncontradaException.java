package co.edu.uniquindio.excepciones.procesos;

public class ActividadNoEncontradaException extends Exception
{
    public ActividadNoEncontradaException() {}

    public ActividadNoEncontradaException(String mensaje)
    {
        super(mensaje);
    }
}