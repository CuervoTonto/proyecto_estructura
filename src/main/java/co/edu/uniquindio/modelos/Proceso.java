package co.edu.uniquindio.modelos;

import java.util.Optional;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistrada;

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

    public void registrarActividad(Actividad actividad) throws ActividadRegistrada
    {
        for (Actividad item : actividades) {
            if (item.getNombre().equalsIgnoreCase(actividad.getNombre())) {
                throw new ActividadRegistrada();
            }
        }

        actividades.agregar(actividad);
    }

    public Optional<Actividad> obtenerActividad(String nombre)
    {
        for (Actividad actividad : actividades) {
            if (actividad.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(actividad);
            }
        }

        return Optional.empty();
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