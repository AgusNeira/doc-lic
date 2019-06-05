package doclic.model;

import doclic.data.SERUDData;
import doclic.model.filter.CheckBoxFilter;
import doclic.model.filter.Filter;
import java.time.LocalDate;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author quintosegunda
 * @param <T>
 */
public abstract class SERUDModel<T extends SERUDData>
        extends ReadOnlyModel<SERUDData> {
    
    public static TableColumn<SERUDData, String>[] getColumns(){
        TableColumn<SERUDData, Integer> id = new TableColumn<>("Código");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<SERUDData, String> doc = new TableColumn<>("Docente");
        doc.setCellValueFactory(new PropertyValueFactory<>("docente"));
        
        TableColumn<SERUDData, String> mat = new TableColumn<>("Materia");
        mat.setCellValueFactory(new PropertyValueFactory<>("materia"));
        
        TableColumn<SERUDData, LocalDate> date0 = new TableColumn<>("Fecha de inicio");
        date0.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        
        TableColumn<SERUDData, LocalDate> date1 = new TableColumn<>("Fecha estimada de fin");
        date1.setCellValueFactory(new PropertyValueFactory<>("temptativeEndDate"));
        
        TableColumn<SERUDData, LocalDate> date2 = new TableColumn<>("Fecha de fin");
        date2.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        
        TableColumn<SERUDData, String> period = new TableColumn<>("Duración");
        period.setCellValueFactory(new PropertyValueFactory<>("duration"));
        
        TableColumn<SERUDData, String> reason = new TableColumn<>("Motivo");
        reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        
        TableColumn<SERUDData, String> obs = new TableColumn<>("Observaciones");
        obs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        
        return (TableColumn<SERUDData, String>[]) Arrays
                .asList(id, doc, mat, date0, date1, date2, period, reason, obs)
                .toArray();
        
    }
    
    protected final HBox buttons;
    protected final Button start, end, delete;
    protected final VBox filters;
    protected final Filter vigentesFilter;
    
    public SERUDModel(Class clazz){
        super(clazz, getColumns());
        
        this.start = new Button("Iniciar nuevo");
        this.start.setOnAction(this::startOnAction);
        
        this.end = new Button("Finalizar existente");
        this.end.addEventFilter(ActionEvent.ACTION, this::selectedAndNotEndedFilter);
        this.end.setOnAction(this::endOnAction);
        
        this.delete = new Button("Eliminar");
        this.delete.addEventFilter(ActionEvent.ACTION, this::selectionFilter);
        this.delete.setOnAction(this::deleteOnAction);
        
        this.buttons = new HBox(start, end, delete);
        this.buttons.setSpacing(15);
        
        this.vigentesFilter = new CheckBoxFilter<T>(
                "Mostrar sólo los elementos vigentes",
                obj -> !obj.isEnded()
        );
        vigentesFilter.linkFilterToData(this.data);
        this.filters = new VBox(vigentesFilter);
        
        this.content.getChildren().add(buttons);
    }
    
    abstract protected void start(SERUDData obj);
    abstract protected void end(SERUDData end);
    abstract protected void delete(SERUDData obj);
    
    abstract protected void startOnAction(ActionEvent event);
    abstract protected void endOnAction(ActionEvent event);
    abstract protected void deleteOnAction(ActionEvent event);
    
    private void selectedAndNotEndedFilter(ActionEvent event){
        if(this.table.getSelectionModel().isEmpty()){
            event.consume();
            return;
        }
        if(this.table
                .getSelectionModel()
                .getSelectedItem()
                .isEnded()){
            event.consume();
        }
    }
}
