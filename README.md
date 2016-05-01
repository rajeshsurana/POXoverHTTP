# POXoverHTTP
Plain Old XML over HTTP (tunnelling)

In this project, client and server communicate using XML messages via HTTP. On client side I have provided interface to read Food Item data in XML/JSON format. I use Factory method and Singleton pattern to read XML and JSON data. There is also an interface to directly paste XML message which triggers the action on server side. Finally, I show up the XML message returned by the server. 

On server side, I store the Food Item data in XML format. I used Jersey framework to provide Level-0 Restful services according to Richardson Maturity Model. Please refer documents in [Info](https://github.com/rajeshsurana/POXoverHTTP/tree/master/Info) folder to run the project.

Here, is the client interface ->

![Client file chooser](https://raw.githubusercontent.com/rajeshsurana/POXoverHTTP/master/images/client_fileChooser.png)

Client request and response ->
![Client request response](https://raw.githubusercontent.com/rajeshsurana/POXoverHTTP/master/images/client_retrieve.png)
