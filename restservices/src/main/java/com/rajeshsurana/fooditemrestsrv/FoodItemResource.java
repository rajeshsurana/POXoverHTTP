/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.fooditemrestsrv;

import com.rajeshsurana.processor.FoodItemProcessor;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Rajesh Surana
 */
@Path("myresource")
public class FoodItemResource {

    @Context
    private UriInfo context;
    
    private FoodItemProcessor fip;

    /**
     * Creates a new instance of FoodItemResource
     */
    public FoodItemResource() {
        
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "<html><body><h1>A Restfull Hello, World!!</body></h1></html>";
    }
    /**
     * PUT method for updating or creating an instance of FoodItemResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response postXml(String content) {
        //System.out.println(content);
        fip = new FoodItemProcessor();
        String response = fip.processCommand(content);
        return Response.ok().entity(response).build();
    }
}
