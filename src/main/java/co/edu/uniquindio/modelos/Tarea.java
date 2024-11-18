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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equalsIgnoreCase(other.descripcion))
            return false;
        if (opcional != other.opcional)
            return false;
        if (duracion != other.duracion)
            return false;
        return true;
    }

    
}