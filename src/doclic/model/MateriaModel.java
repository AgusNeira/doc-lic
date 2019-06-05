package doclic.model;

import doclic.data.Materia;
import doclic.data.SERUDData;
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

/**
 *
 * @author Agustín
 */
public class MateriaModel extends CRUDModel<Materia> {
    private static MateriaModel INSTANCE = null;
    
    private static TableColumn<Materia, String>[] getColumns(){
        
        TableColumn<Materia, String> id = new TableColumn<>("Código");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Materia, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Materia, String> titular = new TableColumn<>("Titular");
        titular.setCellValueFactory(new PropertyValueFactory<>("titular"));
        
        TableColumn<Materia, String> suplente = new TableColumn<>("Suplente");
        suplente.setCellValueFactory(new PropertyValueFactory<>("suplente"));
        
        TableColumn<Materia, String> carga = new TableColumn<>("Carga Semanal");
        carga.setCellValueFactory(new PropertyValueFactory<>("load"));
        
        TableColumn<Materia, String> licensed = new TableColumn<>("Licencia");
        licensed.setCellValueFactory(new PropertyValueFactory<>("licensed"));
        
        return (TableColumn<Materia, String>[]) Arrays
                .asList(id, name, titular, suplente, carga, licensed)
                .toArray();
    }
    
    public static void init(){
        if(INSTANCE == null) INSTANCE = new MateriaModel(getColumns());
    }
    public static boolean isInit(){
        return INSTANCE == null;
    }
    public static MateriaModel get(){
        if(INSTANCE == null) return null;
        return INSTANCE;
    }
    
    public static Materia getDefaultValue(){
        return MateriaModel.get().DEFAULT_VALUE;
    }
    
    public MateriaModel(TableColumn<Materia, String>... columns){
        super(Materia.class, columns);
        this.retrieve();
    }
    
    @Override public final void retrieve(){
        this.data.clear();
        
        String sqlQuery = "SELECT id, name, year, division, "
                + "titular, suplente, hourlyload, licenseactive FROM materias;";
        
        try {
            
            Connection conn = ConnectionPool.get().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
               
            while(rs.next()) this.data.add(new Materia(rs));
               
            this.DEFAULT_VALUE = this.data
                    .stream()
                    .findFirst()
                    .orElse(null);
            
            rs.close();
            stmt.close();
            ConnectionPool.get().releaseConnection(conn);
                
        } catch(SQLException e){
            Logger.getLogger(MateriaModel.class.getName()).log(
                    Level.SEVERE,
                    "Error selecting table at MateriaModel::retrieve",
                    e
            );
        }
        
    }
    
    @Override protected final void insert(Materia mat){
        String query = "INSERT INTO materias(name, year, division,"
            + "titular, suplente, hourlyload) VALUES (?, ?, ?, 0, 0, ?);";
        
        try {
            
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
                
            ps.setString(1, mat.getName());
            ps.setInt(2, mat.getYear());
            ps.setInt(3, mat.getDivision());
            ps.setInt(4, mat.getLoad());
         
            ps.executeUpdate();
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
           
        } catch(SQLException e){
            Logger.getLogger(MateriaModel.class.getName())
                .log(Level.SEVERE, "Error while inserting data "
                        + "into Materias, at MateriaModel::insert", e);
        }
    }
    @Override protected final void update(Materia from, Materia to){
        
        String query = "UPDATE materias SET name = ?, "
                + "year = ?, division = ?, hourlyload = ?"
                + " WHERE id = ?";
        
        try {
            
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
           
            ps.setString(1, to.getName());
            ps.setInt(2, to.getYear());
            ps.setInt(3, to.getDivision());
            ps.setInt(4, to.getLoad());
                
            ps.setInt(5, to.getId());
           
            ps.executeUpdate();
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
            
        } catch(SQLException e){
            Logger.getLogger(MateriaModel.class.getName())
                .log(Level.SEVERE, "Error while updating data "
                        + "from Materias, at MateriaModel::update", e);
        }
    }
    @Override protected final void delete(Materia mat){
        
        String query = "DELETE FROM materias WHERE id = ?;";
            
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
              
            ps.setLong(1, mat.getId());
                       
            ps.executeUpdate();
                
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
                
        } catch(SQLException e){
            Logger.getLogger(MateriaModel.class.getName())
                .log(Level.SEVERE, "Error while deleting data "
                        + "from Materias, at MateriaModel::delete", e);
        }
    }
    
    public final void updateTitular(SERUDData tit, boolean deleting){
        String sqlQuery = "UPDATE Materias SET titular = ? WHERE id = ?";
        
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
                
            if(!(tit.isEnded() || deleting)){
                ps.setLong(1, tit.getDocente().getCUIT());
            } else {
                ps.setLong(1, 0);
            }
            ps.setInt(2, tit.getMateria().getId());
        } catch(SQLException ex){
            Logger.getLogger(MateriaModel.class.getName())
                    .log(Level.SEVERE, "Error when updating "
                        + "Materias on MateriaModel::updateTitular", ex);
        }
    }
    
    public final void updateSuplente(SERUDData sup, boolean deleting){
        String sqlQuery = "UPDATE Materias SET suplente = ? WHERE id = ?";
        
        try {
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
              
            if(!(sup.isEnded() || deleting)){
                ps.setLong(1, sup.getDocente().getCUIT());
            } else {
                ps.setLong(1, 0);
            }
            ps.setInt(2, sup.getMateria().getId());
            
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
            
        } catch(SQLException ex){
            Logger.getLogger(MateriaModel.class.getName())
                    .log(Level.SEVERE, "Error when updating "
                        + "Materias on MateriaModel::updateSuplente", ex);
        }
    }
    
    public final void updateLicencia(SERUDData lic, boolean deleting){
        String sqlQuery = "UPDATE Materias SET licenseactive = ? WHERE id = ?";
        
        try { 
            Connection conn = ConnectionPool.get().getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
                
            if(!(lic.isEnded() || deleting)){
                ps.setLong(1, 1);
            } else {
                ps.setLong(1, 0);
            }
            ps.setInt(2, lic.getMateria().getId());
                
            ps.close();
            ConnectionPool.get().releaseConnection(conn);
                
        } catch(SQLException ex){
            Logger.getLogger(MateriaModel.class.getName())
                    .log(Level.SEVERE, "Error when updating "
                        + "Materias on MateriaModel::updateLicencia", ex);
        }
    }
}
