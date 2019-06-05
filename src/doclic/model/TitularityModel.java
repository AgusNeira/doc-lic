
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model;

import doclic.data.SERUDData;
import doclic.data.Titularity;
import doclic.database.ConnectionPool;
import doclic.dialog.SERUDDialog;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

/**
 *
 * @author Agustín
 */
public class TitularityModel extends SERUDModel<Titularity> {
    private static TitularityModel INSTANCE = null;
    
    public static void init(){
        if(INSTANCE == null) INSTANCE = new TitularityModel();
    }
    public static boolean isInit(){
        return INSTANCE == null;
    }
    public static TitularityModel get(){
        if(INSTANCE == null) return null;
        return INSTANCE;
    }
    
    public TitularityModel(){
        super(Titularity.class);
        this.retrieve();
    }
    
    @Override public final void retrieve(){
        this.data.clear();
        
            String query = "SELECT id, docente, materia, date0,"
                    + "date1, date2, reason, observaciones FROM Titularidades";
            
            try {
                Connection conn = ConnectionPool.get().getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                while(rs.next()) this.data.add(new Titularity(rs));
                
                rs.close();
                st.close();
                ConnectionPool.get().releaseConnection(conn);
                
            } catch(SQLException e){
                Logger.getLogger(TitularityModel.class.getName())
                        .log(Level.SEVERE, "Error while retrieving in TitularityModel::retrieve", e);
            }
    }
    
    @Override protected final void start(SERUDData obj){
        
        String sqlQuery = "INSERT INTO Titularidades"
                + "(docente, materia, date0, date1, date2, reason, observaciones) "
                + "VALUES (?, ?, ?, ?, NULL, ?, ?)";
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                
            stmt.setLong(1, obj.getDocente().getCUIT());
            stmt.setInt(2, obj.getMateria().getId());
            stmt.setDate(3, Date.valueOf(obj.getDate0()));
            stmt.setDate(4, Date.valueOf(obj.getDate1()));
            stmt.setString(5, obj.getReason());
            stmt.setString(6, obj.getObservaciones());
               
            stmt.executeUpdate();
                
            stmt.close();
            ConnectionPool.get().releaseConnection(conn);
                
        } catch(SQLException e){
            Logger.getLogger(TitularityModel.class.getName())
                    .log(Level.SEVERE, "Error while inserting in TitularityModel::start", e);
        }
    }
    @Override protected final void end(SERUDData end) {
        
            String sqlQuery = "UPDATE Titularidades SET date2 = ? "
                    + "WHERE id = ?";
            
            try {
                Connection conn = ConnectionPool.get().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                
                stmt.setDate(1, Date.valueOf(end.getDate2()));
                stmt.setInt(2, end.getId());
                
                stmt.executeUpdate();
                
                stmt.close();
                ConnectionPool.get().releaseConnection(conn);
                
            } catch(SQLException e){
                Logger.getLogger(TitularityModel.class.getName())
                        .log(Level.SEVERE, "Error while inserting in TitularityModel::end", e);
            }
    }
    @Override protected final void delete(SERUDData obj) {
        
            String sqlQuery = "DELETE FROM Titularidades WHERE id = ?";
            
            try {
                Connection conn = ConnectionPool.get().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                
                stmt.setInt(1, obj.getId());
                
                stmt.executeUpdate();
                
                stmt.close();
                ConnectionPool.get().releaseConnection(conn);
                
            } catch(SQLException e){
                Logger.getLogger(TitularityModel.class.getName())
                        .log(Level.SEVERE, "Error while deleting in TitularityModel::delete", e);
            }
            
    }
    
    @Override protected void startOnAction(ActionEvent event){
        SERUDDialog.get(Titularity.class)
                .start()
                .ifResult((selected, result) -> {
                        this.start(result);
                        MateriaModel.get().updateTitular(result, false);
                });
    }
    @Override protected void endOnAction(ActionEvent event){
        SERUDDialog.get(this.clazz)
            .end(table.getSelectionModel().getSelectedItem())
            .ifResult((selected, result) -> {
                    this.end((Titularity) result);
                    MateriaModel.get().updateTitular(result, false);
                });
    }
    @Override protected void deleteOnAction(ActionEvent event){
        SERUDDialog.get(Titularity.class)
                .delete(table.getSelectionModel().getSelectedItem())
                .ifResult((selected, result) -> {
                    this.delete(selected);
                    MateriaModel.get().updateTitular(selected, true);
                });
    }
}
