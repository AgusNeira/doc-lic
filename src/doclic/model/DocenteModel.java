package doclic.model;

import doclic.data.Docente;
import doclic.database.ConnectionPool;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class DocenteModel extends CRUDModel<Docente> {
    private static DocenteModel INSTANCE = null;
    
    private static TableColumn<Docente, String>[] getColumns(){
        TableColumn<Docente, Integer> id = new TableColumn<>("Código");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Docente, String> name = new TableColumn<>("Nombre y Apellido");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Docente, String> cuit = new TableColumn<>("CUIT");
        cuit.setCellValueFactory(new PropertyValueFactory<>("cuit"));
        
        return (TableColumn<Docente, String>[]) Arrays
                .asList(id, name, cuit)
                .toArray();
    }
    public static void init(){
        if(INSTANCE == null) INSTANCE = new DocenteModel();
    }
    public static boolean isInit(){
        return INSTANCE == null;
    }
    public static DocenteModel get(){
        if(INSTANCE == null) return null;
        return INSTANCE;
    }
    
    public static Docente getDefaultValue(){
        return DocenteModel.get().DEFAULT_VALUE;
    }
    
    private DocenteModel(){
        super(Docente.class, getColumns());
        this.retrieve();
    }
    
    @Override public final void retrieve(){
        this.data.clear();
        
        try{
            Connection conn = ConnectionPool.get().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fullname, cuit FROM docentes;");
            
            while(rs.next()) this.data.add(new Docente(rs));
               
            this.DEFAULT_VALUE = this.data
                    .stream()
                    .findFirst()
                    .get();
            
            rs.close();
            stmt.close();
            ConnectionPool.get().releaseConnection(conn);
                
        } catch(SQLException e){
            Logger.getLogger(DocenteModel.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting table at DocenteModel.retrieve",
                    e
            );
        }
    }
    
    @Override protected final void insert(Docente doc){
        String query = "INSERT INTO docentes(fullname, cuit) VALUES (?, ?);";
        
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, doc.nameProperty().get());
            ps.setString(2, doc.cuitProperty().get());
         
            ps.executeUpdate();
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
        } catch(SQLException e){
            Logger.getLogger(DocenteModel.class.getName())
                .log(Level.SEVERE, "Error while inserting data "
                        + "into Docentes, at DocenteModel::insert", e);
        }
    }
    @Override protected final void update(Docente from, Docente to){
        
            String query = "UPDATE docentes SET fullname = ?, cuit = ? WHERE id = ?";
            
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
           
            ps.setString(1, to.nameProperty().get());
            ps.setString(2, to.cuitProperty().get());
            ps.setInt(3, from.idProperty().get());
         
            ps.executeUpdate();
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
            
        } catch(SQLException e){
            Logger.getLogger(DocenteModel.class.getName())
                .log(Level.SEVERE, "Error while updating data "
                        + "from Docentes, at DocenteModel::update", e);
        }
        
    }
    @Override protected final void delete(Docente doc){
        
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Docentes WHERE id = ?;");
              
            ps.setInt(1, doc.idProperty().get());
                        
            ps.executeUpdate();
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
            
        } catch(SQLException e){
            Logger.getLogger(DocenteModel.class.getName())
                .log(Level.SEVERE, "Error while deleting data "
                        + "from Docentes, at DocenteModel::delete", e);
        }
        
    }
}
