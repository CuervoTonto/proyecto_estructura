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

    public void agregarTarea(Tarea tarea)
    {
        agregarTarea(tareas.longtiud(), tarea);
    }

    public void agregarTarea(int indice, Tarea tarea)
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