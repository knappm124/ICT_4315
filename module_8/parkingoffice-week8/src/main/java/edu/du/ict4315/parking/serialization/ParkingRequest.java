/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import com.google.gson.JsonObject;
import java.util.Properties;

/**
 *
 * @author melyg
 */
public class ParkingRequest {
    private final String command;
    private final Properties props;
    
    public ParkingRequest(String command, Properties props){
        this.command = command;
        this.props = props;
    }
    
    @Override
    public String toString(){
        String s = "{'command':'";
        s += command;
        s += "','props':'{";
        for(String temp : props.stringPropertyNames()){
            s += "'";
            s += temp;
            s += "':'";
            s += props.getProperty(temp);
            s += "',";
        }
        s = s.substring(0,s.length()-1);
        s += "}'}";
        return s;
    }
    
    public JsonObject toJSON(){
        JsonObject request = new JsonObject();
        request.addProperty("command",command);
        String s = "'props':";
        for(String temp : props.stringPropertyNames()){
            s += "'";
            s += temp;
            s += "':'";
            s += props.getProperty(temp);
            s += "',";
        }
        s = s.substring(0,s.length()-1);
        s += "}";
        request.addProperty("props",s);
        return request;
    }
}
