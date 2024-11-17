package co.edu.uniquindio.persistencias;

import java.io.File;
import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.utilidades.Persistencia;

public class AplicativoPersistencia
{
    private String path = "resources/repositories/aplicativo.xml";

    public AplicativoPersistencia()
    {
        try {
            crearSiNoExiste();
        } catch (Exception e) {
            throw new RuntimeException("Error en la creacion del aplicativo");
        }
    }

    public Aplicativo obtener()
    {
        try {
            return (Aplicativo) Persistencia.leerXML(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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