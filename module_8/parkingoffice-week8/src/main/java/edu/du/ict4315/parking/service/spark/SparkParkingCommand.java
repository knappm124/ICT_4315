
/*
 * Course: ICT4315
 * File: SparkParkingCommand.java
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import edu.du.ict4315.parking.util.spark.QueryParamsToProperties;
import java.util.Properties;

/**
 * Here we just mimic the line-oriented ParkingService:
 *   There is a command name and command parameters
 *   Command parameters are expected as name=value (much like HTML URL encoding)
 * @author michael
 */
public class SparkParkingCommand {
    private final String commandName;
    private final Properties commandParameters;
    
    public SparkParkingCommand(String commandName, Properties properties) {
        this.commandName = commandName;
        this.commandParameters = properties;
    }
    
    public static SparkParkingCommand of(String commandName, String urlQuery) {
        Properties props = QueryParamsToProperties.getProperties(urlQuery);
        SparkParkingCommand command = new SparkParkingCommand(commandName,props);
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public Properties getCommandParameters() {
        return commandParameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Command: ");
        sb.append(getCommandName());
        sb.append("; Parameters: ");
        sb.append(getCommandParameters());
        return sb.toString();
    }
    
    // Burn test
    private static String[] queryStrings = {
        "name=Michael%20Schwartz",
        "name=Sam&time=2022-03-22T07:33:12.22",
    };
    
    public static final void main(String[] args) {
        
        for (String query: queryStrings) {
            SparkParkingCommand p = SparkParkingCommand.of("SAMPLE", query);
            System.out.println(p);
        }
    }
}

