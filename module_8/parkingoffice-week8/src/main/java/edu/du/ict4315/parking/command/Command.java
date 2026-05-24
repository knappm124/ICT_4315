/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.du.ict4315.parking.command;
import edu.du.ict4315.parking.serialization.ParkingResponse;
import java.util.Properties;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 * 
 * This is the command interface that will be implemented in the RegisterCarCommand and 
 * RegisterCustomerCommand classes
 */
public interface Command {
    public String getCommandName();
    public String getDisplayName();
    public ParkingResponse execute(Properties params);
}
