package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.actividades.ActividadRegistradaException;
import co.edu.uniquindio.excepciones.procesos.ActividadNoEncontradaException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class AdminCrearActividad {

    private Proceso proceso;

    @FXML
    private Button cancelarBtn;

    @FXML
    private CheckBox checkOpcional;

    @FXML
    private ChoiceBox<Actividad> choicePredecesora;

    @FXML
    private Button crearActBtn;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    public AdminCrearActividad(Proceso proceso) {
        this.proceso = proceso;
    }

    @FXML
    void initialize() {
        ObservableList<Actividad> opciones = FXCollections.observableArrayList();

        for (Actividad act : proceso.getActividades()) {
            opciones.add(act);
        }

        choicePredecesora.setItems(opciones);

        choicePredecesora.setConverter(new StringConverter<Actividad>() {
            @Override
            public String toString(Actividad object) {
                return object == null
                    ? "Ninguna (al final de la lista)"
                    : ((Actividad) object).getNombre();
            }

            @Override
            public Actividad fromString(String string) { return null; }
        });
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminVistaProceso",
            new AdminDetallesProceso(proceso)
        );
    }

    @FXML
    void crearActividad(ActionEvent event) throws IOException {
        crearActBtn.setDisable(true);

        if (validarCampos()) {
            try {
                registrarActividad();
                Alertas.informacion("Exito", "se ha creado la actividad correctamente");
                cancelar(event);
            } catch (ActividadRegistradaException e) {
                Alertas.error("Error", "La actividad ya se encuentra registrada en el proceso");
            } catch (ActividadNoEncontradaException e) {
                Alertas.error("Error", "No se encontro la predecesora especificada");
            }
        }

        crearActBtn.setDisable(false);
    }

    private void registrarActividad() throws ActividadRegistradaException, ActividadNoEncontradaException {
        Actividad predecesora = choicePredecesora.getSelectionModel().getSelectedItem();

        if (predecesora == null) {
            proceso.registrarActividad(new Actividad(
                txtNombre.getText(),
                checkOpcional.isSelected(),
                txtDescripcion.getText()
            ));
        } else {
            proceso.registrarActividad(predecesora.getNombre(), new Actividad(
                txtNombre.getText(),
                checkOpcional.isSelected(),
                txtDescripcion.getText()
            ));
        }

        Aplicativo.guardarAplicativo(Aplicativo.instancia());
        Aplicativo.cargarAplicativo();
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtNombre.getText(), txtDescripcion.getText());
            return true;
        } catch (ValidacionException e) {
            Alertas.error("Campos vacios", "Ninguno de los campos debe estar en blanco");
            return false;
        }
    }
}