/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model.data;

import java.util.List;
import javafx.scene.control.TableColumn;

/**
 *
 * @author neira
 */
public interface TableColumnsFactory {
    public TableColumn[] create();
}
