/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.singleton.reader;

import com.rajeshsurana.AbstractReader.IFoodItemDataReader;
import com.rajeshsurana.Concrete.FoodItem;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Rajesh Surana
 */
public class FoodItemJSONReader extends IFoodItemDataReader {
    
    protected static IFoodItemDataReader uniqueInstanceJSON = null;
    
    // Private Constructor to create Singleton pattern
    private FoodItemJSONReader() {
    }

    // Return already created unique instance
    public static IFoodItemDataReader getInstance() {
            if(uniqueInstanceJSON == null){
                    uniqueInstanceJSON = new FoodItemJSONReader();
            }
            return uniqueInstanceJSON;
    }

    @Override
    public List<FoodItem> readFoodItems() {
        JSONParser parser = new JSONParser();
	Object obj = null;
        try {
            // Read json file
            obj = parser.parse(new FileReader(getFilePath()));
        } catch (Exception e) {
                System.out.println(e.getStackTrace());
        }
        JSONObject jsonRootObject = (JSONObject) obj;
        List<FoodItem> foodItemList = new ArrayList<FoodItem>();
        JSONArray foodItemObjectList = null;
        
        if(jsonRootObject.get("FoodItemData") instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) jsonRootObject.get("FoodItemData");
            foodItemObjectList = new JSONArray();
            foodItemObjectList.add(jsonObject);
        }else{
                foodItemObjectList =  (JSONArray) jsonRootObject.get("FoodItemData");
        }
        if(foodItemObjectList != null){
            for(int i=0; i < foodItemObjectList.size(); i++){
                FoodItem f = new FoodItem();
                JSONObject foodItem = (JSONObject)foodItemObjectList.get(i);
                
                // Set country
                String country = (String) foodItem.get("-country");
                if(country != null) f.setCountry(country);
                
                // Set foodItem id
                String id = (String) foodItem.get("id");
                if(id != null) f.setId(id);
                
                // Set foodItem Name
                String name = (String) foodItem.get("name");
                if(name != null) f.setName(name);
                
                // Set foodItem description
                String description = (String) foodItem.get("description");
                if(description != null) f.setDescription(description);
                
                // Set foodItem category
                String category = (String) foodItem.get("category");
                if(category != null) f.setCategory(category);
                
                // Set foodItem price
                String price = (String) foodItem.get("price");
                if(price != null) f.setPrice(price);
                
                // Add food item to food item list
		foodItemList.add(f);
            }
        }
        return foodItemList;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
}
