package co.edu.uniquindio.view;

import java.io.IOException;

import co.edu.uniquindio.excepciones.autenticacion.UsuarioRegistradoException;
import co.edu.uniquindio.excepciones.usuarios.CorreoEnUsoException;
import co.edu.uniquindio.excepciones.validaciones.FormatoCorreoInvalidoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.gestores.GestorUsuarios;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.modelos.Usuario.TipoUsuario;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioNormalController {

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private void initialize() {

    }

    public boolean crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCorreo(txtCorreo.getText());
        usuario.setNombre(txtNombre.getText());
        usuario.setClave(txtPassword.getText());
        usuario.setTipo(TipoUsuario.NORMAL);

        try {
            GestorUsuarios.cargarGestor().registrar(usuario);
            GestorUsuarios.instancia().guardarInformacion();
        } catch (CorreoEnUsoException e) {
            Alertas.error("creacion de usaurio", "No se puedo registrar el usuario");
            return false;
        } catch (IOException e) {
            Alertas.error("Error!", "Hubo un error con los ficheros");
            return false;
        }

        Alertas.informacion("Creacion Completada", "Se Creo el Admin correctamente");
        return true;
    }

    @FXML
    void agregarUsuario(ActionEvent event) throws UsuarioRegistradoException {
        if (verificarCampos() && crearUsuario()) {
            limpiarCampos();
        } else {
            Alertas.error("Error en la Creacion", "Los Datos son invalidos o ya utilizados");
        }
    }

    private boolean verificarCampos() {
        try {
            Validador.validarRequeridos(
                txtCorreo.getText(),
                txtNombre.getText(),
                txtPassword.getText(),
                txtUsername.getText()
            );

            Validador.ValidarCorreo(txtCorreo.getText());

            return true;
        } catch (FormatoCorreoInvalidoException e) {
            Alertas.error("Error", "El correo ingresado no es válido.");
        } catch (ValidacionException e) {
            Alertas.error("Error", "Todos los campos son obligatorios.");
        }

        return false;
    }

    private void limpiarCampos() {
        txtCorreo.clear();
        txtNombre.clear();
        txtPassword.clear();
        txtUsername.clear();
    }
}
