/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic;

import doclic.model.DocenteModel;
import doclic.model.LicenciaModel;
import doclic.model.MateriaModel;
import doclic.model.SuplenciaModel;
import doclic.model.TitularityModel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

/**
 *
 * @author Agustín
 */
public class AppMenu extends MenuBar {
    
    private final Menu file, view, help;
    
    private final CheckMenuItem docs, mats, tits, sups, lics;
    
    private final MenuItem exit, about;
    
    public AppMenu(ObservableList<Tab> tabs){
        super();
        
        this.exit = new MenuItem("Salir");
        this.exit.setOnAction(event -> { Platform.exit(); System.exit(0); });
        
        this.file = new Menu("Archivo");
        this.file.getItems().add(exit);
        
        this.docs = new CheckMenuItem("Docentes");
        this.docs.setSelected(true);
        this.docs.setOnAction(event -> {
            if(docs.isSelected()) tabs.add(DocenteModel.get());
            else tabs.remove(DocenteModel.get());
        });
        
        this.mats = new CheckMenuItem("Materias");
        this.mats.setSelected(true);
        this.mats.setOnAction(event -> {
            if(mats.isSelected()) tabs.add(MateriaModel.get());
            else tabs.remove(MateriaModel.get());
        });
        
        this.tits = new CheckMenuItem("Cargos de titulares");
        this.tits.setSelected(true);
        this.tits.setOnAction(event -> {
            if(tits.isSelected())
                tabs.add(TitularityModel.get());
            else
                tabs.remove(TitularityModel.get());
        });
        
        this.sups = new CheckMenuItem("Suplencias");
        this.sups.setSelected(true);
        this.sups.setOnAction(event -> {
            if(sups.isSelected())
                tabs.add(SuplenciaModel.get());
            else tabs.remove(SuplenciaModel.get());
        });
        
        this.lics = new CheckMenuItem("Licencias");
        this.lics.setSelected(true);
        this.lics.setOnAction(event -> {
            if(lics.isSelected())
                tabs.add(LicenciaModel.get());
            else tabs.remove(LicenciaModel.get());
        });
        
        this.view = new Menu("Ver");
        this.view.getItems().addAll(docs, mats, tits, sups, lics);
        
        this.about = new MenuItem("Acerca de");
        
        this.help = new Menu("Ayuda");
        this.help.getItems().add(about);
        
        this.getMenus().addAll(file, view, help);
    }
    
}
