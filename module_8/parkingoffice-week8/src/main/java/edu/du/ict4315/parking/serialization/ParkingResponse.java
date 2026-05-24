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
    
    public ParkingResponse(int statusCode, String message){
        this.statusCode = statusCode;
        this.message= message;
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
    
    public JsonObject toJSON(){
        JsonObject response = new JsonObject();
        response.addProperty("statuscode",statusCode);
        response.addProperty("message",message);
        return response;
    }
    
}
