package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.modelos.Tarea;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class AdminDetallesActividad {

    private Proceso proceso;

    private Actividad actividad;

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
    private Text lblDurMax;

    @FXML
    private Text lblDurMin;

    @FXML
    private Text lblNombre;

    @FXML
    private Text lblNumResultados;

    @FXML
    private Text lblProceso;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button navInicio;

    @FXML
    private ChoiceBox<Boolean> opcionalChoice;

    @FXML
    private TableColumn<Tarea, ?> tablaTareaDescripcion;

    @FXML
    private TableColumn<Tarea, ?> tablaTareaDuracion;

    @FXML
    private TableColumn<Tarea, ?> tablaTareaOpcional;

    @FXML
    private TableView<Tarea> tablaTareas;

    @FXML
    private TextField txtBuscar;

    public AdminDetallesActividad(Proceso proceso, Actividad actividad) {
        this.proceso = proceso;
        this.actividad = actividad;
    }

    @FXML
    void initialize() {
        lblNombre.setText(actividad.getNombre());
        lblDurMin.setText("Duracion minima: " + actividad.calcularDuracionMinima());
        lblDurMax.setText("Duracion Maxima: " + actividad.calcularDuracionMaxima());
        lblProceso.setText("Proceso: " + proceso.getNombre() + " (" + proceso.getId() + ")");

        opcionalChoice.setItems(
            FXCollections.observableArrayList(null, true, false)
        );

        tablaTareaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaTareaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        tablaTareaOpcional.setCellValueFactory(new PropertyValueFactory<>("opcional"));

        actualizarTabla(actividad.getTareas());
    }

    @FXML
    void buscarActividades(ActionEvent event) {
        actualizarTabla(
            actividad.buscarTareas(txtBuscar.getText(), opcionalChoice.getValue())
        );
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
    void crearTarea(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminCrearTarea",
            new AdminCrearTarea(proceso, actividad)
        );
    }

    @FXML
    void editarTarea(ActionEvent event) throws IOException {
        if (tablaTareas.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "seleccione un elemento de la tabla");
        } else {
            Tarea tarea = tablaTareas.getSelectionModel().getSelectedItem();
            int posicion = actividad.posicionTarea(tarea);

            if (posicion == -1) {
                Alertas.error("Posicion invalida", "La posicion de la tarea no es valida");
            } else {
                App.instancia().setRoot(
                    "admin/AdminEditarTarea",
                    new AdminEditarTarea(proceso, actividad, actividad.posicionTarea(tarea))
                );
            }
        }
    }

    @FXML
    void removerTarea(ActionEvent event) {
        if (tablaTareas.getSelectionModel().isEmpty()) {
            Alertas.error("Error", "seleccione un elemento de la tabla");
        } else {
            actividad.removerTarea(
                tablaTareas.getSelectionModel().getSelectedItem()
            );

            Aplicativo.guardarAplicativo();

            actualizarTabla(actividad.getTareas());
        }
    }

    @FXML
    void volverAlProceso(MouseEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminVistaProceso",
            new AdminDetallesProceso(proceso)
        );
    }

    private void actualizarTabla(Cola<Tarea> tareas) {
        ObservableList<Tarea> datos = FXCollections.observableArrayList();

        for (int i = 0; i < tareas.longitud(); i++) {
            Tarea elemento = tareas.desencolar();
            datos.add(elemento);
            tareas.encolar(elemento);
        }

        tablaTareas.setItems(datos);
        tablaTareas.refresh();
        lblNumResultados.setText("resultados: " + tareas.longitud());
    }

    private class FilaTarea {
        public int posicion;

    }
}