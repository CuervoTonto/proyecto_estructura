package co.edu.uniquindio.modelos;

import co.edu.uniquindio.estructuras.listas.ListaSimple;

public class Proceso
{
    private String id;
    private String nombre;
    private ListaSimple<Actividad> actividades;

    public Proceso()
    {
        this.actividades = new ListaSimple<>();
    }

    public Proceso(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
        this.actividades = new ListaSimple<>();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public ListaSimple<Actividad> getActividades()
    {
        return actividades;
    }

    public void setActividades(ListaSimple<Actividad> actividades)
    {
        this.actividades = actividades;
    }
}