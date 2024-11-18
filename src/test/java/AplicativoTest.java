import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.excepciones.procesos.ProcesoRegistradoException;
import co.edu.uniquindio.modelos.Proceso;

public class AplicativoTest
{
    @Test
    public void crearProceso()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba");

        assertDoesNotThrow(() -> app.registrarProceso(p1));
        assertEquals(1, app.getProcesos().getLongitud());
    }

    @Test
    public void registrarMismoProceso()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");

        assertDoesNotThrow(() -> app.registrarProceso(p1));
        assertThrows(ProcesoRegistradoException.class, () -> app.registrarProceso(p1));

        assertEquals(1, app.getProcesos().getLongitud());
    }

    @Test
    public void registrarProcesosMismoId()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        Proceso p2 = new Proceso("123", "Prueba 2");

        assertDoesNotThrow(() -> app.registrarProceso(p1));
        assertThrows(ProcesoRegistradoException.class, () -> app.registrarProceso(p2));

        assertEquals(1, app.getProcesos().getLongitud());
    }

    @Test
    public void registrarProcesosMismoNombre()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        Proceso p2 = new Proceso("ab3", "Prueba 1");

        assertDoesNotThrow(() -> app.registrarProceso(p1));
        assertDoesNotThrow(() -> app.registrarProceso(p2));

        assertEquals(2, app.getProcesos().getLongitud());
    }

    @Test
    public void encontrarProceso()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        try { app.registrarProceso(p1); } catch (Exception e) {}

        assertTrue(app.encontrarProceso("123").isPresent());
        assertEquals(p1, app.encontrarProceso("123").get());
    }

    @Test
    public void removerProceso()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        Proceso p2 = new Proceso("abc", "Prueba 2");

        try {
            app.registrarProceso(p1);
            app.registrarProceso(p2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> app.removerProceso("abc"));
        assertEquals(1, app.getProcesos().getLongitud());
        assertTrue(app.encontrarProceso("abc").isEmpty());
    }

    @Test
    public void removerProcesoInexistente()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        Proceso p2 = new Proceso("abc", "Prueba 2");

        try {
            app.registrarProceso(p1);
            app.registrarProceso(p2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> app.removerProceso("1a2b3c"));
        assertEquals(2, app.getProcesos().getLongitud());
        assertTrue(app.encontrarProceso("abc").isPresent());
        assertTrue(app.encontrarProceso("123").isPresent());
    }

    @Test
    public void actualizarProceso()
    {
        Aplicativo app = new Aplicativo();
        Proceso p1 = new Proceso("123", "Prueba 1");
        Proceso p2 = new Proceso("123", "Nueva prueba 1");

        try {
            app.registrarProceso(p1);
            app.registrarProceso(p2);
        } catch (Exception e) {}

        assertDoesNotThrow(() -> app.actualizarProceso("123", p2));
        assertTrue(app.encontrarProceso("123").isPresent());
        assertTrue(app.encontrarProceso("123").get().equals(p2));
    }
}