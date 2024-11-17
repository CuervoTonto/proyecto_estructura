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

    public void iniciarActividad(boolean duracionMinima)
    {
        Cola<Tarea> cola = tareas.clone();

        while (! cola.estaVacia()) {
            Tarea tarea = cola.desencolar();

            if (tarea.isOpcional() && duracionMinima) continue;

            // simula la realizacion de la tarea dado el tiempo de la misma
            try {
                Thread.sleep(tarea.getDuracion() * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int calcularDuracionMinima()
    {
        int acumulado = 0;
        Cola<Tarea> tareas = this.tareas.clone();

        while (! tareas.estaVacia()) {
            Tarea tarea = tareas.desencolar();

            if (! tarea.isOpcional()) acumulado += tarea.getDuracion();
        }
        
        return acumulado;
    }

    public int  calcularDuracionMaxima()
    {
        int acumulado = 0;
        Cola<Tarea> tareas = this.tareas.clone();

        while (! tareas.estaVacia()) {
            acumulado += tareas.desencolar().getDuracion();
        }

        return acumulado;
    }

    public void registrarTarea(Tarea tarea)
    {
        registrarTarea(tareas.longtiud(), tarea);
    }

    public void registrarTarea(int indice, Tarea tarea)
    {
        // verificar rango
        if (indice < 0 || indice > tareas.longtiud()) {
            throw new IndexOutOfBoundsException();
        }

        if (tareas.estaVacia()) {
            tareas.encolar(tarea);
        } else {
            int indiceActual = 0;
            int numElementos = tareas.longtiud();
            Cola<Tarea> aux = new Cola<>();
            Tarea ultimoIns = null;

            // mientras buscamos elemento que corresponde al indice
            // desencolamos de la principal y guardamos en la auxiliar
            while (indiceActual <= numElementos) {
                // si este es el punto en el que queremos insertar
                if (indiceActual == indice) {
                    // miramos si el elemento que queda a la izquierda del
                    // que queremos insertar es opcional
                    boolean l = ultimoIns == null ? false : ultimoIns.isOpcional();
                    // los mismo para el elemento de la derecha
                    boolean r = tareas.estaVacia() ? false : tareas.elemento().isOpcional();

                    if (l || r) { // si alguno de sus adyacentes es opcional
                        // si la tarea no es opcional insertamos
                        if (! tarea.isOpcional()) aux.encolar(tarea);
                    } else {
                        aux.encolar(tarea);
                    }
                }

                if (! tareas.estaVacia()) {
                    aux.encolar(ultimoIns = tareas.desencolar());
                }

                indiceActual++;
            }

            // volvemos a encolar los elementos desencolados para que no se pierdan
            while (! aux.estaVacia()) tareas.encolar(aux.desencolar());
        }
    }

    public Cola<Tarea> buscarTareas(String descripcion, Boolean opcional)
    {
        Cola<Tarea> tareas = this.tareas.clone();
        Cola<Tarea> resultado = new Cola<>();

        while (! tareas.estaVacia()) {
            Tarea tarea = tareas.desencolar();

            if (opcional != null) {
                if (opcional.booleanValue() != tarea.isOpcional()) {
                    continue;
                }
            }

            if (descripcion != null) {
                if (tarea.getDescripcion().matches("(?i).*" + descripcion + ".*")) {
                    resultado.encolar(tarea);
                }
            } else {
                resultado.encolar(tarea);
            }
        }

        return resultado;
    }

    public void removerTarea(Tarea tarea)
    {
        Cola<Tarea> aux = new Cola<>();

        while (! tareas.estaVacia()) {
            Tarea actual = tareas.desencolar();
            if (tarea != actual) aux.encolar(actual);
        }

        while (! aux.estaVacia()) tareas.encolar(aux.desencolar());
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
        return tareas.clone();
    }

    public void setTareas(Cola<Tarea> tareas)
    {
        this.tareas = tareas;
    }
}