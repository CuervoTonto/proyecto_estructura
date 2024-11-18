package co.edu.uniquindio.excepciones.validaciones;

public class FormatoCorreoInvalidoException extends ValidacionException
{
    public FormatoCorreoInvalidoException()
    {
        super();
    }

    public FormatoCorreoInvalidoException(String mensaje)
    {
        super(mensaje);
    }
}