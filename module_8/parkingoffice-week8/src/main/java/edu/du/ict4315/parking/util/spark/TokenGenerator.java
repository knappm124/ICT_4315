/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.util.spark;

import org.apache.commons.text.RandomStringGenerator;

/**
 *
 * @author michael
 */
public class TokenGenerator {

        private static RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();

        public static String generate(int length) {
                if ( length <= 1 ) {
                        length = 4;
                }
                return generator.generate(length);
        }
}
