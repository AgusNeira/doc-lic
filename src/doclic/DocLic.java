package doclic;

import doclic.database.ConnectionPool;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Agustín
 */
public class DocLic extends Application {
    
    private VBox content;
    private TabPane tabPane;
    private AppMenu menuBar;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ConnectionPool.create("jdbc:sqlite:database.db");
        
        content = new VBox();
    
        Scene scene = new Scene(content);
        
        stage.setTitle("Administración de licencias");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}