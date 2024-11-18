package co.edu.uniquindio.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class ProcesosAdminController {

    @FXML
    private TableView<?> tbTablaProcesos;

    @FXML
    private TableColumn<?, ?> tcActividades;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTreas;

    @FXML
    private Text txProcesosRealizados;

    @FXML
    void actualizar(ActionEvent event) {

    }

    @FXML
    void crear(ActionEvent event) {

    }

    @FXML
    void eliminar(ActionEvent event) {

    }

}

