/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.util.spark;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class QueryParamsToProperties {

    public static Properties getProperties(String query) {
        String[] params = query.split("&");
        Properties properties = new Properties();

        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            try {
                properties.put(URLDecoder.decode(name, "UTF-8"),
                        URLDecoder.decode(value, "UTF-8"));
            }
            catch (UnsupportedEncodingException ex) {
                Logger.getLogger(QueryParamsToProperties.class.getName()).severe("Can't parse " + param + " :: " + ex.getMessage());
            }
        }

        return properties;
    }
    
    // This version supports multi-valued parameters
    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(key, values);
                    }
                    values.add(value);
                }
            }

            return params;
        }
        catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }
}
