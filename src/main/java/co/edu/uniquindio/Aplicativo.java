package co.edu.uniquindio;

import java.util.Iterator;
import java.util.Optional;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.procesos.ProcesoRegistradoException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.persistencias.AplicativoPersistencia;
import co.edu.uniquindio.utilidades.UtilidadILista;

public class Aplicativo
{
    private static Aplicativo instancia;
    private ListaSimple<Proceso> procesos;

    public Aplicativo()
    {
        this.procesos = new ListaSimple<>();
    }

    public static Aplicativo instancia()
    {
        if (instancia == null) {
            instancia = new AplicativoPersistencia().obtener();
        }

        return instancia;
    }

    public static void cargarAplicativo()
    {
        instancia = new AplicativoPersistencia().obtener();
    }

    public static void guardarAplicativo(Aplicativo aplicativo)
    {
        try {
            new AplicativoPersistencia().guardar(aplicativo);
            instancia = aplicativo;
        } catch (Exception e) {
            throw new RuntimeException("No se puedo guardar el aplicativo");
        }
    }

    public void registrarProceso(Proceso proceso) throws ProcesoRegistradoException
    {
        if (procesoExistente(proceso.getId())) {
            throw new ProcesoRegistradoException();
        }

        procesos.agregar(proceso);
    }

    public boolean procesoExistente(String id)
    {
        for (Proceso proceso : procesos) {
            if (proceso.getId().equals(id)) return true;
        }

        return false;
    }

    public Optional<Proceso> encontrarProceso(String id)
    {
        for (Proceso proceso : procesos) {
            if (proceso.getId().equals(id)) return Optional.of(proceso);
        }

        return Optional.empty();
    }

    /**
     * actualiza un proceso en el aplicativo
     * 
     * @param original id del proceso a actualizar
     * @param nuevo los nuevos datos (solo se toma el nombre)
     * 
     * @return proceso actualizado exitosamente
     */
    public boolean actualizarProceso(String original, Proceso nuevo)
    {
        for (Proceso proceso : procesos) {
            if (proceso.getId().equals(original)) {
                // reemplazando la informacion del antiguo con la del nuevo
                proceso.setNombre(nuevo.getNombre());
                proceso.setActividades(nuevo.getActividades());
                proceso.setUltimaRegistrada(nuevo.getUltimaRegistrada());

                return true;
            }
        }

        return false;
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
            Optional<Actividad> act = proceso.encontrarActividad(nombre);
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
            Optional<Actividad> act = proceso.encontrarActividad(nombre);
            act.ifPresent((a) -> activiades.agregar(a));
        }

        return activiades;
    }

    /**
     * obtiene los procesos que contengan almenos una actividad que coincida
     * 
     * @param nombre nombre de la actividad o {@code null} para cualquiera nombre 
     * @param descripcion descripcion de la actividad o {@codea null} para cualquiera
     * @param opcional la tarea es opcional o {@code null} para cualquieira
     * @return
     */
    public ListaSimple<Proceso> buscarProcesoConActividad(String nombre, String descripcion, Boolean opcional)
    {
        ListaSimple<Proceso> procesos = new ListaSimple<>();

        for (Proceso proceso : this.procesos) {
            if (proceso.contieneActividad(nombre, descripcion, opcional)) {
                procesos.agregar(proceso);
            }
        }

        return procesos;
    }

    public void removerProceso(String id)
    {
        Iterator<Proceso> it = procesos.iterator();
        int indice = 0;
        boolean encontrado = false;

        while (it.hasNext() && ! encontrado) {
            if (it.next().getId().equals(id)) {
                encontrado = true;
            } else {
                indice++;
            }
        }

        if (encontrado) procesos.remover(indice);
    }

    public ListaSimple<Proceso> getProcesos()
    {
        return procesos;
    }

    public void setProcesos(ListaSimple<Proceso> procesos)
    {
        this.procesos = procesos;
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
        Aplicativo other = (Aplicativo) obj;
        if (procesos == null) {
            if (other.procesos != null)
                return false;
        } else if (! UtilidadILista.iguales(procesos, other.procesos))
            return false;
        return true;
    }
}