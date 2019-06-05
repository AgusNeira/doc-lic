/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog;

import javafx.scene.control.TextField;

/**
 *
 * @author quintosegunda
 */
public class TextEntry extends Entry<String> {
    private final String regex;
    
    private String BAD_FORMAT_MESSAGE = "Formato inválido";

    public TextEntry(String label, boolean required, String regex, String initial){
        super(label, required);
        
        this.regex = regex;
        this.node = new TextField(initial);
        
        this.getChildren().add(node);
    }
    
    public TextEntry(String label, boolean required, String regex){
        super(label, required);
        
        this.regex = regex;
        this.node = new TextField();
        
        this.getChildren().add(node);
    }
    
    @Override public String getValue() {
        return ((TextField) this.node).getText();
    }

    @Override public boolean isEmptyCompliant() {
        return !(((TextField) this.node).getText() == null && this.required);
    }
    
    public boolean isFormatCompliant(){
        return !(!((TextField) this.node).getText().matches(regex) && this.required);
    }

    @Override public boolean isCompliant() {
        return isEmptyCompliant() && isFormatCompliant();
    }
    
    public void setFormattingMessage(String str){
        this.BAD_FORMAT_MESSAGE = str;
    }
    
    @Override public String getMessage(){
        if(!isEmptyCompliant()) return EMPTY_MESSAGE;
        else if(!isFormatCompliant()) return BAD_FORMAT_MESSAGE;
        else return "";
    }
    
}
