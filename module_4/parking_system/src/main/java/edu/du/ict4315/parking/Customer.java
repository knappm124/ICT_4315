 ////////////////////
// The Customer class represents a parker in the Parking system
// File: Customer.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.parking.support.IdMaker;

public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;

    private Customer(CustomerBuilder customerBuilder) {
        this.id = customerBuilder.id;
        this.firstName = customerBuilder.firstName;
        this.lastName = customerBuilder.lastName;
        this.phoneNumber = customerBuilder.phoneNumber;
        this.address = customerBuilder.address;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerName() {
        return firstName + " " + lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer id: ");
        sb.append(id);
        sb.append("\n");
        sb.append(lastName);
        sb.append(", ");
        sb.append(firstName);
        sb.append("\n");
        sb.append(address.toString());
        sb.append("\n");
        sb.append(phoneNumber);
        return sb.toString();
    }

    public static class CustomerBuilder {
       
        private String id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Address address;   
        
        public CustomerBuilder(String firstName, String lastName) {
            this.id = IdMaker.makeId("CUST-1");
            this.firstName = firstName;
            this.lastName = lastName;
            
            if (firstName == null || lastName == null){
                throw new IllegalArgumentException("First name and last name cannot be null");
            }
        }
        
        public CustomerBuilder withPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        
        public CustomerBuilder withAddress(Address address){
            this.address = address;
            return this;
        }
        
        public Customer build(){
            return new Customer(this);
        }
    }
}
