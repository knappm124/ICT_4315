/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class CustomerTest {
    
    public CustomerTest() {
    }

    /**
     * Test of getId method, of class Customer.
     */
    @Test
    public void testGetId() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        String result = customer.getId();
        String expResult = "CUST-14";
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Customer.
     */
    @Test
    public void testSetId() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        customer.setId("CUST-2");
        String expResult = "CUST-2";
        String result = customer.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstName method, of class Customer.
     */
    @Test
    public void testGetFirstName() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        String result = customer.getFirstName();
        String expResult = "Jessica";
        assertEquals(expResult, result);
    }

    /**
     * Test of setFirstName method, of class Customer.
     */
    @Test
    public void testSetFirstName() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        customer.setFirstName("Jennifer");
        String result = customer.getFirstName();
        String expResult = "Jennifer";
        assertEquals(expResult, result);
    }

    /**
     * Test of getLastName method, of class Customer.
     */
    @Test
    public void testGetLastName() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        String result = customer.getLastName();
        String expResult = "Jones";
        assertEquals(expResult, result);
    }

    /**
     * Test of setLastName method, of class Customer.
     */
    @Test
    public void testSetLastName() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        customer.setLastName("Smith");
        String result = customer.getLastName();
        String expResult = "Smith";
        assertEquals(expResult, result);
    }

    /**
     * Test of getCustomerName method, of class Customer.
     */
    @Test
    public void testGetCustomerName() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        String result = customer.getCustomerName();
        String expResult = "Jessica Jones";
        assertEquals(expResult, result);
    }

    /**
     * Test of getPhoneNumber method, of class Customer.
     */
    @Test
    public void testGetPhoneNumber() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones")
                .withPhoneNumber("5382749274")
                .build();
        String result = customer.getPhoneNumber();
        String expResult = "5382749274";
        assertEquals(expResult, result);
    }

    /**
     * Test of setPhoneNumber method, of class Customer.
     */
    @Test
    public void testSetPhoneNumber() {
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones").build();
        customer.setPhoneNumber("3825673859");
        String result = customer.getPhoneNumber();
        String expResult = "3825673859";
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddress method, of class Customer.
     */
    @Test
    public void testGetAddress() {
        Address address = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("25167")
                .build();
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones")
                .withAddress(address)
                .build();
        String result = customer.getAddress().toString();
        String expResult = address.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAddress method, of class Customer.
     */
    @Test
    public void testSetAddress() {
        Address address = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("25167")
                .build();
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones")
                .build();
        customer.setAddress(address);
        String result = customer.getAddress().toString();
        String expResult = address.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Customer.
     */
    @Test
    public void testToString() {
        Address address = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("25167")
                .build();
        Customer customer = new Customer.CustomerBuilder("Jessica","Jones")
                .withPhoneNumber("3528374852")
                .withAddress(address)
                .build();
        String result = customer.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("Customer id: ");
        sb.append("CUST-16");
        sb.append("\n");
        sb.append("Jones");
        sb.append(", ");
        sb.append("Jessica");
        sb.append("\n");
        sb.append(address.toString());
        sb.append("\n");
        sb.append("3528374852");
        String expResult =  sb.toString();
        assertEquals(expResult, result);
    }
    
}
