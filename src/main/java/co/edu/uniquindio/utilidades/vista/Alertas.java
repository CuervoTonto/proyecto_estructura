package co.edu.uniquindio.utilidades.vista;

import javafx.scene.control.Alert;

final public class Alertas
{
    private Alertas() {}

    public static void informacion(String titulo, String contenido)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public static void error(String titulo, String contenido)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(" Error!!");
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}