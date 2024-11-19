package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminEditarActividad {

    private Proceso proceso;

    private Actividad actividad;

    @FXML
    private Button actulizarActBtn;

    @FXML
    private Button cancelarBtn;

    @FXML
    private CheckBox checkOpcional;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    public AdminEditarActividad(Proceso proceso, Actividad actividad) {
        this.proceso = proceso;
        this.actividad = actividad;
    }

    @FXML
    void initialize() {
        txtNombre.setText(actividad.getNombre());
        txtDescripcion.setText(actividad.getDescripcion());
        checkOpcional.setSelected(actividad.isOpcional());
    }

    @FXML
    void actualizarActividad(ActionEvent event) throws IOException {
        if (validarCampos()) {
            try {
                actualizar();
                Alertas.informacion("Exito", "Actividad actualizada con exito");
                cancelar(event);
            } catch (ActividadRegistradaException e) {
                Alertas.error("error", "El nombre de la actividad ya esta en uso");
            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminVistaProceso",
            new AdminDetallesProceso(proceso)
        );
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtNombre.getText(), txtNombre.getText());
            return true;
        } catch (ValidacionException e) {
            Alertas.error("Error!", "Todos los campos son requiridos");
        }

        return false;
    }

    private void actualizar() throws ActividadRegistradaException {
        proceso.actualizarActividad(
            actividad.getNombre(),
            new Actividad(txtNombre.getText(), checkOpcional.isSelected(), txtDescripcion.getText())
        );
    }
}