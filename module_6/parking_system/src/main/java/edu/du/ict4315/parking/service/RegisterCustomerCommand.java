/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.service;

import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Customer;
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
    private static final Logger logger = Logger.getLogger(RegisterCustomerCommand.class.getName());

    //Checking that first name, last name, phone number, and address exist and that phone number is of valid length
    public void checkParameters(Properties params) throws IOException {
        //check for missing parameters
        String temp;
        Boolean error = false;
        List<String> expected = Arrays.asList("firstName","lastName","phoneNumber","streetAddress1","city","state","zip");
        for(String e : expected){
            if(params.getProperty(e).isBlank()){
                temp = e + " is blank";
                error = true;
                logger.severe(temp);
            }
        }
        //clean up phone number
        String phoneNumber = params.getProperty("phoneNumber");
        phoneNumber = phoneNumber.replaceAll("[ -()]","");
        
        if((phoneNumber.charAt(0) == '1' && phoneNumber.length() != 11) || (phoneNumber.charAt(0) != '1' && phoneNumber.length() != 10)) {
            error = true;
            logger.severe("Phone number is invalid length");
        }
        
        if(error){
           throw new IOException("Parameters are not valid, see log for more details"); 
        }
    }

    @Override
    public String execute(Properties params) {
        //check parameters are valid
        try {
            this.checkParameters(params);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        //Create new customer
        Customer tempCustomer = new Customer();
        
        //Update name and phone number
        tempCustomer.setFirstName(params.getProperty("firstName"));
        tempCustomer.setLastName(params.getProperty("lastName"));
        tempCustomer.setPhoneNumber(params.getProperty("phoneNumber"));
        
        //Use builder to create Address object
        Address tempAddress = new Address.Builder()
                .withStreetAddress1(params.getProperty("streetAddress1"))
                .withStreetAddress2(params.getProperty("streetAddress2"))
                .withCity(params.getProperty("city"))
                .withState(params.getProperty("state"))
                .withZip(params.getProperty("zip"))
                .build();
        tempCustomer.setAddress(tempAddress);
        return office.register(tempCustomer);
    }

    @Override
    public String getCommandName() {
        return "CUSTOMER";
    }

    @Override
    public String getDisplayName() {
        return "";
    }
}
