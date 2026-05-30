/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.server;

import edu.du.ict4315.parking.service.ParkingService;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author melyg
 */
public class Client implements Runnable {
    ParkingService service;
    Socket socket;
    
    public Client(ParkingService service, Socket socket){
        this.service = service;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            String output;
            try {
                output = service.handleInput(socket.getInputStream());
            } catch (RuntimeException ex) {
                output = ex.getMessage();
            }
            pw.println(output);
            pw.println("end");
            pw.flush();
            
            socket.close();
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
        }
    }
}
