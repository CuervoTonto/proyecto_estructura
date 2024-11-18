package co.edu.uniquindio.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.autenticacion.Sesion;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.modelos.Proceso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminListaProcesos implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablaProcesosId.setCellValueFactory(new PropertyValueFactory<Proceso, String>("id"));
        tablaProcesosNombre.setCellValueFactory(new PropertyValueFactory<Proceso, String>("nombre"));

        actualizarTabla(Aplicativo.cargarAplicativo().getProcesos());
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


    private void actualizarTabla(ListaSimple<Proceso> procesos) {
        ObservableList<Proceso> coll  = FXCollections.observableArrayList();

        for (Proceso proceso : procesos) coll.add(proceso);
        
        tablaProcesos.setItems(coll);
        tablaProcesos.refresh();
    }
}