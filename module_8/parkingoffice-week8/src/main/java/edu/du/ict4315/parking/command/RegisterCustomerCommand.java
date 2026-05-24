/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.support.ParameterCheckUtilities;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 */
public class RegisterCustomerCommand implements Command {

    private RealParkingOffice office = new RealParkingOffice();
    private ParameterCheckUtilities check;
    private static final Logger logger = Logger.getLogger(RegisterCustomerCommand.class.getName());

    public RegisterCustomerCommand(RealParkingOffice office){
        this.office = office;
        check = new ParameterCheckUtilities(office);
    }
    
    //Checking that first name, last name, and phone number exist and that phone number is of valid length
    public void checkParameters(Properties params) throws IOException {
        //check for missing parameters
        String temp;
        Boolean error = false;
        List<String> expected = Arrays.asList("firstname","lastname","phonenumber");
        for(String e : expected){
            if(params.getProperty(e).isBlank()){
                temp = e + " is blank";
                error = true;
                logger.severe(temp);
            }
        }
       
        //clean up phone number
        String phonenumber = params.getProperty("phonenumber");
        phonenumber = phonenumber.replaceAll("[ -()]","");
        
        if((phonenumber.charAt(0) == '1' && phonenumber.length() != 11) || (phonenumber.charAt(0) != '1' && phonenumber.length() != 10)) {
            error = true;
            logger.severe("Phone number is invalid length");
        }
        
        if(params.contains("zip")){
            String zip = params.getProperty("zip");
            if(zip.length() != 5){
                throw new IOException("Zip code must be 5 digits long");
            }
        }
        
        if(error){
           throw new IOException("Parameters are not valid, see log for more details"); 
        }
    }

    @Override
    public String execute(Properties params) {
        // Ensure enough info is present to create a Customer (lastName)
        String lastName = ParameterCheckUtilities.checkName(params.getProperty("lastname"));

        if (lastName == null || lastName.isBlank()) {
            logger.info("Cannot create customer without an acceptable last name");
            logger.info("Properties: " + params);
            throw new IllegalArgumentException("Missing last name for customer");
        }
        // Create the customer (note that here a Builder pattern for customer might help
        Customer customer = new Customer();
        customer.setFirstName(ParameterCheckUtilities.checkName(params.getProperty("firstname", "")));
        customer.setLastName(ParameterCheckUtilities.checkName(params.getProperty("lastname")));
        customer.setPhoneNumber(ParameterCheckUtilities.checkName(params.getProperty("phonenumber", "")));
        logger.info("Registering customer " + customer);
        // Register the customer
        return office.register(customer);
    }

    @Override
    public String getCommandName() {
        return "CUSTOMER";
    }

    @Override
    public String getDisplayName() {
        return "Register Customer";
    }
}
