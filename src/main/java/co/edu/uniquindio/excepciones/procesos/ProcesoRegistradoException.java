package co.edu.uniquindio.excepciones.procesos;

public class ProcesoRegistradoException extends Exception
{
    public ProcesoRegistradoException() {}

    public ProcesoRegistradoException(String mensaje)
    {
        super(mensaje);
    }
}