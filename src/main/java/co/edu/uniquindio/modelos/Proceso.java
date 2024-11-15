package co.edu.uniquindio.modelos;

import java.util.Optional;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.estructuras.listas.ListaDobleCircular;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;

public class Proceso
{
    private String id;
    private String nombre;
    private ListaDobleCircular<Actividad> actividades;
    private String ultimaRegistrada;

    public Proceso()
    {
        this.actividades = new ListaDobleCircular<>();
    }

    public Proceso(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
        this.actividades = new ListaDobleCircular<>();
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

    public ListaSimple<Actividad> buscarActividad(String nombre, String descripcion, Boolean opcional)
    {
        ListaSimple<Actividad> resultado = new ListaSimple<>();

        for (Actividad actividad : this.actividades) {
            // si se esta filtrando por opcionales y la propiedad
            // de la actividad no coincide pasar a la siguiente
            if (opcional != null) {
                if (opcional.booleanValue() != actividad.isOpcional()) {
                    continue;
                }
            }

            // si se esta filtrando por descripciones y la propiedad
            // de la actividad no coincide pasar a la siguiente
            if (descripcion != null) {
                if (actividad.getDescripicion() != null) {
                    if (! actividad.getDescripicion().matches("(?i).*" + descripcion + ".*")) {
                        continue;
                    }
                } else {
                    continue;
                }
            }

            // si se esta filtrando por nombres y la propiedad
            // de la actividad no coincide pasar a la siguiente
            // en caso de no buscar por nombre agregar como resultado

            // tener en cuenta que si esta validando esto es debido a que
            // tanto opcional como descripcion coindicen con los criterios
            if (nombre != null) {
                if (actividad.getNombre() == null) continue;

                if (actividad.getNombre().matches("(?i).*"+nombre+".*")) {
                    resultado.agregar(actividad);
                }
            } else {
                resultado.agregar(actividad);
            }
        }

        return resultado;
    }

    public boolean contieneActividad(String nombre, String descripcion, Boolean opcional)
    {
        for (Actividad actividad : this.actividades) {
            if (opcional != null) {
                if (opcional.booleanValue() != actividad.isOpcional()) {
                    continue;
                }
            }

            if (descripcion != null) {
                if (actividad.getDescripicion() != null) {
                    if (! actividad.getDescripicion().matches("(?i).*" + descripcion + ".*")) {
                        continue;
                    }
                } else {
                    continue;
                }
            }

            if (nombre != null) {
                if (actividad.getNombre() == null) continue;

                if (actividad.getNombre().matches("(?i).*" + nombre + ".*")) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
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

    public ListaDobleCircular<Actividad> getActividades()
    {
        return actividades;
    }

    public void setActividades(ListaDobleCircular<Actividad> actividades)
    {
        this.actividades = actividades;
    }

    public String getUltimaRegistrada()
    {
        return ultimaRegistrada;
    }

    public void setUltimaRegistrada(String ultimaRegistrada)
    {
        this.ultimaRegistrada = ultimaRegistrada;
    }
}