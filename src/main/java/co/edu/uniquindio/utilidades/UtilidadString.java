package co.edu.uniquindio.utilidades;

public class UtilidadString
{
    private UtilidadString() {}

    public static boolean iContiene(String objetivo, String coincidencia)
    {
        if (coincidencia == null) return true;
        if (objetivo == null) return false;

        return objetivo.matches("(?i).*" + coincidencia + ".*");
    }

    public static boolean iNoContiene(String objetivo, String coincidencia)
    {
        if (coincidencia == null) return false;
        if (objetivo == null) return true;

        return ! objetivo.matches("(?i).*" + coincidencia + ".*");
    }
}