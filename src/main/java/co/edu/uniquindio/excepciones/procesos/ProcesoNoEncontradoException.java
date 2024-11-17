package co.edu.uniquindio.excepciones.procesos;

public class ProcesoNoEncontradoException extends Exception
{
    public ProcesoNoEncontradoException() {}

    public ProcesoNoEncontradoException(String mensaje)
    {
        super(mensaje);
    }
}
