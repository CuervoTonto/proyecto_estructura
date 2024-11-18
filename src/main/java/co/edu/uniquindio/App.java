package co.edu.uniquindio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application
{
    private static App instacia;
    private Scene scene;
    private Stage stage;

    public static App instancia()
    {
        return instacia;
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        App.instacia = this;
        this.stage = stage;
        // scene = new Scene(loadFXML("inicio"), 720, 480);
        // scene = new Scene(loadFXML("AdminListaProcesos2"), 720, 480);
        // stage.setScene(scene);
        // stage.show();
        setScene(new Scene(loadFXML("inicio"), 720, 480));
    }

    public void setScene(Scene scene) throws IOException
    {
        stage.setScene(this.scene = scene);
        stage.show();
    }

    public void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
    }

    public void setRoot(String fxml, Object controller) throws IOException
    {
        scene.setRoot(loadFXML(fxml, controller));
    }

    private Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private Parent loadFXML(String fxml, Object controller) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }
}