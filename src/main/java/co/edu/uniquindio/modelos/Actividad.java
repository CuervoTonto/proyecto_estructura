package co.edu.uniquindio.modelos;

import java.util.NoSuchElementException;
import java.util.Optional;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.excepciones.tareas.TareaOpcionalException;

public class Actividad
{
    private String nombre;
    private String descripcion;
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
        this.descripcion = descripcion;
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

    public Optional<Tarea> encontrarTarea(int posicion)
    {
        if (posicion < 0 || posicion >= tareas.longitud())  {
            return Optional.empty();
        }

        Cola<Tarea> aux = tareas.clone();

        // recorremos hasta dejar el que queremos en la cima
        for (int i = 0; i < posicion; i++) aux.desencolar();

        // devolvemos el elemento en la cima
        return Optional.of(aux.desencolar());
    }

    /**
     * encuentra la posicion de una tarea en la cola completa
     * 
     * @param tarea instancia en la cola (tiene que ser la misma instancia no una igual)
     * 
     * @return posicion en lista o -1 si no esta
     */
    public int posicionTarea(Tarea tarea)
    {
        Cola<Tarea> aux = tareas.clone();

        for (int i = 0; i < tareas.longitud(); i++) {
            if (aux.desencolar() == tarea) return i;
        }

        return -1;
    }

    public void registrarTarea(Tarea tarea) throws TareaOpcionalException
    {
        registrarTarea(tareas.longitud(), tarea);
    }

    public void registrarTarea(int indice, Tarea tarea) throws TareaOpcionalException
    {
        // verificar rango
        if (indice < 0 || indice > tareas.longitud()) {
            throw new IndexOutOfBoundsException();
        }

        if (tareas.estaVacia()) {
            tareas.encolar(tarea);
        } else {
            int indiceActual = 0;
            int numElementos = tareas.longitud();
            Cola<Tarea> aux = new Cola<>();
            Tarea ultimoIns = null;
            boolean opcionalSeguido = false;

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

                    // si al insertarla habra 2 seguidos no insertarla
                    // caso contrario insertarla
                    if ((l || r) && tarea.isOpcional()) {
                        opcionalSeguido = true;
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

            if (opcionalSeguido) {
                throw new TareaOpcionalException();
            }
        }
    }

    public Cola<Tarea> buscarTareas(String descripcion, Boolean opcional)
    {
        Cola<Tarea> tareas = this.tareas.clone();
        Cola<Tarea> resultado = new Cola<>();

        while (! tareas.estaVacia()) {
            Tarea tarea = tareas.desencolar();

            if (opcional != null) {
                if (opcional.booleanValue() != tarea.isOpcional()) continue;
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

    public void actualizarTarea(int posicion, Tarea nueva) throws TareaOpcionalException
    {
        // verificar rango
        if (posicion < 0 || posicion > tareas.longitud()) {
            throw new IndexOutOfBoundsException();
        }

        if (tareas.estaVacia()) {
            throw new NoSuchElementException();
        } else {
            Cola<Tarea> aux = new Cola<>(); // cola axuliar
            Tarea objetivo = null; // la tarea a actualizar
            boolean anterior = false; // valor del opcional de la tarea anterior a la objetivo
            boolean siguiente = false; // valor del opcional de la tarea anterior a la objetivo
            int indiceActual = 0; // contador de posicion

            while (! tareas.estaVacia()) {
                Tarea actual = tareas.desencolar();
                aux.encolar(actual);

                // si es la tarea opccional
                if (indiceActual == posicion - 1) {
                    anterior = actual.isOpcional();
                // si es la tarea siguiente
                } else if (indiceActual == posicion + 1) {
                    siguiente = actual.isOpcional();
                // si es la tarea objetivo
                } else if (indiceActual == posicion) {
                    objetivo = actual;
                }

                indiceActual++;
            }

            // devolvemos las tareas desde la cola axuliar
            while (! aux.estaVacia()) tareas.encolar(aux.desencolar());

            // comprobar que el cambio de la propiedad opcional no deje dos
            // tareas opcionales seguidas
            if ((anterior || siguiente) && nueva.isOpcional()) {
                throw new TareaOpcionalException();
            }

            // actualizar la informacion
            objetivo.setDuracion(nueva.getDuracion());
            objetivo.setOpcional(nueva.isOpcional());
            objetivo.setDescripcion(nueva.getDescripcion());
        }
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

    public void removerTarea(int indice)
    {
        Cola<Tarea> aux = new Cola<>();
        int indiceActual = 0;

        while (! tareas.estaVacia()) {
            Tarea tarea = tareas.desencolar();
            if (indiceActual != indice) aux.encolar(tarea);
            indiceActual++;
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

    public Cola<Tarea> getTareas()
    {
        return tareas.clone();
    }

    public void setTareas(Cola<Tarea> tareas)
    {
        this.tareas = tareas;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Actividad other = (Actividad) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (opcional != other.opcional)
            return false;
        if (tareas == null) {
            if (other.tareas != null)
                return false;
        } else if (!tareas.equals(other.tareas))
            return false;
        return true;
    }
}