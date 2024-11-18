import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.modelos.Tarea;
import co.edu.uniquindio.utilidades.UtilidadILista;

public class ProcesoTest
{
    @Test
    public void calcularDuracionMinima()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", true, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);

            act1.registrarTarea(new Tarea(3, false, "T#1"));
            act1.registrarTarea(new Tarea(7, false, "T#2"));
            act2.registrarTarea(new Tarea(3, true, "T#3"));
            act2.registrarTarea(new Tarea(2, false, "T#4"));
        } catch (Exception e) {}

        assertEquals(2, p.calcularDuracionMinima());
    }

    @Test
    public void calcularDuracionMaxima()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", true, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);

            act1.registrarTarea(new Tarea(3, false, "T#1"));
            act1.registrarTarea(new Tarea(7, false, "T#2"));
            act2.registrarTarea(new Tarea(3, true, "T#3"));
            act2.registrarTarea(new Tarea(2, false, "T#4"));
        } catch (Exception e) {}

        assertEquals(15, p.calcularDuracionMaxima());
    }

    @Test
    public void crearActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");

        assertDoesNotThrow(() -> p.registrarActividad(act1));
        assertDoesNotThrow(() -> p.registrarActividad(act2));
        assertEquals(2, p.getActividades().getLongitud());
    }

    @Test
    public void crearActividadDespuesDePredecesora()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Actividad act3 = new Actividad("Act #3", false, "#3");
        Actividad act4 = new Actividad("Act #4", true, "#4");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad(act3);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> p.registrarActividad("Act #1", act4));
        assertEquals(4, p.getActividades().getLongitud());
        assertTrue(p.getActividades().obtener(1).equals(act4));
    }

    @Test
    public void crearActividadDespuesUltimaInsertada()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Actividad act3 = new Actividad("Act #3", false, "#3");
        Actividad act4 = new Actividad("Act #4", true, "#4");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad("Act #1", act3);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> p.registrarActividadDesdeAnterior(act4));
        assertEquals(4, p.getActividades().getLongitud());
        assertTrue(p.getActividades().obtener(2).equals(act4));
    }

    @Test
    public void crearActividadRepetida()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");

        assertDoesNotThrow(() -> p.registrarActividad(act1));
        assertThrows(ActividadRegistradaException.class, () -> p.registrarActividad(act1));
        assertEquals(1, p.getActividades().getLongitud());
    }

    @Test
    public void crearActividadesMismoNombre()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #1", true, "#2");

        assertDoesNotThrow(() -> p.registrarActividad(act1));
        assertThrows(ActividadRegistradaException.class, () -> p.registrarActividad(act2));
        assertEquals(1, p.getActividades().getLongitud());
    }

    @Test
    public void encontrarActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", true, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
        } catch (Exception e) {}

        assertTrue(p.encontrarActividad("Act #1").isPresent());
        assertTrue(p.encontrarActividad("Act #2").isPresent());
    }

    @Test
    public void removerActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> p.removerActividad("Act #1"));
        assertEquals(1, p.getActividades().getLongitud());
    }

    @Test
    public void removerActividadInexistente()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> p.removerActividad("Act #23"));
        assertEquals(2, p.getActividades().getLongitud());
    }

    @Test
    public void actualizarActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #1.0", true, "Reemplazo");

        try {
            p.registrarActividad(act1);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> p.actualizarActividad("act #1", act2));
        assertEquals(1, p.getActividades().getLongitud());
        assertTrue(p.encontrarActividad("Act #1.0").isPresent());
        assertTrue(p.encontrarActividad("Act #1.0").get().equals(act2));
    }

    @Test
    public void buscarActividadesOpcionales()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Actividad act3 = new Actividad("Act #3", false, "#3");
        Actividad act4 = new Actividad("Act #4", true, "#4");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad(act3);
            p.registrarActividad(act4);
        } catch (Exception e) {}

        ListaSimple<Actividad> esperado = new ListaSimple<>();
        esperado.agregar(act4);

        assertTrue(UtilidadILista.iguales(
            esperado,
            p.buscarActividad(null, null, true)
        ));
    }

    @Test
    public void buscarActividadesPorNombre()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "Tipo A ... abc");
        Actividad act2 = new Actividad("Prueba #2", false, "Tipo B ... 123");
        Actividad act3 = new Actividad("Act #3", false, "Tipo A ... asd");
        Actividad act4 = new Actividad("Prueba #4", true, "Tipo A");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad(act3);
            p.registrarActividad(act4);
        } catch (Exception e) {}

        ListaSimple<Actividad> esperado = new ListaSimple<>();
        esperado.agregar(act1);
        esperado.agregar(act3);

        assertTrue(UtilidadILista.iguales(
            esperado,
            p.buscarActividad("act #", null, null)
        ));
    }

    @Test
    public void buscarActividadesPorDescripcion()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "Tipo A ... abc");
        Actividad act2 = new Actividad("Prueba #2", false, "Tipo B ... 123");
        Actividad act3 = new Actividad("Act #3", false, "Tipo A ... asd");
        Actividad act4 = new Actividad("Prueba #4", true, "Tipo A");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad(act3);
            p.registrarActividad(act4);
        } catch (Exception e) {}

        ListaSimple<Actividad> esperado = new ListaSimple<>();
        esperado.agregar(act1);
        esperado.agregar(act2);
        esperado.agregar(act3);

        assertTrue(UtilidadILista.iguales(
            esperado,
            p.buscarActividad(null, "\\.\\.\\.", null)
        ));
    }

    @Test
    public void buscarActividadCombinacion()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", false, "Tipo A ... abc");
        Actividad act2 = new Actividad("Prueba #2", false, "Tipo B ... 123");
        Actividad act3 = new Actividad("Act #3", false, "Tipo A ... asd");
        Actividad act4 = new Actividad("Prueba #4", true, "Tipo B");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
            p.registrarActividad(act3);
            p.registrarActividad(act4);
        } catch (Exception e) {}

        ListaSimple<Actividad> esperado = new ListaSimple<>();
        esperado.agregar(act4);

        assertTrue(UtilidadILista.iguales(
            esperado,
            p.buscarActividad("prueba", "b",  true)
        ));
    }

    @Test
    public void intercambiarActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Preparar cafe", false, "#1");
        Actividad act2 = new Actividad("Preparar huevos", true, "#2");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);
        } catch (Exception e) {}

        p.intercambiarActividades("preparar cafe", "preparar huevos");

        assertEquals(2, p.getActividades().getLongitud());
        assertEquals(act1, p.encontrarActividad("Preparar huevos").get());
        assertEquals(act2, p.encontrarActividad("Preparar cafe").get());
    }

    @Test
    public void buscarTareasOpcionalesEnProceso()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", true, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Tarea t1 = new Tarea(3, true, "Preparar ... ");
        Tarea t2 = new Tarea(7, false, "Hacer ...");
        Tarea t3 = new Tarea(2, false, "Realizar ...");
        Tarea t4 = new Tarea(5, false, "Preparar ...");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);

            act1.registrarTarea(t1);
            act1.registrarTarea(t2);
            act2.registrarTarea(t3);
            act2.registrarTarea(t4);
        } catch (Exception e) {}

        Cola<Tarea> resultado = new Cola<>();
        resultado.encolar(t1);

        assertEquals(resultado, p.buscarTareas(null, true));
    }

    @Test
    public void buscarTareasPorDescripcion()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", true, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Tarea t1 = new Tarea(3, true, "Preparar ... ");
        Tarea t2 = new Tarea(7, false, "Hacer ...");
        Tarea t3 = new Tarea(2, false, "Realizar ...");
        Tarea t4 = new Tarea(5, false, "Preparar ...");

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);

            act1.registrarTarea(t1);
            act1.registrarTarea(t2);
            act2.registrarTarea(t3);
            act2.registrarTarea(t4);
        } catch (Exception e) {}

        Cola<Tarea> resultado = new Cola<>();
        resultado.encolar(t1);
        resultado.encolar(t4);

        assertEquals(resultado, p.buscarTareas("preparar", null));
    }

    @Test
    public void buscarTareasOpcionalesEnActividad()
    {
        Proceso p = new Proceso("123", "Prueba 1");
        Actividad act1 = new Actividad("Act #1", true, "#1");
        Actividad act2 = new Actividad("Act #2", false, "#2");
        Tarea t1 = new Tarea(3, true, "Preparar ... ");
        Tarea t2 = new Tarea(7, false, "Hacer ...");
        Tarea t3 = new Tarea(2, false, "Realizar ...");
        Tarea t4 = new Tarea(5, false, "Preparar ...");
        Cola<Tarea> resultado = null;

        try {
            p.registrarActividad(act1);
            p.registrarActividad(act2);

            act1.registrarTarea(t1);
            act1.registrarTarea(t2);
            act2.registrarTarea(t3);
            act2.registrarTarea(t4);

            resultado = p.buscarTareasEnActividad("Act #1", null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cola<Tarea> esperado = new Cola<>();
        esperado.encolar(t1);

        assertEquals(esperado, resultado);
    }
}