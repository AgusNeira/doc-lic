/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model;

import doclic.data.Data;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Agustín
 * @param <T>
 */
public class DataList<T extends Data> {
    private final ObservableList<T> data, filtered;
    private final ObservableList<Predicate<T>> filters;
    
    public DataList(){
        this.data = FXCollections.observableArrayList();
        this.filtered = FXCollections.observableArrayList();
        this.filters = FXCollections.observableArrayList();
        
        this.data.addListener(this::updateFiltered);
        this.filters.addListener(this::updateFiltered);
    }
    
    public boolean add(T data){
        return this.data.add(data);
    }
    public boolean addAll(T... data){
        return this.data.addAll(data);
    }
    public void clear(){
        this.data.clear();
    }
    public Stream<T> stream(){
        return this.data.stream();
    }
    
    public ObservableList<T> get(){
        return this.filtered;
    }
    
    public Predicate<T> getPredicate(){
        return this.filters
            .stream()
            .reduce(Predicate::and)
            .orElse(t -> true);
    }
    
    public void addFilter(Predicate<T> predicate){
        if(this.filters.contains(predicate)) return;
        this.filters.add(predicate);
    }
    public boolean removeFilter(Predicate<T> predicate){
        return this.filters.remove(predicate);
    }
    public void removeAllFilters(){
        this.filters.clear();
    }
    
    public boolean isFiltered(){ return !filters.isEmpty(); }
    
    public void updateFiltered(ListChangeListener.Change change){
        this.filtered.setAll(this.data
            .stream()
            .filter(this.getPredicate())
            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    };
    
    public void addListener(ListChangeListener listener){
        this.filtered.addListener(listener);
    }
}
