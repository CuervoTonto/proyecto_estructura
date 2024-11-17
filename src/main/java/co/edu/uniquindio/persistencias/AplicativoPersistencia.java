package co.edu.uniquindio.persistencias;

import java.io.File;
import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.utilidades.Persistencia;

public class AplicativoPersistencia
{
    private String path = "resources/repositories/aplicativo.xml";

    public AplicativoPersistencia() throws IOException
    {
        crearSiNoExiste();
    }

    public Aplicativo obtener() throws IOException
    {
        return (Aplicativo) Persistencia.leerXML(path);
    }

    public void guardar(Aplicativo app) throws IOException
    {
        if (app == null) app = new Aplicativo();

        Persistencia.escribirXML(path, app);
    }

    private void crearSiNoExiste() throws IOException
    {
        File file = new File(path);

        if (! file.exists()) {
            file.getParentFile().mkdirs();
            guardar(null);
        }
    }
}