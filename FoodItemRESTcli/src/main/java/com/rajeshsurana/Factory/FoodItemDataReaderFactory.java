/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.Factory;

import com.rajeshsurana.AbstractReader.IFoodItemDataReader;
import com.rajeshsurana.singleton.reader.FoodItemJSONReader;
import com.rajeshsurana.singleton.reader.FoodItemXMLReader;

/**
 *
 * @author Rajesh Surana
 */
public class FoodItemDataReaderFactory {
    public static IFoodItemDataReader FoodItemDataReaderFactoryMethod(String filePath) {
            String extension = "";
            if(filePath.toLowerCase().endsWith(".json")){
                    extension = "json";
            }else if(filePath.toLowerCase().endsWith(".xml")){
                    extension = "xml";
            }
            IFoodItemDataReader reader = null;
            switch (extension) {
            case "xml":
                    reader = FoodItemXMLReader.getInstance();
                    break;
            case "json":
                    reader = FoodItemJSONReader.getInstance();
                    break;
            default:
                    break;
            }
            return reader;
    }
}
