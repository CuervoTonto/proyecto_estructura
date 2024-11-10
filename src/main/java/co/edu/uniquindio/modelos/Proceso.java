package co.edu.uniquindio.modelos;

import java.util.Optional;

import co.edu.uniquindio.estructuras.colas.Cola;
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

    public int calcularDuracionMinima()
    {
        int acumulado = 0;

        for (Actividad actividad : actividades) {
            if (! actividad.isOpcional()) {
                acumulado += actividad.calcularDuracionMinima();
            }
        }

        return acumulado;
    }

    public int calcularDuracionMaxima()
    {
        int acumulado = 0;

        for (Actividad actividad : actividades) {
            acumulado += actividad.calcularDuracionMaxima();
        }

        return acumulado;
    }

    /**
     * intercambia el nombre de 2 actividades
     * 
     * @param act1 nombre de la primera actividad
     * @param act2 nombre de la segunda actividad
     */
    public void intercambiarActividades(String act1, String act2)
    {
        obtenerActividad(act1).ifPresent((a) -> {
            obtenerActividad(act2).ifPresent((b) -> {
                String temp = a.getNombre();
                a.setNombre(b.getNombre());
                b.setNombre(temp);
            });
        });
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

    public Cola<Tarea> buscarTareas(String descripcion, Boolean opcional)
    {
        Cola<Tarea> tareas = new Cola<>();

        for (Actividad actividad : actividades) {
            Cola<Tarea> coincidencias = actividad.buscarTareas(descripcion, opcional);

            while (! coincidencias.estaVacia()) {
                tareas.encolar(coincidencias.desencolar());
            }
        }

        return tareas;
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