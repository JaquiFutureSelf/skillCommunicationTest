package com.pegaxchange.java.web.rest;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.amazon.speech.json.SpeechletResponseEnvelope;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.pegaxchange.java.bean.AlexaResponse;
import com.pegaxchange.java.bean.Product;
import com.pegaxchange.java.bean.Status;

@Path("productcatalog")
public class ProductCatalogResource {
    private static List<Product> productCatalog;
    private static List<AlexaResponse> alexaCatalog;
    private static List<SpeechletResponseEnvelope> alexaResponse;
    
    public ProductCatalogResource() {
        initializeProductCatalog();
        initializeAlexaCatalog();
        initializeAlexaResponseList();
    }
    @GET
    @Path("search/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product[] searchByCategory(@PathParam("category") String category) {
        List<Product> products = new ArrayList<Product>();
        for (Product p : productCatalog) {
            if (category != null && category.equalsIgnoreCase(p.getCategory())) {
                products.add(p);
            }
        }
        return products.toArray(new Product[products.size()]);
    }
    
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Product[] searchByName(@QueryParam("name") String name) {
        List<Product> products = new ArrayList<Product>();
        for (Product p : productCatalog) {
            if (name != null && name.toLowerCase().startsWith(p.getName().toLowerCase())) {
                products.add(p);
            }
        }
        return products.toArray(new Product[products.size()]);
    }
    
    @GET
    @Path("search2")
    @Produces(MediaType.APPLICATION_JSON)
    public AlexaResponse[] searchAlexaByName(@QueryParam("name") String name) {
        List<AlexaResponse> products = new ArrayList<AlexaResponse>();
        for (AlexaResponse p : alexaCatalog) {
            if (name != null && name.toLowerCase().startsWith(p.getName().toLowerCase())) {
                products.add(p);
            }
        }
        return products.toArray(new AlexaResponse[alexaCatalog.size()]);
    }
    
    @GET
    @Path("speechletResponse")
    @Produces(MediaType.APPLICATION_JSON)
    public SpeechletResponseEnvelope[] searchAlexaspeechlet(@QueryParam("name") String name) {

        return alexaResponse.toArray(new SpeechletResponseEnvelope[alexaResponse.size()]);
    }
    
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status insert(Product product) {
        productCatalog.add(product);
        return new Status("SUCCESS", "Inserted " + product.getName());
    }
    private void initializeProductCatalog() {
        if (productCatalog == null) {
            productCatalog = new ArrayList<Product>();
            productCatalog.add(new Product(1, "Keyboard", "Electronics", 29.99D));
            productCatalog.add(new Product(2, "Mouse", "Electronics", 9.95D));
            productCatalog.add(new Product(3, "17\" Monitor", "Electronics", 159.49D));
            productCatalog.add(new Product(4, "Hammer", "Maven Hardware", 9.95D));
            productCatalog.add(new Product(5, "Screwdriver", "Hardware", 7.95D));
            productCatalog.add(new Product(6, "English Dictionary", "Books", 11.39D));
            productCatalog.add(new Product(7, "A House in Bali", "Books", 15.99D));
            productCatalog.add(new Product(8, "An Alaskan Odyssey", "Books", 799.99D));
            productCatalog.add(new Product(9, "LCD Projector", "Electronics", 1199.19D));
            productCatalog.add(new Product(10, "Smart Thermostat", "Electronics", 1199.19D));
        }
    }
    
    private void initializeAlexaCatalog() {
        if (alexaCatalog == null) {
        	alexaCatalog = new ArrayList<AlexaResponse>();
            alexaCatalog.add(new AlexaResponse("1_ABC"));
            alexaCatalog.add(new AlexaResponse("2_DEF"));
            alexaCatalog.add(new AlexaResponse("3_GHI"));
        }
    }
    
    private void initializeAlexaResponseList() {
        if (alexaResponse == null) {
        	alexaResponse = new ArrayList<SpeechletResponseEnvelope>();
        	alexaResponse.add(response());
        }
    }

    private SpeechletResponseEnvelope response(){
    	SpeechletResponseEnvelope resp = new SpeechletResponseEnvelope();
//    	  Map<String,Object> sessionAttributesOutput = mapper.convertValue(outputMetadata, new TypeReference<Map<String,Object>>(){});
//    	  responseEnvelope.setSessionAttributes(sessionAttributesOutput);
    	resp.setVersion("1.0");
    	resp.setResponse(getTestResponse());
    	return resp;
    }
    
    private SpeechletResponse getTestResponse() {
        String speechText = "Dies ist ein Test. Der Webservice funktioniert.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Webservice Test");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        SpeechletResponse resp = SpeechletResponse.newTellResponse(speech, card);
        resp.setNullableShouldEndSession(false);
		return resp;
    }
    
    
    
}