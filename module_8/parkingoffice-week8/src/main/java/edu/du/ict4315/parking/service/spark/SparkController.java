/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import edu.du.ict4315.parking.util.spark.TokenGenerator;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 *
 * @author michael
 */
public class SparkController {

    private static final Logger logger = Logger.getLogger(SparkController.class.getName());

    // The token generator is used to produce a short string
    // that can be used to shut down the server
    public SparkController(final SparkService sparkService) {
        String token = TokenGenerator.generate(6);

        logger.info("Starting SparkParkingController with stop token " + token);

        // TODO: All these services must be replaced.
        //   GET is OK for listing services
        //   POST for changes
        //   Path /po/ in front of all
        //   Path /admin/ for office services
        //   Path /user/ for self-services
        get("/", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("Requested Home");
                return sparkService.getHomePage(request, response);
            }
        });

        get("/po/test", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("Requested server status");
                response.type("text/html;charset=UTF-8");
                return sparkService.getStatus(request, response);
            }
        });

        get("/po/commands", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("Requested command list");
                response.type("text/html;charset=UTF-8");
                return sparkService.getListOfCommands(request, response);
            }
        });

        get("/po/listlots", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                logger.info("Requested parking lot list");
                response.type("text/html;charset=UTF-8");
                return sparkService.getParkingLotList(request, response);
            }
        });

        get("/po/lotdetails", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                logger.info("Requested parking lot list");
                response.type("text/html;charset=UTF-8");
                return sparkService.getParkingLotListDetail(request, response);
            }
        });

        get("/po/customers", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                logger.info("Requested customer list");
                response.type("text/html;charset=UTF-8");
                return sparkService.getCustomers(request, response);
            }
        });

        get("/po/customerdetails", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                logger.info("Requested customer list");
                response.type("text/html;charset=UTF-8");
                return sparkService.getCustomerDetails(request, response);
            }
        });
        post("/user/newcustomer", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("New customer request: " + request.queryParams());
                response.type("text/html;charset=UTF-8");
                return sparkService.newCustomer(request, response);
            }
        });
        
        post("/user/command/:command_name", new Route() {
          @Override
          public Object handle(Request request, Response response) {
            logger.info("Command "+request.pathInfo());
            return sparkService.getCommand(request, response);
          }
          
        });

        // STOP command
        get("/stop", (request, response) -> {
            if (request.queryParamOrDefault("token", "Not provided").equalsIgnoreCase(token)) {
                logger.info("Request to stop server accepted from " + request.ip());
                Spark.stop();
                return "Server stopped!";
            }
            logger.info("Body: " + request.body() + "; param: " + request.queryParamOrDefault("token", "Not provided"));
            return "Server running";
        });

    }

}
