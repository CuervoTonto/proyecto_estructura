module co.edu.uniquindio {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.desktop;

    opens co.edu.uniquindio to javafx.fxml;
    exports co.edu.uniquindio;

    opens co.edu.uniquindio.modelos to javafx.fxml;
    exports co.edu.uniquindio.modelos;

    opens co.edu.uniquindio.view to javafx.fxml;
    opens co.edu.uniquindio.view.admin to javafx.fxml;

    opens co.edu.uniquindio.utilidades to javafx.fxml;

    exports co.edu.uniquindio.utilidades to javafx.graphics;
    exports co.edu.uniquindio.view to javafx.fxml;
    exports co.edu.uniquindio.estructuras.listas;
    exports co.edu.uniquindio.estructuras.colas;
    exports co.edu.uniquindio.estructuras.pilas;
}