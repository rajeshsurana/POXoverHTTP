/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.AbstractReader;

import com.rajeshsurana.Concrete.FoodItem;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author Rajesh Surana
 */
public abstract class IFoodItemDataReader {
    public static IFoodItemDataReader getInstance() {
        return null;
    }
    protected String filePath;
    public abstract List<FoodItem> readFoodItems();

    public abstract String getFilePath();

    public abstract void setFilePath(String filePath); 
    
    public void setDoc(Document doc){}
    
    public Document getDoc(){ return null; }  
}
