package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.procesos.ProcesoRegistradoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminCrearProceso {

    @FXML
    private Button cancelarCreacionBtn;

    @FXML
    private Button crearProceoBtn;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    void crearProceso(ActionEvent event) throws IOException {
        if (validarCampos()) {
            if (registrarProceso()) {
                Alertas.informacion("Proceso registrado", "El proceso se ha registrado correctamente");
                App.instancia().setRoot("AdminListaProcesos2");
            } else {
                Alertas.error("Error al registrar", "El id del proceso ya esta en uso");
            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot("AdminListaProcesos2");
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtId.getText(), txtNombre.getText());
            return true;
        } catch (ValidacionException e) {
            Alertas.error("Error!", "Todos los campos son requiridos");
        }

        return false;
    }

    private boolean registrarProceso() throws IOException {
        try {
            Aplicativo.instancia().registrarProceso(
                new Proceso(txtId.getText(), txtNombre.getText())
            );

            Aplicativo.guardarAplicativo(Aplicativo.instancia());
            Aplicativo.cargarAplicativo();

            return true;
        } catch (ProcesoRegistradoException e) {
            return false;
        }
    }
}