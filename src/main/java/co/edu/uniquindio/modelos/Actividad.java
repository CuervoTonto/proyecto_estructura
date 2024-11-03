package co.edu.uniquindio.modelos;

import co.edu.uniquindio.estructuras.colas.Cola;

public class Actividad
{
    private String nombre;
    private String descripicion;
    private boolean opcional;
    private Cola<Tarea> tareas;

    public Actividad()
    {
        this.tareas = new Cola<>();
    }

    public Actividad(String nombre, boolean opcional, String descripcion)
    {
        this.nombre = nombre;
        this.opcional = opcional;
        this.descripicion = descripcion;
        this.tareas = new Cola<>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripicion()
    {
        return descripicion;
    }

    public void setDescripicion(String descripicion)
    {
        this.descripicion = descripicion;
    }

    public boolean isOpcional()
    {
        return opcional;
    }

    public void setOpcional(boolean opcional)
    {
        this.opcional = opcional;
    }

    public Cola<Tarea> getTareas()
    {
        return tareas;
    }

    public void setTareas(Cola<Tarea> tareas)
    {
        this.tareas = tareas;
    }
}