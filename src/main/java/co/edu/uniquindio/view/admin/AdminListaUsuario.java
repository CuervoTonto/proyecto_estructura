package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.App;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.gestores.GestorUsuarios;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.modelos.Usuario.TipoUsuario;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminListaUsuario {
    
    @FXML
    private Button buscarBtn;

    @FXML
    private TextField busquedaCorreo;

    @FXML
    private TextField busquedaNombre;

    @FXML
    private Button crearBtn;

    @FXML
    private Button editarBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button navInicio;

    @FXML
    private Button navUsuarios;

    @FXML
    private TableView<Usuario> tabla;

    @FXML
    private TableColumn<Usuario, String> tablaCol1;

    @FXML
    private TableColumn<Usuario, String> tablaCol2;

    @FXML
    private TableColumn<Usuario, TipoUsuario> tablaCol3;

    @FXML
    private ChoiceBox<Usuario.TipoUsuario> choiceTipo;

    public AdminListaUsuario() {
    }

    @FXML
    void initialize() {
        choiceTipo.setItems(
            FXCollections.observableArrayList(null, TipoUsuario.NORMAL, TipoUsuario.ADMINISTRADOR)
        );

        tablaCol1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaCol2.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tablaCol3.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        actualizarTabla(GestorUsuarios.instancia().getUsuarios());
    }

    @FXML
    void crearUsuario(ActionEvent event) throws IOException {
        App.instancia().setRoot("admin/AdminCrearUsuario");
    }

    @FXML
    void editarUsuario(ActionEvent event) {

    }

    @FXML
    void iniciarBusqueda(ActionEvent event) {
        actualizarTabla(
            GestorUsuarios.instancia().buscarUsuario(
                busquedaNombre.getText(),
                busquedaCorreo.getText(),
                choiceTipo.getValue()
            )
        );
    }

    @FXML
    void removerUsuario(ActionEvent event) throws IOException {
        if (tabla.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            GestorUsuarios.instancia().remover(
                tabla.getSelectionModel().getSelectedItem().getCorreo()
            );

            GestorUsuarios.instancia().guardarInformacion();
            Alertas.informacion("Exito", "Usuario eliminado correctamente");
            iniciarBusqueda(event);
        }
    }

    @FXML
    void irAUsuarios() throws IOException {
        App.instancia().setRoot("admin/AdminListaUsuarios");
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        App.instancia().setRoot("inicio");
    }

    @FXML
    void volverInicio(ActionEvent event) throws IOException {
        App.instancia().setRoot("AdminListaProcesos2");
    }

    private void actualizarTabla(ListaSimple<Usuario> usuarios) {

        ObservableList<Usuario> resultado = FXCollections.observableArrayList();

        for (Usuario usuario : usuarios) resultado.add(usuario);

        tabla.setItems(resultado);
        tabla.refresh();
    }
}