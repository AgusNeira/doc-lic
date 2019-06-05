package doclic;

import doclic.database.ConnectionPool;
import doclic.model.DocenteModel;
import doclic.model.LicenciaModel;
import doclic.model.MateriaModel;
import doclic.model.SuplenciaModel;
import doclic.model.TitularityModel;

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
        
        DocenteModel.init();
        MateriaModel.init();
        TitularityModel.init();
        SuplenciaModel.init();
        LicenciaModel.init();
        
        this.tabPane = new TabPane(
                DocenteModel.get(),
                MateriaModel.get(),
                TitularityModel.get(),
                SuplenciaModel.get(),
                LicenciaModel.get()
        );
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        this.menuBar = new AppMenu(tabPane.getTabs());
    
        this.content = new VBox(menuBar, tabPane);
    
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