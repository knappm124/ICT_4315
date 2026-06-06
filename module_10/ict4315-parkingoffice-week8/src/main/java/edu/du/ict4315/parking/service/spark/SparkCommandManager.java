/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * This class manages a registered list of SparkComands
 * @author michael
 */
public class SparkCommandManager {
  Map<String,SparkParkingCommand> commands = new TreeMap<>();
  
  public void register(SparkParkingCommand command) {
    commands.put(command.getCommandName(), command);
  }
  
  public SparkParkingCommand get(String commandName) {
    return commands.get(commandName);
  }
  
  public String[] listRegisteredCommands() {
    return commands.keySet().toArray(String[]::new);
  }
  
  public Properties getCommandParameters(String commandName) {
    SparkParkingCommand command = get(commandName);
    if ( command != null ) {
      return command.getCommandParameters();
    }
    return new Properties();
  }
  
}
