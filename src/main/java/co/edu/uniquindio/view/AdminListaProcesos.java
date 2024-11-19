package co.edu.uniquindio.view;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.vista.Alertas;
import co.edu.uniquindio.view.admin.AdminDetallesProceso;
import co.edu.uniquindio.view.admin.AdminEditarProceso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminListaProcesos {

    @FXML
    private TextField busquedaId;

    @FXML
    private TextField busquedaNombre;

    @FXML
    private Button buscarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button detallesBtn;

    @FXML
    private Button editarBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button navInicio;

    @FXML
    private TableView<Proceso> tablaProcesos;

    @FXML
    private TableColumn<Proceso, ?> tablaProcesosEstado;

    @FXML
    private TableColumn<Proceso, String> tablaProcesosId;

    @FXML
    private TableColumn<Proceso, String> tablaProcesosNombre;

    public AdminListaProcesos() {
    }

    @FXML
    public void initialize() {
        tablaProcesosId.setCellValueFactory(new PropertyValueFactory<Proceso, String>("id"));
        tablaProcesosNombre.setCellValueFactory(new PropertyValueFactory<Proceso, String>("nombre"));

        actualizarTabla(Aplicativo.instancia().getProcesos());
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        App.instancia().setRoot("inicio");
    }

    @FXML
    void volverInicio(ActionEvent event) {
        //
    }

    @FXML
    void iniciarBusqueda(ActionEvent event) {
        actualizarTabla(
            Aplicativo.instancia().buscarProcesos(
                busquedaId.getText(), busquedaNombre.getText()
            )
        );
    }

    @FXML
    void crearProceso(ActionEvent event) throws IOException {
        App.instancia().setRoot("admin/AdminCrearProceso");
    }

    @FXML
    void editarProceso() throws IOException {
        if (tablaProcesos.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            App.instancia().setRoot(
                "admin/AdminEditarProceso",
                new AdminEditarProceso(tablaProcesos.getSelectionModel().getSelectedItem())
            );
        }
    }

    @FXML
    void detallesProceso() throws IOException {
        if (tablaProcesos.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            App.instancia().setRoot(
                "admin/AdminVistaProceso",
                new AdminDetallesProceso(tablaProcesos.getSelectionModel().getSelectedItem())
            );
        }
    }

    @FXML
    synchronized void removerProceso() throws IOException {
        eliminarBtn.setDisable(true);

        if (tablaProcesos.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            Aplicativo.instancia().removerProceso(
                tablaProcesos.getSelectionModel().getSelectedItem().getId()
            );

            Aplicativo.guardarAplicativo(Aplicativo.instancia());
            Aplicativo.cargarAplicativo();

            actualizarTabla(Aplicativo.instancia().getProcesos());
        }

        eliminarBtn.setDisable(false);
    }

    @FXML
    void irAUsuarios() throws IOException {
        App.instancia().setRoot("admin/AdminListaUsuario");
    }

    private void actualizarTabla(ListaSimple<Proceso> procesos) {
        ObservableList<Proceso> coll  = FXCollections.observableArrayList();

        for (Proceso proceso : procesos) coll.add(proceso);
        
        tablaProcesos.setItems(coll);
        tablaProcesos.refresh();
    }
}