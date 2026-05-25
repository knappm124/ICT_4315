/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.command;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.serialization.ParkingResponse;
import edu.du.ict4315.parking.support.ParameterCheckUtilities;
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
    private final ParameterCheckUtilities check;
    
    private static final Logger logger = Logger.getLogger(RegisterCarCommand.class.getName());
    
    public RegisterCarCommand(RealParkingOffice office){
        this.office = office;
        check = new ParameterCheckUtilities(office);
    }
    
    public void checkParameters(Properties params) throws IOException {
        //Check parameters exist and are valid
        String temp;
        Boolean error = false;
        List<String> expected = Arrays.asList("customer","license");
        for(String e : expected){
            if (!params.contains(e)){
                temp = e + "  is not valid";
                logger.severe(temp);
                error = true;
            } else if (params.getProperty(e).isBlank()){
                temp = e + "  is blank";
                logger.severe(temp);
                error = true;
            }
        }
        if (office.getCustomer(params.getProperty("customer")) == null){
            logger.severe("Customer id does not exist");
            error = true;
        }
        if(error){
            throw new IOException("Parameters are not valid, see log for more details");
        }
    }
    
    @Override
    public ParkingResponse execute(Properties params){
         // Requires a licensePlate and a customer id
        String licensePlate = ParameterCheckUtilities.checkLicensePlate(params.getProperty("license"));
        String customerId = params.getProperty("customer");
        int statusCode;
        String message;
        
        if (licensePlate == null) {
            statusCode = 400;
            message = "Can't register car: missing license";
        } else if (customerId == null) {
            statusCode = 400;
            message = "Can't register car: missing customer id";
        } else {

        Customer customer = check.checkCustomer(customerId);
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setOwner(customer);

        logger.info("Registering car " + car);
        message = office.register(car);
        System.out.println("MESSAGE CHECK: " + message);
        statusCode = 200;
        }
        String json = "{'statuscode':" + statusCode;
        json += ",'message':'";
        json += message;
        json += "'}";
        return new ParkingResponse(json);
    }
    
    @Override
    public String getCommandName(){
        return "CAR";
    }
    
    @Override
    public String getDisplayName(){
        return "Register Car";
    }
}
