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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 * 
 * This class will be used to register a car with the parking office and provide a permit
 */
public class RegisterCarCommand implements Command {
    private RealParkingOffice office = new RealParkingOffice();
    
    private static final Logger logger = Logger.getLogger(RegisterCarCommand.class.getName());
    
    public void checkParameters(Properties params) throws IOException {
        //Check parameters exist and are valid
        String temp;
        Boolean error = false;
        List<String> expected = Arrays.asList("customerId","licensePlate","carType");
        for(String e : expected){
            if (params.getProperty(e).isBlank()){
                temp = e + " is blank";
                logger.severe(temp);
                error = true;
            }
        }
        if (!"COMPACT".equals(params.getProperty("carType").toUpperCase()) || !"SUV".equals(params.getProperty("carType").toUpperCase())){
            logger.severe("Car type must be SUV or compact");
            error = true;
        } else if (office.getCustomer(params.getProperty("customerId")) == null){
            logger.severe("Customer id does not exist");
            error = true;
        }
        if(error){
            throw new IOException("Parameters are not valid, see log for more details");
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
