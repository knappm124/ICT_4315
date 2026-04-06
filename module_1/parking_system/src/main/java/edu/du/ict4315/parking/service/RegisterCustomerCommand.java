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

    private RealParkingOffice office;

    //Checking that first name, last name, and phone number exist and that phone number is of valid length
    //TODO: Figure out how address will be stored in params and add validity check for it
    public void checkParameters(Properties params) throws IOException {
        //check for missing parameters
        if (params.getProperty("firstName").isBlank()) {
            throw new IOException("Customer's first name is missing");
        } else if (params.getProperty("lastName").isBlank()) {
            throw new IOException("Customer's last name is missing");
        } else if (params.getProperty("phoneNumber").isBlank()) {
            throw new IOException("Customer's phone number is missing");
        }
        //clean up phone number
        String phoneNumber = params.getProperty("phoneNumber");
        phoneNumber = phoneNumber.replace(" ","");
        phoneNumber = phoneNumber.replace("-", "");
        if((phoneNumber.charAt(0) == '1' && phoneNumber.length() != 11) || (phoneNumber.charAt(0) != '1' && phoneNumber.length() != 10)) {
            throw new IOException("Customer's phone number is invalid");
        }
    }

    @Override
    public String execute(Properties params) {
        try {
            this.checkParameters(params);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        Customer tempCustomer = new Customer();
        tempCustomer.setFirstName(params.getProperty("firstName"));
        tempCustomer.setLastName(params.getProperty("lastName"));
        tempCustomer.setPhoneNumber(params.getProperty("phoneNumber"));
        Address tempAddress = new Address.Builder().build();
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
