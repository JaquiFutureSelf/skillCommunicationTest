package com.pegaxchange.java.bean;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AlexaResponse implements Serializable {
    private static final long serialVersionUID = 6926191735682596960L;
//    private int id;
    private String name;
    
    public AlexaResponse() {} // needed for JAXB
    public AlexaResponse(String text) {
        this.name = text;

    }
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}