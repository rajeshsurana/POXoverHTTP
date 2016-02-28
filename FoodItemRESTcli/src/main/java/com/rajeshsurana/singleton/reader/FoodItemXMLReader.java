/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.singleton.reader;

import com.rajeshsurana.AbstractReader.IFoodItemDataReader;
import com.rajeshsurana.Concrete.FoodItem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Rajesh Surana
 */
public class FoodItemXMLReader extends IFoodItemDataReader{

    private Document doc;
    protected static IFoodItemDataReader uniqueInstanceXML = null;
    
    // Private Constructor to create Singleton pattern
    private FoodItemXMLReader() {
    }

    // Return already created unique instance
    public static IFoodItemDataReader getInstance() {
            if(uniqueInstanceXML == null){
                    uniqueInstanceXML = new FoodItemXMLReader();
            }
            return uniqueInstanceXML;
    }
    
    @Override
    public List<FoodItem> readFoodItems() {
        List<FoodItem> foodItemList = new ArrayList<FoodItem>();
        NodeList foodItemNodeList = doc.getElementsByTagName("FoodItem");
        for(int i=0; i < foodItemNodeList.getLength(); i++){
            FoodItem f = new FoodItem();
            Element foodItemNode = (Element)foodItemNodeList.item(i);
            
            // Set foodItem country
            String country = foodItemNode.getAttribute("country");
            if(country != null) f.setCountry(country);
            
            // Set foodItem id
            String id = foodItemNode.getElementsByTagName("id").item(0).getTextContent();
            if(id != null) f.setId(id);
            
            // Set foodItem name
            String name = foodItemNode.getElementsByTagName("name").item(0).getTextContent();
            if(name != null) f.setName(name);
            
            // Set foodItem description
            String description = foodItemNode.getElementsByTagName("description").item(0).getTextContent();
            if(description != null) f.setDescription(description);
            
            // Set foodItem category
            String category = foodItemNode.getElementsByTagName("category").item(0).getTextContent();
            if(category != null) f.setCategory(category);
            
            // Set foodItem price
            String price = foodItemNode.getElementsByTagName("price").item(0).getTextContent();
            if(price != null) f.setPrice(price);
            
            // Add food item to food item list
            foodItemList.add(f);
            
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
        try{
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(fXmlFile);		
            this.doc.getDocumentElement().normalize();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
}
