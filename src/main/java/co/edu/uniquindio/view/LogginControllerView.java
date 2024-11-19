package co.edu.uniquindio.view;

import co.edu.uniquindio.App;
import co.edu.uniquindio.autenticacion.Autenticador;
import co.edu.uniquindio.autenticacion.Credenciales;
import co.edu.uniquindio.autenticacion.Sesion;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioClaveIncorrectaException;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioNoRegistradoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LogginControllerView {

    @FXML
    private void initialize() {
    }

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button iniciarBtn;        

    @FXML
    void ingresar(ActionEvent event) {
        iniciarBtn.setDisable(true);

        if (verificarCampo()) {
            abrirVentanaSegunTipo(event);
        }

        iniciarBtn.setDisable(false);
    }

    private void abrirVentanaSegunTipo(ActionEvent event) {
        try {
            Autenticador.autenticar(
                new Credenciales(txtUsername.getText(), txtContrasena.getText())
            );

            try {
                switch (Sesion.getInstancia().getUsuario().getTipo()) {
                    case NORMAL:
                        cambiarVentana("UsuarioGeneral.fxml", event);
                        break;

                    case ADMINISTRADOR:
                        // cambiarVentana("AdminListaProcesos2.fxml", event);
                        cambiarVentandaAdministardor(event);
                        break;

                    default:
                        Alertas.error("Error!", "No se pudo identificar el tipo de usuario");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Sesion.getInstancia().setUsuario(null);
            }
        } catch (UsuarioNoRegistradoException e) {
            Alertas.error("Datos invalidos", "El correo no pertenece a ningun usuario");
        } catch (UsuarioClaveIncorrectaException e) {
            Alertas.error("Datos invalidos", "La contraseña es incorrecta");
        }
    }

    // int opciones = logginController.autentificador(txtUsername.getText(),
    // txtContrasena.getText());
    // switch (opciones){

    // case 1: cambiarVentana("AdminGeneral.fxml",event);
    // break;

    // case 2: cambiarVentana("UsuarioGeneral.fxml",event);
    // break;

    // case 0: mostrarMensaje("Error Al Iniciar Sesion","Error con los datos","El
    // Usuario"+
    // "o Contraseña no han sido registrados", Alert.AlertType.ERROR);
    // break;
    // }}

    private boolean verificarCampo() {
        try {
            Validador.validarRequeridos(txtContrasena.getText(), txtUsername.getText());

            return true;
        } catch (ValidacionException e) {
            Alertas.error("Datos invalidos", e.getMessage());
        }

        return false;
    }

    public void cambiarVentana(String nombreFxml, ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(nombreFxml));
        Parent root = loader.load();
        Scene scene = new Scene(root, 720, 480);

        // Obtener la referencia a la ventana actual
        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActual.close(); // Cerrar la ventana actual

        // Abrir la nueva ventana
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void cambiarVentandaAdministardor(ActionEvent event) throws IOException {
        App.instancia().setRoot("AdminListaProcesos2");
    }
}
