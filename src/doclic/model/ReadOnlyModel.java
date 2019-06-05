/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model;

import doclic.data.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Agustín
 * @param <T>
 */
public abstract class ReadOnlyModel<T extends Data> extends Tab {
    private static final List<ReadOnlyModel> 
            MASTER_INSTANCES = new ArrayList<>();
    
    public static void updateAllModels(){
        MASTER_INSTANCES.forEach((model) ->  model.retrieve() );
    }
    public static List<ReadOnlyModel> getAll(){
        return MASTER_INSTANCES;
    }
    public static <U extends Data> ReadOnlyModel get(Class<U> clazz){
        return MASTER_INSTANCES
                .stream()
                .filter(model -> clazz.equals(model.clazz))
                .findFirst()
                .orElse(null);
    }
    
    protected final VBox content;
    protected final TableView<T> table;
    protected final Class<T> clazz;
    
    protected final Button refresh;
    
    protected DataList<T> data;
    
    public ReadOnlyModel(Class<T> clazz, TableColumn<T, String>... columns){
        super(Data.getTableName(clazz));
        this.clazz = clazz;
        
        MASTER_INSTANCES.add(this);
        
        this.refresh = new Button("Refrescar");
        this.refresh.setOnAction(event -> this.retrieve());
        
        this.data = new DataList<>();
        
        this.table = new TableView<>(data.get());
        this.table.getColumns().addAll(columns);
        
        this.content = new VBox(refresh, table);
        this.content.setSpacing(15);
        this.content.setPadding(new Insets(30));
        this.content.setMaxHeight(Double.MAX_VALUE);
        
        this.setContent(content);
    }
    
    abstract public void retrieve();
    
    public final ObservableList<T> getData(){
        return this.data.get();
    }
    
    public final void addListener(ListChangeListener listener){
        this.data.addListener(listener);
    }
    
    public final void addFilter(Predicate<T> pred){
        this.data.addFilter(pred);
    }
    public final void removeFilter(Predicate<T> pred){
        this.data.removeFilter(pred);
    }
    public final void removeAllFilters(){
        this.data.removeAllFilters();
    }
    
    protected final void selectionFilter(ActionEvent event){
        if(this.table.getSelectionModel().isEmpty()){
            this.noSelection().showAndWait();
            event.consume();
        }
    }
    protected final Alert noSelection(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setContentText("Debe seleccionar un elemento de la tabla\n"
            + "para poder realizar esta operación");
        return alert;
    }
    
    public final Optional<T> find(Predicate<T> predicate){
        return this.data
                .stream()
                .filter(predicate)
                .findFirst();
    }
}
