/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.data;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustín
 */
public abstract class Data {
    public static Map<Class, String>
            classNames = Stream.of(new Object[][] {
        { Docente.class, "docente" }, 
        { Materia.class, "materia" },
        { Titularity.class, "titularidad" },
        { Suplencia.class, "suplencia" },
        { Licencia.class, "licencia" }
    }).collect(Collectors.toMap(
        data -> (Class) data[0],
        data -> (String) data[1])
    );
    public static <T> String getClassName(Class<T> clazz){
        return classNames.get(clazz);
    }
    
    public static Map<Class, String>
            tableNames = Stream.of(new Object[][] {
        { Docente.class, "Docentes" }, 
        { Materia.class, "Materias" },
        { Titularity.class, "Titularidades" },
        { Suplencia.class, "Suplencias" },
        { Licencia.class, "Licencias" }
    }).collect(Collectors.toMap(
        data -> (Class) data[0],
        data -> (String) data[1])
    );
    public static <T> String getTableName(Class<T> clazz){
        return tableNames.get(clazz);
    }
    
    public abstract Data copy();
}
