/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.service;

import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Customer;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 */
public class RegisterCustomerCommand implements Command {

    private RealParkingOffice office = new RealParkingOffice();

    //Checking that first name, last name, phone number, and address exist and that phone number is of valid length
    public void checkParameters(Properties params) throws IOException {
        //check for missing parameters
        if (params.getProperty("firstName").isBlank()) {
            throw new IOException("Customer's first name is missing");
        } else if (params.getProperty("lastName").isBlank()) {
            throw new IOException("Customer's last name is missing");
        } else if (params.getProperty("phoneNumber").isBlank()) {
            throw new IOException("Customer's phone number is missing");
        } else if (params.getProperty("streetAddress1").isBlank()){
            throw new IOException("Customer's street address is missing");
        } else if (params.getProperty("city").isBlank()) {
            throw new IOException("Customer's city is missing");
        } else if (params.getProperty("state").isBlank()) {
            throw new IOException("Customer's state is missing");
        } else if (params.getProperty("zip").isBlank()) {
            throw new IOException("Customer's zip code is missing");
        }
        //clean up phone number
        String phoneNumber = params.getProperty("phoneNumber");
        phoneNumber = phoneNumber.replace(" ","");
        phoneNumber = phoneNumber.replace("-", "");
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "");
        if((phoneNumber.charAt(0) == '1' && phoneNumber.length() != 11) || (phoneNumber.charAt(0) != '1' && phoneNumber.length() != 10)) {
            throw new IOException("Customer's phone number is invalid");
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
