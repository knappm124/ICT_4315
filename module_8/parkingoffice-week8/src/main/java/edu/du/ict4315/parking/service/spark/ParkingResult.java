/*
 * Course: ICT4315
 * File: ParkingResult.java
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.util.logging.Logger;

/**
 * A parking result comprises a result code, and a result message.
 * If a failure occurs, a failure message is also provided.
 * Codes are intended to follow HTTP codes:
 * 100: success
 * 500: failure
 * @author michael
 */
public class ParkingResult {

    private static final Logger logger = Logger.getLogger(SparkController.class.getName());

    private int resultCode;
    private String result;
    private String failureMessage;

    public static ParkingResult fromJsonString(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ParkingResult parkingResult = new ParkingResult();
        try {
            parkingResult = gson.fromJson(jsonString, ParkingResult.class);
        }
        catch (JsonParseException exc) {
            parkingResult.resultCode = 500;
            parkingResult.failureMessage = "Cannot parse result: " + exc;
            logger.severe("Cannot parse JSON: " + exc);
        }
        return parkingResult;
    }

    public String toPrettyString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    private ParkingResult() {
        resultCode = 100;
        result = "";
        failureMessage = "";
    }

    public ParkingResult(int resultCode, String result, String failureMessage) {
        this.resultCode = resultCode;
        this.result = result;
        this.failureMessage = failureMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailure_message() {
        return failureMessage;
    }

    public void setFailure_message(String failure_message) {
        this.failureMessage = failure_message;
    }
    
    public ParkingResult(int resultCode, String result) {
        this(resultCode, result, "");
    }
}
