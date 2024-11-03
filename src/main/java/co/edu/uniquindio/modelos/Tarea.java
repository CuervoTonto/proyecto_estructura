package co.edu.uniquindio.modelos;

public class Tarea
{
    private String descripcion;
    private boolean opcional;
    private int duracion;

    public Tarea() {}

    public Tarea(int duracion, boolean opcional, String descripcion)
    {
        this.duracion = duracion;
        this.opcional = opcional;
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public boolean isOpcional()
    {
        return opcional;
    }

    public void setOpcional(boolean opcional)
    {
        this.opcional = opcional;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion)
    {
        this.duracion = duracion;
    }
}