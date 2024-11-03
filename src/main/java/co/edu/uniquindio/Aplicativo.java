package co.edu.uniquindio;

import java.util.Optional;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;

public class Aplicativo
{
    private ListaSimple<Proceso> procesos;

    public Aplicativo()
    {
        this.procesos = new ListaSimple<>();
    }

    /**
     * obtiene los procesos que contengan una actividad
     * 
     * @param nombre nombre de la actividad
     * 
     * @return procesos con la actividad
     */
    public ListaSimple<Proceso> buscarProcesoActividad(String nombre)
    {
        ListaSimple<Proceso> activiades = new ListaSimple<>();

        for (Proceso proceso : procesos) {
            Optional<Actividad> act = proceso.obtenerActividad(nombre);
            act.ifPresent((a) -> activiades.agregar(proceso));
        }

        return activiades;
    }

    /**
     * busca en los procesos obtiene las actividades que
     * coincidan con un nombre
     * 
     * @param nombre nombre de la actividad
     * 
     * @return actividades con el nombre dado
     */
    public ListaSimple<Actividad> buscarActividad(String nombre)
    {
        ListaSimple<Actividad> activiades = new ListaSimple<>();

        for (Proceso proceso : procesos) {
            Optional<Actividad> act = proceso.obtenerActividad(nombre);
            act.ifPresent((a) -> activiades.agregar(a));
        }

        return activiades;
    }

    public ListaSimple<Proceso> getProcesos()
    {
        return procesos;
    }

    public void setProcesos(ListaSimple<Proceso> procesos)
    {
        this.procesos = procesos;
    }
}