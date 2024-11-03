package co.edu.uniquindio.persistencias;

import java.io.File;
import java.io.IOException;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.procesos.ProcesoRegistrado;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Persistencia;

final public class ProcesoPersistencia
{
    private String path = "resources/persistencia/procesos.xml";

    private ProcesoPersistencia() {}

    public ListaSimple<Proceso> todos() throws IOException
    {
        crearSiNoExiste();
        Object leido = Persistencia.leerXML(path);
        @SuppressWarnings("unchecked")
        ListaSimple<Proceso> procesos = (ListaSimple<Proceso>) leido;

        return procesos;
    }

    public void sobreescribir(ListaSimple<Proceso> lista) throws IOException
    {
        if (lista == null) lista = new ListaSimple<>();

        Persistencia.escribirXML(path, lista);
    }

    public void registrar(Proceso proceso) throws IOException, ProcesoRegistrado
    {
        ListaSimple<Proceso> procesos = todos();

        for (Proceso item : procesos) {
            if (proceso.getId().equals(item.getId())) {
                throw new ProcesoRegistrado("El id ya ha sido tomado");
            }

            if (proceso.getNombre().equals(item.getNombre())) {
                throw new ProcesoRegistrado("El nombre ya ha sido tomado");
            }
        }

        procesos.agregar(proceso);
        sobreescribir(procesos);
    }

    private void crearSiNoExiste() throws IOException
    {
        File file = new File(path);

        if (! file.exists()) {
            file.getParentFile().mkdirs();
            sobreescribir(null);
        }
    }
}