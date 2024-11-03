package co.edu.uniquindio.excepciones.autenticacion;

public class UsuarioClaveIncorrecta extends AutenticacionException
{
    public UsuarioClaveIncorrecta() {}

    public UsuarioClaveIncorrecta(String mensaje)
    {
        super(mensaje);
    }
}