package co.edu.uniquindio.view.admin;
import java.io.IOException;

import co.edu.uniquindio.App;
import co.edu.uniquindio.excepciones.usuarios.CorreoEnUsoException;
import co.edu.uniquindio.excepciones.validaciones.FormatoCorreoInvalidoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.gestores.GestorUsuarios;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.modelos.Usuario.TipoUsuario;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AdminCrearUsuario {

    @FXML
    private Button cancelarBtn;

    @FXML
    private ChoiceBox<TipoUsuario> choiceTipo;

    @FXML
    private Button crearBtn;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {
        choiceTipo.setItems(
            FXCollections.observableArrayList(TipoUsuario.NORMAL, TipoUsuario.ADMINISTRADOR)
        );

        choiceTipo.setValue(TipoUsuario.NORMAL);
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot("admin/AdminListaUsuario");
    }

    @FXML
    void crearUsuario(ActionEvent event) throws IOException {
        if (validarCampos()) {
            try {
                GestorUsuarios.instancia().registrar(
                    new Usuario(
                        txtNombre.getText(), txtCorreo.getText(), txtClave.getText(), choiceTipo.getValue()
                    )
                );

                Alertas.informacion("Exito", "Usuario creado con exito");
                App.instancia().setRoot("admin/AdminListaUsuario");
            } catch (CorreoEnUsoException e) {
                Alertas.error("Usuario registrado", "El correo electronico se encuentra en uso");
            }
        }
    }

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtNombre.getText(), txtCorreo.getText(), txtClave.getText());
            Validador.ValidarCorreo(txtCorreo.getText());

            return true;
        } catch (FormatoCorreoInvalidoException e) {
            Alertas.error("Correo invalidos", "El corro electronico debe tener un formato valido");
        } catch (ValidacionException e) {
            Alertas.error("Campos vaciso", "No deben haber campos vacios");
        }

        return false;
    }
}