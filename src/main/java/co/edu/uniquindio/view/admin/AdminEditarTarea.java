package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.tareas.TareaOpcionalException;
import co.edu.uniquindio.excepciones.validaciones.FormatoEnteroInvalidoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.modelos.Tarea;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminEditarTarea
{
    private int posicion;

    private Proceso proceso;

    private Actividad actividad;

    private Tarea tarea;

    @FXML
    private Button actualizarTareaBtn;

    @FXML
    private Button cancelarBtn;

    @FXML
    private CheckBox checkOpcional;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtDuracion;

    public AdminEditarTarea(Proceso proceso, Actividad actividad, int posicion) {
        this.proceso = proceso;
        this.actividad = actividad;
        this.tarea = actividad.encontrarTarea(posicion).get();
        this.posicion = posicion;
    }

    @FXML
    void initialize() {
        txtDescripcion.setText(tarea.getDescripcion());
        txtDuracion.setText(Integer.toString(tarea.getDuracion()));
        checkOpcional.setSelected(tarea.isOpcional());
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminVistaActividad",
            new AdminDetallesActividad(proceso, actividad)
        );
    }

    @FXML
    void actualizarTarea(ActionEvent event) throws IOException {
        actualizarTareaBtn.setDisable(true);

        if (validarCampos()) {
            try {
                actividad.actualizarTarea(
                    posicion,
                    new Tarea(
                        Integer.valueOf(txtDuracion.getText()),
                        checkOpcional.isSelected(),
                        txtDescripcion.getText()
                    )
                );

                Alertas.informacion("Tarea actualizada", "Se ha actualizado la informacion correctamente");
                Aplicativo.guardarAplicativo();
                cancelar(event);
            } catch (TareaOpcionalException e) {
                Alertas.error("Tarea opcional seguida", "No pueden haber 2 actividades opcionales seguidas");
            }
        }

        actualizarTareaBtn.setDisable(false);
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtDuracion.getText(), txtDescripcion.getText());
            Validador.validarEnteros(txtDuracion.getText());

            return true;
        } catch (FormatoEnteroInvalidoException e) {
            Alertas.error("Error", "El campo duracion debe ser numerico");
        } catch (ValidacionException e) {
            Alertas.error("Error", "ninguno de los campos puede estar en blanco");
        }

        return false;
    }
}