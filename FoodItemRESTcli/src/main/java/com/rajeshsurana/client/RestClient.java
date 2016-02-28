/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.client;


import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

/**
 * Jersey REST client generated for REST resource:GreetingResource
 * [myresource]<br>
 * USAGE:
 * <pre>
 *        GreetingClient client = new GreetingClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author calliss
 */
public class RestClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/restservices/webapi";

    public RestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("myresource");
    }

    public String getHtml() throws javax.ws.rs.ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.TEXT_HTML).get(String.class);
    }
    public Response sendXML(String xml){
        WebTarget resource = webTarget;
        //String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <note> <to>Tove</to> <from>Jani</from> <heading>Reminder</heading> <body>Do forget me this weekend!</body> </note>";
        return resource.request(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(Entity.entity(xml, MediaType.WILDCARD_TYPE),Response.class);
    }

    public void close() {
        client.close();
    }
    
}
