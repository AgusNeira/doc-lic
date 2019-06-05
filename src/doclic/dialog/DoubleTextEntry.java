package doclic.dialog;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Agustín
 */
public class DoubleTextEntry extends Entry<DoubleTextEntry.StringPair> {
    public class StringPair{
        private final String first, second;
        
        public StringPair(String first, String second){
            this.first = first;
            this.second = second;
        }
        public String first(){
            return this.first;
        }
        public String second(){
            return this.second;
        }
    }
    
    private final TextField first, second;
    private final String firstRegex, secondRegex;
    
    private String BAD_FORMAT_MESSAGE = "Formato inválido";
    
    public DoubleTextEntry(String lbl, boolean required,
            String firstRegex, String secondRegex){
        super(lbl, required);
        
        this.first = new TextField();
        this.second = new TextField();
        this.node = new HBox(first, second);
        
        this.firstRegex = firstRegex;
        this.secondRegex = secondRegex;
        
        this.getChildren().addAll(this.node);
    }
    
    public DoubleTextEntry(String lbl, boolean required,
            String firstRegex, String secondRegex,
            String firstInitial, String secondInitial){
        super(lbl, required);
        
        this.first = new TextField(firstInitial);
        this.second = new TextField(secondInitial);
        this.node = new HBox(first, second);
        
        this.firstRegex = firstRegex;
        this.secondRegex = secondRegex;
        
        this.getChildren().addAll(node);
    }
    
    @Override public StringPair getValue(){
        return new StringPair(first.getText(), second.getText());
    }
    
    public boolean isFirstEmptyCompliant(){
        return !(first.getText().isEmpty() && required);
    }
    public boolean isSecondEmptyCompliant(){
        return !(second.getText().isEmpty() && required);
    }
    @Override public boolean isEmptyCompliant(){
        return isFirstEmptyCompliant() && isSecondEmptyCompliant();
    }
    
    public boolean isFirstFormatCompliant(){
        return !(!first.getText().matches(firstRegex) && required);
    }
    public boolean isSecondFormatCompliant(){
        return !(!second.getText().matches(secondRegex) && required);
    }
    public boolean isFormatCompliant(){
        return isFirstFormatCompliant() && isSecondFormatCompliant();
    }
    
    @Override public boolean isCompliant(){
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
