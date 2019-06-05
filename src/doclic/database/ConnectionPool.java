/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author neira
 */
public class ConnectionPool {
    private static ConnectionPool INSTANCE;
 
    private final String url;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;
     
    public static ConnectionPool get(){
        return INSTANCE;
    }
    
    public static ConnectionPool create(String url) throws SQLException {
  
        if(INSTANCE == null){
            List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.add(createConnection(url));
            }
            INSTANCE = new ConnectionPool(url, pool);
        }
        return INSTANCE;
    }
     
    public ConnectionPool(String url, List<Connection> pool){
        this.url = url;
        this.connectionPool = pool;
    }
     
    public Connection getConnection() {
        Connection connection = connectionPool
          .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }
     
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
     
    private static Connection createConnection(String url) 
      throws SQLException {
        return DriverManager.getConnection(url);
    }
     
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
 
    public String getUrl(){
        return this.url;
    }
}