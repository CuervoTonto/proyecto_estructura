package co.edu.uniquindio.modelos;

import java.util.Iterator;
import java.util.Optional;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.estructuras.listas.ListaDobleCircular;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;
import co.edu.uniquindio.excepciones.procesos.ActividadNoEncontradaException;
import co.edu.uniquindio.utilidades.UtilidadILista;

public class Proceso
{
    private String id;
    private String nombre;
    private ListaDobleCircular<Actividad> actividades;
    private String ultimaRegistrada;
    private Boolean iniciado;

    public Proceso()
    {
        this(null, null);
    }

    public Proceso(String id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
        this.actividades = new ListaDobleCircular<>();
        this.iniciado = false;
    }

    public void iniciarProceso(String actividad, boolean duracionMinima) throws ActividadNoEncontradaException
    {
        if (iniciado) return;

        iniciado = true;
        Optional<Actividad> inicio = encontrarActividad(actividad);

        if (inicio.isPresent()) {
            new Thread(() -> {
                Iterator<Actividad> it = actividades.iterator();
                Actividad actual = null;

                while ((actual = it.next()) != inicio.get()) {}

                for (int i = 0; i < actividades.getLongitud(); i++) {
                    if (duracionMinima && actual.isOpcional()) continue;

                    actual.iniciarActividad(duracionMinima);
                    actual = it.next();
                }

                iniciado = false;
            }).start();
        } else {
            throw new ActividadNoEncontradaException();
        }
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
        encontrarActividad(act1).ifPresent((a) -> {
            encontrarActividad(act2).ifPresent((b) -> {
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

    public void registrarActividad(String predecesora, Actividad actividad) throws ActividadRegistradaException, ActividadNoEncontradaException
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

        if (indicePredecesor == -1) {
            throw new ActividadNoEncontradaException();
        }

        actividades.agregar(indicePredecesor + 1, actividad);
        ultimaRegistrada = actividad.getNombre();
    }

    public void registrarActividadDesdeAnterior(Actividad actividad) throws ActividadRegistradaException, ActividadNoEncontradaException
    {
        if (ultimaRegistrada != null) {
            registrarActividad(ultimaRegistrada, actividad);
        } else {
            registrarActividad(actividad);
        }
    }

    public Optional<Actividad> encontrarActividad(String nombre)
    {
        for (Actividad actividad : actividades) {
            if (actividad.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(actividad);
            }
        }

        return Optional.empty();
    }

    public ListaDobleCircular<Actividad> buscarActividad(String nombre, String descripcion, Boolean opcional)
    {
        ListaDobleCircular<Actividad> resultado = new ListaDobleCircular<>();

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
                if (actividad.getDescripcion() != null) {
                    if (! actividad.getDescripcion().matches("(?i).*" + descripcion + ".*")) {
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
            // filtrar actividades opcionales (si se selecciona)
            if (opcional != null) {
                if (opcional.booleanValue() != actividad.isOpcional()) {
                    continue;
                }
            }

            // filtrar actividades por descripcion
            if (descripcion != null) {
                if (! actividad.getDescripcion().matches("(?i).*" + descripcion + ".*")) {
                    continue;
                }
            }

            // filtrar actividades por nombre
            // este es el ultimo filtro, aqui se decide si se retorno
            if (nombre != null) {
                if (actividad.getNombre().matches("(?i).*" + nombre + ".*")) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    public void removerActividad(Actividad actividad)
    {
        Iterator<Actividad> it = actividades.iterator();
        int indice = 0;
        boolean encontrado = false;

        while (it.hasNext() && ! encontrado) {
            if (it.next() == actividad) {
                encontrado = true;
            } else {
                indice++;
            }
        }

        if (encontrado) actividades.remover(indice);
    }

    public void removerActividad(String actividad)
    {
        Iterator<Actividad> it = actividades.iterator();
        boolean encontrado = false;
        int indice = 0;

        while (it.hasNext() && ! encontrado) {
            if (it.next().getNombre().equalsIgnoreCase(actividad)) {
                encontrado = true;
            } else {
                indice++;
            }
        }

        if (encontrado) actividades.remover(indice);
    }

    /**
     * actualiza una actividad del proceso
     * 
     * @param original nombre de la actividad
     * @param nuevo nuevos datos de la actividad
     * 
     * @throws ActividadRegistradaException si el nuevo nombre ya ha sido tomado
     */
    public boolean actualizarActividad(String original, Actividad nuevo) throws ActividadRegistradaException
    {
        for (Actividad actividad : actividades) {
            if (actividad.getNombre().equalsIgnoreCase(original)) {
                if (! nuevo.getNombre().equalsIgnoreCase(original)) {
                    if (encontrarActividad(nuevo.getNombre()).isPresent()) {
                        throw new ActividadRegistradaException();
                    }

                    actividad.setNombre(nuevo.getNombre());
                }

                // reemplazar/actualizar la informacion
                actividad.setOpcional(nuevo.isOpcional());
                actividad.setDescripcion(nuevo.getDescripcion());
                actividad.setTareas(nuevo.getTareas());

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

    public Cola<Tarea> buscarTareasEnActividad(String actividad, String descripcion, Boolean opcional) throws ActividadNoEncontradaException
    {
        Optional<Actividad> act = encontrarActividad(actividad);

        if (act.isEmpty()) throw new ActividadNoEncontradaException();

        return act.get().buscarTareas(descripcion, opcional);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Proceso other = (Proceso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (actividades == null) {
            if (other.actividades != null)
                return false;
        } else if (! UtilidadILista.iguales(actividades, other.actividades))
            return false;
        if (ultimaRegistrada == null) {
            if (other.ultimaRegistrada != null)
                return false;
        } else if (!ultimaRegistrada.equals(other.ultimaRegistrada))
            return false;
        if (iniciado == null) {
            if (other.iniciado != null)
                return false;
        } else if (!iniciado.equals(other.iniciado))
            return false;
        return true;
    }

    public Boolean getIniciado() {
        return iniciado;
    }

    public void setIniciado(Boolean iniciado) {
        this.iniciado = iniciado;
    }
}