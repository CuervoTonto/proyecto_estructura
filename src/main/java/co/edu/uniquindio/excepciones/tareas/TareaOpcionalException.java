package co.edu.uniquindio.excepciones.tareas;

public class TareaOpcionalException extends Exception
{
    public TareaOpcionalException() {}

    public TareaOpcionalException(String mensaje)
    {
        super(mensaje);
    }
}