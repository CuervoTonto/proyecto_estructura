package co.edu.uniquindio.view.admin;

import java.io.IOException;

import co.edu.uniquindio.Aplicativo;
import co.edu.uniquindio.App;
import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.excepciones.tareas.TareaOpcionalException;
import co.edu.uniquindio.excepciones.validaciones.FormatoEnteroInvalidoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.modelos.Tarea;
import co.edu.uniquindio.utilidades.Validador;
import co.edu.uniquindio.utilidades.vista.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class AdminCrearTarea
{
    private Proceso proceso;

    private Actividad actividad;

    @FXML
    private Button cancelarBtn;

    @FXML
    private CheckBox checkOpcional;

    @FXML
    private Button crearTareaBtn;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtDuracion;

    @FXML
    private ChoiceBox<OpcionTarea> choicePredecesora;

    public AdminCrearTarea(Proceso proceso,  Actividad actividad) {
        this.proceso = proceso;
        this.actividad = actividad;
    }

    @FXML
    void initialize() {
        Cola<Tarea> tareas = actividad.getTareas();
        ObservableList<OpcionTarea> opciones = FXCollections.observableArrayList();

        for (int i = 0; i < tareas.longitud(); i++) {
            Tarea elemento = tareas.desencolar();
            OpcionTarea op = new OpcionTarea(i, "#" + (i + 1) + " (" + elemento.getDescripcion() + ")");
            opciones.add(op);
            tareas.encolar(elemento);
        }

        choicePredecesora.setItems(opciones);

        choicePredecesora.setConverter(new StringConverter<OpcionTarea>() {
            @Override
            public String toString(OpcionTarea object) {
                if (object != null) {
                    return object.tarea;
                }

                return "Ninguna (al final de la cola)";
            }

            @Override
            public OpcionTarea fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        App.instancia().setRoot(
            "admin/AdminVistaActividad",
            new AdminDetallesActividad(proceso, actividad)
        );
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        if (validarCampos()) {
            try {
                OpcionTarea opcion = choicePredecesora.getSelectionModel().getSelectedItem();

                if (opcion == null) {
                    actividad.registrarTarea(
                        new Tarea(
                            Integer.valueOf(txtDuracion.getText()),
                            checkOpcional.isSelected(),
                            txtDescripcion.getText()
                        )
                    );
                } else {
                    actividad.registrarTarea(
                        opcion.posicion + 1,
                        new Tarea(
                            Integer.valueOf(txtDuracion.getText()),
                            checkOpcional.isSelected(),
                            txtDescripcion.getText()
                        )
                    );
                }

                Aplicativo.guardarAplicativo();
                Alertas.informacion("Tarea registrada", "La tarea se ha registrado con exito");
                cancelar(event);
            } catch (TareaOpcionalException e) {
                Alertas.error("Error", "No deben haber 2 actividades seguidas");
            }
        }
    }    

    private boolean validarCampos() {
        try {
            Validador.validarRequeridos(txtDuracion.getText(), txtDescripcion.getText());
            Validador.validarEnteros(txtDuracion.getText());

            return true;
        } catch (FormatoEnteroInvalidoException e) {
            Alertas.error("Error", "El campo duracion debe ser numerico");
        } catch (ValidacionException e) {
            Alertas.error("Error", "ninguno de los campos puede estar en blanco");
        }

        return false;
    }

    private class OpcionTarea {
        public int posicion;
        public String tarea;

        public OpcionTarea(int posicion, String tarea) {
            this.posicion = posicion;
            this.tarea = tarea;
        }
    }
}