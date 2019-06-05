/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog;

import doclic.data.CRUDData;
import doclic.dialog.impl.DocenteDialog;
import doclic.dialog.impl.MateriaDialog;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Agustín
 * @param <T> Docente, Materia, Movimiento
 * Specified by derived classes rather that user choice
 * 
 */

public abstract class CRUDDialog<T extends CRUDData> extends SuperDialog<T> {
    protected static List<CRUDDialog> 
            INSTANCES = Arrays.asList(new DocenteDialog(), new MateriaDialog());
    
    public static <U extends CRUDData> CRUDDialog<U> get(Class<U> clazz){
        return INSTANCES.stream()
            .filter(dialog -> clazz.equals(dialog.clazz))
            .findFirst()
            .get();
    }
    
    
    protected CRUDDialog(Class<T> clazz){ super(clazz); }
    
    abstract public CRUDDialog insert();
    abstract public CRUDDialog update(T t);
    abstract public CRUDDialog delete(T t);
}
