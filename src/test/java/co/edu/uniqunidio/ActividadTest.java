package co.edu.uniqunidio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.excepciones.tareas.TareaOpcionalException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Tarea;

public class ActividadTest
{
    @Test
    public void registrarTareas()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");

        assertDoesNotThrow(() -> actividad.registrarTarea(t1));
        assertDoesNotThrow(() -> actividad.registrarTarea(t2));
        assertEquals(2, actividad.getTareas().longitud());
    }

    @Test
    public void obtenerTareas()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
        } catch (Exception e) {}

        Cola<Tarea> tareas = actividad.getTareas();

        assertEquals(t1, tareas.desencolar());
        assertEquals(t2, tareas.desencolar());
    }

    @Test
    public void registrarTareaEnPosicion()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");
        Tarea t3 = new Tarea(2, false, "T#3");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
            actividad.registrarTarea(1, t3);
        } catch (Exception e) {}

        Cola<Tarea> tareas = actividad.getTareas();

        assertEquals(3, tareas.longitud());
        assertEquals(t1, tareas.desencolar());
        assertEquals(t3, tareas.desencolar());
        assertEquals(t2, tareas.desencolar());
    }

    @Test
    public void registrarTareaOpcionalSeguida()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");
        Tarea t3 = new Tarea(2, true, "T#3");

        assertDoesNotThrow(() -> actividad.registrarTarea(t1));
        assertDoesNotThrow(() -> actividad.registrarTarea(t2));
        // assertDoesNotThrow(() -> actividad.registrarTarea(t3));
        assertThrows(TareaOpcionalException.class, () -> actividad.registrarTarea(t3));
        assertEquals(2, actividad.getTareas().longitud());
    }

    @Test
    public void actualizarTarea()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");
        Tarea t3 = new Tarea(2, false, "T#2.1");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> actividad.actualizarTarea(0, t3));
        assertEquals(t3, actividad.getTareas().desencolar());
    }

    @Test
    public void removerTarea()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(2, true, "T#2");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> actividad.removerTarea(1));
        assertEquals(t1, actividad.getTareas().desencolar());
        assertDoesNotThrow(() -> actividad.removerTarea(0));
        assertEquals(0, actividad.getTareas().longitud());
    }

    @Test
    public void calcularTiempoMinimo()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");

        try {
            actividad.registrarTarea(new Tarea(3, false, "T#1"));
            actividad.registrarTarea(new Tarea(7, false, "T#2"));
            actividad.registrarTarea(new Tarea(3, true, "T#3"));
            actividad.registrarTarea(new Tarea(2, false, "T#4"));
        } catch (Exception e) {}

        assertEquals(12, actividad.calcularDuracionMinima());
    }

    @Test
    public void calcularTiempoMaximo()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");

        try {
            actividad.registrarTarea(new Tarea(3, false, "T#1"));
            actividad.registrarTarea(new Tarea(7, false, "T#2"));
            actividad.registrarTarea(new Tarea(3, true, "T#3"));
            actividad.registrarTarea(new Tarea(2, false, "T#4"));
        } catch (Exception e) {}

        assertEquals(15, actividad.calcularDuracionMaxima());
    }

    @Test
    public void buscarTareasOpcionales()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "T#1");
        Tarea t2 = new Tarea(7, false, "T#2");
        Tarea t3 = new Tarea(2, true, "T#3");
        Tarea t4 = new Tarea(5, false, "T#4");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
            actividad.registrarTarea(t3);
            actividad.registrarTarea(t4);
        } catch (Exception e) {}

        Cola<Tarea> esperado = new Cola<>();
        esperado.encolar(t3);

        assertTrue(esperado.equals(actividad.buscarTareas(null, true)));
    }

    @Test
    public void buscarTareasPorDescripcion()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, false, "Preparar ... ");
        Tarea t2 = new Tarea(7, false, "Hacer ...");
        Tarea t3 = new Tarea(2, true, "Realizar ...");
        Tarea t4 = new Tarea(5, false, "Preparar ...");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
            actividad.registrarTarea(t3);
            actividad.registrarTarea(t4);
        } catch (Exception e) {}

        Cola<Tarea> esperado = new Cola<>();
        esperado.encolar(t1);
        esperado.encolar(t4);

        assertTrue(esperado.equals(actividad.buscarTareas("preparar", null)));
    }

    @Test
    public void buscarTareasCombinacion()
    {
        Actividad actividad = new Actividad("Prueba", false, "actividad");
        Tarea t1 = new Tarea(3, true, "Preparar ... ");
        Tarea t2 = new Tarea(7, false, "Hacer ...");
        Tarea t3 = new Tarea(2, false, "Realizar ...");
        Tarea t4 = new Tarea(5, false, "Preparar ...");

        try {
            actividad.registrarTarea(t1);
            actividad.registrarTarea(t2);
            actividad.registrarTarea(t3);
            actividad.registrarTarea(t4);
        } catch (Exception e) {}

        Cola<Tarea> esperado = new Cola<>();
        esperado.encolar(t1);

        assertTrue(esperado.equals(actividad.buscarTareas("preparar", true)));
    }

}