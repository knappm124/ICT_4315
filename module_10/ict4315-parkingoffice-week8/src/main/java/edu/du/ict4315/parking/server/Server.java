 //////////////////////////
// File: Server.java
// Author: R Judd, modified by M I Schwartz, modified by M Knapp
// This file implements a String message-oriented server to allow clients to send
// commands to the Parking Office server.
// Note: Assignment 8 examines alternate ways of exchanging messages.
// Note: Assignment 9 examines ways of supporting paralellism.
//////////////////////////
package edu.du.ict4315.parking.server;

import edu.du.ict4315.parking.service.ParkingService;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.RealParkingOffice;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "%1$tc %4$-7s (%2$s) %5$s %6$s%n");
    }

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    // Set logging level
    private boolean shutdownServer;
    static {
        logger.setLevel(Level.FINE);
    }
    // Pick a TCP/IP Port
    private final int PORT = 7777;

    private final ParkingService service;

    public Server(ParkingService service) {
        this.service = service;
        shutdownServer = false;
    }

    public void startServer() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        logger.info("Starting server: " + InetAddress.getLocalHost().getHostAddress());
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            while (!shutdownServer) {
                executor.submit(() -> {
                    try {
                        Client handler = new Client(service, serverSocket.accept());
                        handler.run();
                    } catch (IOException ex) {
                        System.getLogger(Server.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        shutdownServer = true;
                    }
                });
            }
            executor.shutdown();
        }
    }
    /**
     * Run this as: $ java ict4315.server.Server
     */
    public static void main(String[] args) throws Exception {
        RealParkingOffice parkingOffice = new RealParkingOffice();
        // Set an address and name
        parkingOffice.setParkingOfficeName("DU Parking Office -- Test");
        Address address;
        address = new Address.Builder().withStreetAddress1("2130 S. High St.")
                .withCity("Denver")
                .withState("CO")
                .withZip("80210")
                .build();
        parkingOffice.setParkingOfficeAddress(address);

        ParkingService service = new ParkingService(parkingOffice);

        new Server(service).startServer();
    }
}
