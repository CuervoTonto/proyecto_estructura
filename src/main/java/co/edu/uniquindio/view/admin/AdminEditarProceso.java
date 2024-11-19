package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminEditarProceso {

    private Proceso proceso;

    @FXML
    private Button actualizarProceoBtn;

    @FXML
    private Button cancelarBtn;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    public AdminEditarProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    @FXML
    void initialize() {
        this.txtId.setText(proceso.getId());
        this.txtNombre.setText(proceso.getNombre());
    }

    @FXML
    void actualizarProceso(ActionEvent event) throws IOException {
        if (validarCampos()) {
            if (actualizar()) {
                Alertas.informacion("Accion exitosa", "Se ha actualizado el proceso correctamente");
                App.instancia().setRoot("AdminListaProcesos2");
            } else {
                Alertas.error("Error", "no hemos podido actualizar la informacion");
            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot("AdminListaProcesos2");
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtNombre.getText());
            return true;
        } catch (ValidacionException e) {
            return false;
        }
    }

    private boolean actualizar() {
        boolean actualizado = Aplicativo.instancia().actualizarProceso(
            this.proceso.getId(),
            new Proceso(this.proceso.getId(), txtNombre.getText())
        );

        if (actualizado) {
            Aplicativo.guardarAplicativo(Aplicativo.instancia());
            Aplicativo.cargarAplicativo();
        }

        return actualizado;
    }
}