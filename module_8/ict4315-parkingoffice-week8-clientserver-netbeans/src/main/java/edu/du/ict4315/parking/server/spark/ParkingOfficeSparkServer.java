/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.server.spark;

import edu.du.ict4315.parking.service.spark.SparkController;
import edu.du.ict4315.parking.service.spark.SparkService;
import edu.du.ict4315.parking.util.spark.CORSUtil;
import java.util.logging.Logger;
import spark.Spark;

/**
 *
 * @author michael
 */
public class ParkingOfficeSparkServer {
    private static final Logger logger = Logger.getLogger(ParkingOfficeSparkServer.class.getName());

    private static int port = 4315;

    public static void main(String[] args) {
        Spark.port(port);
        Spark.staticFiles.location("public");
        CORSUtil.enableCORS("*", "GET, POST", "*");
        logger.info("Starting server on port "+port);
        new SparkController(new SparkService());
    }
}
