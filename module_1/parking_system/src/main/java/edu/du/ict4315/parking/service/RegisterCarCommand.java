/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.service;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.CarType;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 * 
 * This class will be used to register a car with the parking office and provide a permit
 */
public class RegisterCarCommand implements Command {
    private RealParkingOffice office = new RealParkingOffice();
    
    public void checkParameters(Properties params) throws IOException {
        //Check parameters exist and are valid
        //TODO: Determine regex for license plate validity in Colorado
        if(params.getProperty("customerId").isBlank()){
            throw new IOException("Customer id cannot be empty");
        } else if (params.getProperty("carType").isBlank()) {
            throw new IOException("Car type cannot be empty");
        } else if(params.getProperty("licensePlate").isBlank()){
            throw new IOException("License plate cannot be empty");
        } else if (!"COMPACT".equals(params.getProperty("carType").toUpperCase()) || !"SUV".equals(params.getProperty("carType").toUpperCase())){
            throw new IOException("Car must be of type SUV or Compact");
        }      
    }
    
    @Override
    public String execute(Properties params){
        CarType type = null;
        try {
            this.checkParameters(params);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String id = params.getProperty("customerId");
        Customer tempCustomer = office.getCustomer(id);
        String licensePlate = params.getProperty("licensePlate");
        switch (params.getProperty("carType").toUpperCase()){
            case "COMPACT" -> {
                type = CarType.COMPACT;
            }
            case "SUV" -> {
                type = CarType.SUV;
            }
        }
        Car c = new Car(type, licensePlate, tempCustomer);
        return office.register(c);
    }
    
    @Override
    public String getCommandName(){
        return "CAR";
    }
    
    @Override
    public String getDisplayName(){
        return "";
    }
}
