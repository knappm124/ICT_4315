/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.service;
import edu.du.ict4315.parking.RealParkingOffice;
import java.util.Properties;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 * 
 * This class will be used to register a car with the parking office and provide a permit
 */
public class RegisterCarCommand implements Command {
    private RealParkingOffice office;
    
    private void checkParameters(Properties params) {
        
    }
    
    @Override
    public String execute(Properties params){
        return "";
    }
    
    @Override
    public String getCommandName(){
        return "";
    }
    
    @Override
    public String getDisplayName(){
        return "";
    }
}
