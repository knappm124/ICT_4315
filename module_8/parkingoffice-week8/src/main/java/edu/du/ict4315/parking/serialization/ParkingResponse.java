/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author melyg
 */
public class ParkingResponse {
    private final int statusCode;
    private final String message;
    
    public ParkingResponse(String json){
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        this.statusCode = obj.get("statuscode").getAsInt();
        this.message = obj.get("message").getAsString();
    }
    
    public int getStatus(){
        return statusCode;
    }
    
    public String getMessage(){
        return message;
    }
    
    @Override
    public String toString(){
        String s = "{'statuscode':";
        s += statusCode;
        s += ",'message':'";
        s += message;
        s += "'}";
        return s;
    }
}
