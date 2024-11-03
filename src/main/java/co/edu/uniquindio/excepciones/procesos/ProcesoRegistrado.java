package co.edu.uniquindio.excepciones.procesos;

public class ProcesoRegistrado extends Exception
{
    public ProcesoRegistrado() {}

    public ProcesoRegistrado(String mensaje)
    {
        super(mensaje);
    }
}