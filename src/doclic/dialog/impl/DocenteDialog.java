/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog.impl;

import doclic.data.Docente;
import doclic.dialog.CRUDDialog;
import doclic.dialog.TextEntry;
import doclic.model.DocenteModel;

import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 *
 * @author Agustín
 */
public class DocenteDialog extends CRUDDialog<Docente> {
    
    private static final String NAME_REGEX = 
              "^([A-Z][a-zA-ZÀ-ÿ\\u00f1\\u00d1]+)"
            + "(\\s[A-Z][a-zA-ZÀ-ÿ\\u00f1\\u00d1]+)+$";
    private static final String CUIT_REGEX = "^[0-9]{2}-[0-9]{8}-[0-9]$";
    
    @Override public DocenteDialog insert(){
        TextEntry nameEntry = new TextEntry("Nombre", true, NAME_REGEX);
        nameEntry.setFormattingMessage("Este campo debe estar compuesto por\n"
                + "dos o más palabras, todas comenzando con mayúscula");
        TextEntry cuitEntry = new TextEntry("CUIT", true, CUIT_REGEX);
        cuitEntry.setFormattingMessage("Este campo debe respetar el siguiente\n"
                + "formato: NN-NNNNNNNN-N\n"
                + "donde N es cualuier dígito");
        
        Callback<ButtonType, Docente> resultConverter;
        resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY)
                return new Docente(
                        -1,
                        nameEntry.getValue(),
                        cuitEntry.getValue()
                );
            return null;
        };
        
        return (DocenteDialog) this.reset(CRUDDialog.FORM_TYPE.INSERT,
                DocenteModel.getDefaultValue(),
                resultConverter, nameEntry, cuitEntry
        );
    }
    @Override public DocenteDialog update(Docente doc){
        TextEntry nameEntry = new TextEntry("Nombre", true, NAME_REGEX, doc.toString());
        TextEntry cuitEntry = 
                new TextEntry("CUIT", true, CUIT_REGEX, doc.cuitProperty().get());
        
        Callback<ButtonType, Docente> resultConverter;
        resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY)
                return new Docente(
                        doc.idProperty().get(),
                        nameEntry.getValue(),
                        cuitEntry.getValue()
                );
            return null;
        };
        
        return (DocenteDialog) this.reset(CRUDDialog.FORM_TYPE.INSERT, doc,
                resultConverter, nameEntry, cuitEntry
        );
    }
    @Override public DocenteDialog delete(Docente doc){
        return (DocenteDialog) this.reset(doc);
    }
    
    public DocenteDialog(){ super(Docente.class); }
}
