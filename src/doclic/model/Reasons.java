package doclic.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Agustín
 */
public class Reasons {
    public static final String CARGO_MAYOR = "Cargo mayor de jerarquía";
    public static final String ORGANIZACION = "Organización escolar";
    public static final String PERSONAL = "Personal";
    public static final String ENFERMEDAD = "Por enfermedad";
    public static final String OTRO = "Otro";
    
    public static final ObservableList<String>
        DATA = FXCollections.observableArrayList(
            CARGO_MAYOR,
            ORGANIZACION,
            PERSONAL,
            ENFERMEDAD,
            OTRO
        );
}
