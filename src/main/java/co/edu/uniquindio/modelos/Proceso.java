package co.edu.uniquindio.modelos;

import java.util.Optional;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;

public class Proceso
{
    private String id;
    private String nombre;
    private ListaSimple<Actividad> actividades;
    private String ultimaRegistrada;

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

    public void registrarActividad(Actividad actividad) throws ActividadRegistradaException
    {
        for (Actividad item : actividades) {
            if (item.getNombre().equalsIgnoreCase(actividad.getNombre())) {
                throw new ActividadRegistradaException();
            }
        }

        actividades.agregar(actividad);
        ultimaRegistrada = actividad.getNombre();
    }

    public void registrarActividad(String predecesora, Actividad actividad) throws ActividadRegistradaException
    {
        int indicePredecesor = -1;
        int indiceActual = 0;

        for (Actividad item : actividades) {
            if (item.getNombre().equalsIgnoreCase(actividad.getNombre())) {
                throw new ActividadRegistradaException();
            }

            if (item.getNombre().equalsIgnoreCase(predecesora)) {
                indicePredecesor = indiceActual;
            }

            indiceActual++;
        }

        actividades.agregar(indicePredecesor, actividad);
        ultimaRegistrada = actividad.getNombre();
    }

    public void registrarActividadDesdeAnterior(Actividad actividad) throws ActividadRegistradaException
    {
        if (ultimaRegistrada != null) {
            registrarActividad(ultimaRegistrada, actividad);
        } else {
            registrarActividad(actividad);
        }
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