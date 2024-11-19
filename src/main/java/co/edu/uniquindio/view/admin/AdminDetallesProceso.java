package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.estructuras.listas.ListaDobleCircular;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
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
import javafx.scene.text.Text;

public class AdminDetallesProceso {

    private Proceso proceso;

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
    private Text lblId;

    @FXML
    private Text lblNombre;

    @FXML
    private Text lblNumResultados;

    @FXML
    private ChoiceBox<Boolean> opcionalChoice;

    @FXML
    private TableColumn<Actividad, String> tablaActNombre;

    @FXML
    private TableColumn<Actividad, Boolean> tablaActOpcional;

    @FXML
    private TableColumn<Actividad, String> tablaActDescripcion;

    @FXML
    private TableView<Actividad> tablaActividades;

    @FXML
    private TextField txtBuscar;

    public AdminDetallesProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    @FXML
    void initialize() {
        lblNombre.setText(this.proceso.getNombre());
        lblId.setText(this.proceso.getId());

        tablaActNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaActDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaActOpcional.setCellValueFactory(new PropertyValueFactory<>("opcional"));
        actualizarTabla(proceso.getActividades());

        opcionalChoice.setItems(
            FXCollections.observableArrayList(null, true, false)
        );
    }

    @FXML
    void crearActividad(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminCrearActividad",
            new AdminCrearActividad(proceso)
        );
    }

    @FXML
    void detallesActividad(ActionEvent event) throws IOException {
        if (tablaActividades.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            App.instancia().setRoot(
                "admin/AdminVistaActividad",
                new AdminDetallesActividad(
                    proceso, tablaActividades.getSelectionModel().getSelectedItem()
                )
            );
        }
    }

    @FXML
    void editarActividad(ActionEvent event) throws IOException {
        if (tablaActividades.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            App.instancia().setRoot(
                "admin/AdminEditarActividad",
                new AdminEditarActividad(
                    proceso,
                    tablaActividades.getSelectionModel().getSelectedItem()
                )
            );
        }
    }

    @FXML
    void removerActividad(ActionEvent event) {
        if (tablaActividades.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "Debes seleccionar un elemento de la tabla");
        } else {
            proceso.removerActividad(
                tablaActividades.getSelectionModel().getSelectedItem().getNombre()
            );

            Aplicativo.guardarAplicativo(Aplicativo.instancia());

            actualizarTabla(proceso.getActividades());
        }
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        App.instancia().setRoot("inicio");
    }

    @FXML
    void volverInicio(ActionEvent event) throws IOException {
        App.instancia().setRoot("AdminListaProcesos2");
    }

    @FXML
    void buscarActividades(ActionEvent event) {
        actualizarTabla(
            proceso.buscarActividad(txtBuscar.getText(), null, opcionalChoice.getValue())
        );
    }

    private void actualizarTabla(ListaDobleCircular<Actividad> actividades) {
        ObservableList<Actividad> datos = FXCollections.observableArrayList();

        for (Actividad actividad : actividades) datos.add(actividad);

        tablaActividades.setItems(datos);
        tablaActividades.refresh();

        lblNumResultados.setText("resultados: " + datos.size());
    }
}