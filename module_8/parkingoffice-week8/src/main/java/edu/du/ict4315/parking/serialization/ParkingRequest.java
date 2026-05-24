/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Properties;

/**
 *
 * @author melyg
 */
public class ParkingRequest {
    private final String command;
    private final Properties props;
    
    public ParkingRequest(String json){
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        this.command = obj.get("command").toString();
        Properties prop = new Properties();
        String str = obj.get("props").toString();
        JsonObject obj2 = JsonParser.parseString(str).getAsJsonObject();
        for(String key : obj2.keySet()){
            prop.put(key, obj2.get(key).toString());
        }
        System.out.println(prop);
        this.props = prop;
    }
    
    public String getCommand(){
        return command;
    }
    
    public Properties getProps(){
        return props;
    }
    
    @Override
    public String toString(){
        String s = "{'command':";
        s += command;
        s += ",'props':{";
        for(String temp : props.stringPropertyNames()){
            s += "'";
            s += temp;
            s += "':";
            s += props.getProperty(temp);
            s += ",";
            System.out.println(s);
        }
        s = s.substring(0,s.length()-1);
        s += "}'}";
        return s;
    }
}
