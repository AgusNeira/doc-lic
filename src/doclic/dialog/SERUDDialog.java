 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog;

import doclic.data.Docente;
import doclic.data.Materia;
import doclic.data.SERUDData;
import doclic.model.DocenteModel;
import doclic.model.MateriaModel;
import doclic.model.Reasons;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 *
 * @author Agustín
 */
public class SERUDDialog extends SuperDialog<SERUDData> {
    protected static final List<SERUDDialog>
            INSTANCES = FXCollections.observableArrayList();
    
    public static <U extends SERUDData> SERUDDialog get(Class<U> clazz){
        return INSTANCES.stream()
                .filter(dialog -> clazz.equals(dialog.clazz))
                .findFirst()
                .orElseGet(() -> {
                    SERUDDialog dialog = new SERUDDialog(clazz);
                    INSTANCES.add(dialog);
                    return dialog;
                });
    }
    
    protected SERUDDialog(Class clazz){
        super(clazz);
    }
    
    public SERUDDialog start(){
        ChoiceEntry<Docente> doc = new ChoiceEntry<>("Docente",
                DocenteModel.get().getData(), true
        );
        ChoiceEntry<Materia> mat = new ChoiceEntry<>("Materia",
                MateriaModel.get().getData(), true
        );
        DateEntry date0 = new DateEntry("Fecha de comienzo", true);
        DateEntry date1 = new DateEntry("Fecha estimativa de fin", true);
        ChoiceEntry<String> reason = new ChoiceEntry("Motivo",
                Reasons.DATA, true
        );
        TextEntry obs = new TextEntry("Observaciones", false, ".*");
        
        Callback<ButtonType, SERUDData> resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY){
                return new SERUDData(
                        0,
                        doc.getValue(),
                        mat.getValue(),
                        date0.getValue(),
                        date1.getValue(),
                        reason.getValue(),
                        obs.getValue()
                );
            } else return null;
        };
        
        return (SERUDDialog) this.reset(FORM_TYPE.START, null,
                resultConverter, doc, mat, date0, date1, reason, obs);
    }
    public SERUDDialog end(SERUDData obj){
        
        DateEntry date = new DateEntry("Fecha de fin", true, LocalDate.now());
        
        Callback<ButtonType, SERUDData> resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY){
                return new SERUDData(
                        obj.getId(),
                        obj.getDocente(),
                        obj.getMateria(),
                        obj.getDate0(),
                        obj.getDate1(),
                        date.getValue(),
                        obj.getReason(),
                        obj.getObservaciones()
                );
            } else return null;
        };
        
        return (SERUDDialog) this.reset(FORM_TYPE.INSERT, null,
                resultConverter, date);
    }
    public SERUDDialog delete(SERUDData mov){
        return (SERUDDialog) this.reset(mov);
    }
}
