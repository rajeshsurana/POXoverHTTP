/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.processor;

import com.rajeshsurana.AbstractReader.IFoodItemDataReader;
import com.rajeshsurana.Factory.FoodItemDataReaderFactory;
import com.rajeshsurana.helper.StringDocConverter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Rajesh Surana
 */
public class FoodItemProcessor {
    private String message;
    private final String DataBasePath = "FoodItemDB.xml";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message.trim();
    }
    
    private boolean isAddFoodItem(){
        boolean res = false;
        if(message.startsWith("<NewFoodItems")){
            res = true;
        }
        return res;
    }
    
    private boolean isGetFoodItem(){
        boolean res = false;
        if(message.startsWith("<SelectedFoodItems")){
            res = true;
        }
        return res;
    }
    
    
    private Map<String, List<String>> getLookupDictionaryForNameCategory(Document doc){
        // Set of Ids
        Map<String, List<String>>  lookup = new HashMap<String, List<String>>();
        NodeList foodItemNodeList = doc.getElementsByTagName("FoodItem");
        List<String> arrId = new ArrayList<String>();
        for(int i=0; i < foodItemNodeList.getLength(); i++){
            Element foodItemNode = (Element)foodItemNodeList.item(i);
            String id = foodItemNode.getElementsByTagName("id").item(0).getTextContent();
            String name = foodItemNode.getElementsByTagName("name").item(0).getTextContent();
            String category = foodItemNode.getElementsByTagName("category").item(0).getTextContent();
            List<String> list = null;
            if(lookup.containsKey(name)){
                list = lookup.get(name);
                list.add(category);
                list.add(id);
            }else{
                list = new ArrayList<String>();
            list.add(category);
            list.add(id);
            }           
            arrId.add(id);
            lookup.put(name, list);
        }
        int max = 0;
        while(true){
            try{
                max = Integer.parseInt(Collections.max(arrId));
                break;
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        List<String> maxList = new ArrayList<String>();
        maxList.add(String.valueOf(max));
        lookup.put("Max", maxList);
        return lookup;
    }
    
    
    private String addFoodItem(){
                Document newItemDoc;
        try {
            newItemDoc = StringDocConverter.convertStringToDocument(message);
        } catch (Exception ex) {
            return "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
        } 
        IFoodItemDataReader reader = FoodItemDataReaderFactory.FoodItemDataReaderFactoryMethod(DataBasePath);
        reader.setFilePath(DataBasePath);
        Document dbDoc = reader.getDoc();
        Node foodItemData = dbDoc.getElementsByTagName("FoodItemData").item(0);
        Map<String, List<String>> lookup = getLookupDictionaryForNameCategory(dbDoc);
        StringBuilder responseFoodItemAdded = new StringBuilder();
        responseFoodItemAdded.append("<FoodItemAdded xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        boolean added = false, exists = false;
        StringBuilder responseFoodItemExist = new StringBuilder();
        responseFoodItemExist.append("<FoodItemExists xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        NodeList foodItemNodeList = newItemDoc.getElementsByTagName("FoodItem");
        int maxId = Integer.parseInt(lookup.get("Max").get(0));
        for(int i=0; i < foodItemNodeList.getLength(); i++){
            Element foodItemNode = (Element)foodItemNodeList.item(i);
            String name, category, country, description, price;
            try{
                // name
                name = foodItemNode.getElementsByTagName("name").item(0).getTextContent();
                // category
                category = foodItemNode.getElementsByTagName("category").item(0).getTextContent();
                // country
                country = foodItemNode.getAttribute("country");
                // description
                description = foodItemNode.getElementsByTagName("description").item(0).getTextContent();
                // price
                price = foodItemNode.getElementsByTagName("price").item(0).getTextContent();
            }catch(Exception e){
                return "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
            }
            boolean add = true;
            
            if(lookup.containsKey(name)){
                List<String> templist = lookup.get(name);
                for(int j=0; j<templist.size(); j+=2){
                    String categoryTemp = templist.get(j);
                    String idTemp = templist.get(j+1);
                    if(categoryTemp.equals(category)){
                        add = false;
                        responseFoodItemExist.append("  <FoodItemId>"+ idTemp +"</FoodItemId>\n");
                        exists = true;
                        break;
                    }
                }     
            }
            if(add){
                maxId++;
                // Add new food item to database
                // fooditem
                Element FoodItem = dbDoc.createElement("FoodItem");
                foodItemData.appendChild(FoodItem);
                // set country attribute
                Attr attr = dbDoc.createAttribute("country");
                attr.setValue(country);
                FoodItem.setAttributeNode(attr);
                // add id
                Element idNode = dbDoc.createElement("id");
                idNode.appendChild(dbDoc.createTextNode(String.valueOf(maxId)));
                FoodItem.appendChild(idNode);
                // add name
                Element nameNode = dbDoc.createElement("name");
                nameNode.appendChild(dbDoc.createTextNode(name));
                FoodItem.appendChild(nameNode);
                // add description
                Element descriptionNode = dbDoc.createElement("description");
                descriptionNode.appendChild(dbDoc.createTextNode(description));
                FoodItem.appendChild(descriptionNode);
                // add category
                Element categoryNode = dbDoc.createElement("category");
                categoryNode.appendChild(dbDoc.createTextNode(category));
                FoodItem.appendChild(categoryNode);
                // add price
                Element priceNode = dbDoc.createElement("price");
                priceNode.appendChild(dbDoc.createTextNode(price));
                FoodItem.appendChild(priceNode);
                foodItemData.appendChild(FoodItem); 
                
                // Add appropriate response message
                responseFoodItemAdded.append("  <FoodItemId>"+ String.valueOf(maxId) +"</FoodItemId>\n");
                List<String> list = new ArrayList<String>();
                list.add(category);
                list.add(String.valueOf(maxId));
                lookup.put(name, list);
                added = true;
            }
        }        
        responseFoodItemAdded.append("</FoodItemAdded>\n");
        responseFoodItemExist.append("</FoodItemExists>\n");
        
        try{
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(dbDoc);
            String path = reader.getFilePath();
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        // return response
        String response = "";
        if(added){
            response += responseFoodItemAdded.toString();
        }
        if(exists){
            response += responseFoodItemExist.toString();
        }
        return response;
    }
    
    private List<String> getIDList(Document doc){
        List<String> idList = new ArrayList<String>();
        NodeList foodItemNodeList = doc.getElementsByTagName("FoodItemId");
        for(int i=0; i < foodItemNodeList.getLength(); i++){
            Element foodItemId = (Element)foodItemNodeList.item(i);
            String id = foodItemId.getTextContent();
            idList.add(id);
        }
        return idList;
    }
    
    private Map<String, Node> getLookupDictionaryForId(Document doc){
        // Dictionary of ids and nodes
        Map<String, Node> lookup = new HashMap<String, Node>();
        NodeList foodItemNodeList = doc.getElementsByTagName("FoodItem");
        for(int i=0; i < foodItemNodeList.getLength(); i++){
            Element foodItemNode = (Element)foodItemNodeList.item(i);
            String id = foodItemNode.getElementsByTagName("id").item(0).getTextContent();
            lookup.put(id, foodItemNodeList.item(i));
        }
        return lookup;
    }
    
    private String getFoodItem(){
        Document newItemDoc;
        try {
            newItemDoc = StringDocConverter.convertStringToDocument(message);
        } catch (Exception ex) {
            return "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
        } 
        IFoodItemDataReader reader = FoodItemDataReaderFactory.FoodItemDataReaderFactoryMethod(DataBasePath);
        reader.setFilePath(DataBasePath);
        Document dbDoc = reader.getDoc();
        Map<String, Node> lookup = getLookupDictionaryForId(dbDoc);
        List<String> list = getIDList(newItemDoc);
        StringBuilder response = new StringBuilder();
        response.append("<RetrievedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        try{
            for(String id: list){
                if(lookup.containsKey(id)){
                    response.append("   "+StringDocConverter.nodeToString(lookup.get(id)));
                }else{
                    String temp = "     <InvalidFoodItem>\n" +
                                  "         <FoodItemId>"+id+"</FoodItemId>\n" +
                                  "     </InvalidFoodItem>\n";
                    response.append(temp);
                }
            }
        }catch (TransformerException ex) {
            Logger.getLogger(FoodItemProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.append("</RetrievedFoodItems>\n");
        return response.toString(); //TODO
    }
    public String processCommand(String message){
        this.setMessage(message);
        String response = null;
        if(isAddFoodItem()){
            response = addFoodItem();
         }else if(isGetFoodItem()){
            response = getFoodItem();
        }else{
            response = "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
        }   
        System.out.println(response);
        return response;
    }
}
