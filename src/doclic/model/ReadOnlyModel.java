/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model;

import doclic.model.data.Data;
import doclic.model.data.DataFactory;
import doclic.model.data.TableColumnsFactory;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


/**
 *
 * @author neira
 * @param <T> data type
 */
public class ReadOnlyModel <T extends Data> extends Tab {
    
    private final String RETRIEVE;
    
    private final ObservableList<T> data_;
    private final VBox content;
    private final TableView<T> table_;
    private final DataFactory factory;
    
    public ReadOnlyModel(String title, String tableName, DataFactory dataFactory, TableColumnsFactory columns){
        super(title);
        data_ = FXCollections.observableArrayList();
        
        table_ = new TableView(data_);
        table_.getColumns().addAll(columns.create());
        
        content = new VBox(table_);
        
        factory = dataFactory;
        
        this.setContent(content);
        
        this.RETRIEVE = "SELECT * FROM " + tableName;
        
        
    }
    
    void retrieve(){
        try(){
            
        }
    }
    
}
