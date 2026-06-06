/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import edu.du.ict4315.parking.support.PropertiesUtilities;

/**
 *
 * @author michael
 */
public final class SparkFormField {
    private final String displayName;
    private final String fieldName;
    
    public SparkFormField(String displayName, String fieldName) {
        this.displayName = displayName;
        this.fieldName = fieldName;
    }
    
    public SparkFormField(String displayName) {
        this.displayName = displayName;
        this.fieldName = PropertiesUtilities.displayNameToPropertyName(displayName.toLowerCase());
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFieldName() {
        return fieldName;
    }
    
}
