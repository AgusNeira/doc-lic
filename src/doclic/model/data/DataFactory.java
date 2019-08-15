/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author neira
 * @param <T>
 */
public interface DataFactory <T extends Data> {
    public T create(ResultSet rs) throws SQLException;
}