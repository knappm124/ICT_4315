/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.service.spark;

import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.command.Command;
import edu.du.ict4315.parking.command.DefaultCommand;
import edu.du.ict4315.parking.command.ListCustomerCommand;
import edu.du.ict4315.parking.command.ListParkingLotCommand;
import edu.du.ict4315.parking.command.ParkCommand;
import edu.du.ict4315.parking.command.RegisterCarCommand;
import edu.du.ict4315.parking.command.RegisterCustomerCommand;
import edu.du.ict4315.parking.util.spark.QueryParamsToProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author michael
 */
public class SparkService {
  // TODO: Differential validate and authorize (authorize not used at this time)

  // TODO: Establish endpoints for services
  //  *Start with status services:
  //   List of customer ids
  //   List of parking permit ids
  //   Parking office address
  // *Next add registration services
  //   register car
  //   register customer
  //   register parking lot
  //   unregister parking lot
  // *Add parking lot sensor
  //  park
  //  leave
  private static final Logger logger = Logger.getLogger(SparkService.class.getName());

  protected final RealParkingOffice parkingOffice;
  private final SparkCommandManager commandManager = new SparkCommandManager();

  public SparkService(RealParkingOffice office) {
    parkingOffice = office;

    // Register the known commands (missing CHARGES)
    register(new RegisterCarCommand(parkingOffice));
    register(new RegisterCustomerCommand(parkingOffice));
    register(new ParkCommand(parkingOffice));
    register(new ListCustomerCommand(parkingOffice));
    register(new ListParkingLotCommand(parkingOffice));

  }

  // It's better to inject a parking office
  public SparkService() {
    this(new RealParkingOffice());
  }

  // Map of the command names to their implementations
  private Map<String, Command> commands = new TreeMap<>();

  private void register(Command command) {
    commands.put(command.getCommandName(), command);
  }

/////////////////////////////////
// This section emulates the command line client
// It accepts commands (from HTTP urlencoding).
// It can be invoked with curl or wget. Results are in JSON
//  Example commands:
// curl -d 'firstname=Michael&lastname=Smithson&phonenumber=333-444-2221' http://127.0.0.1:4315/user/command/customer
//   Creates a customer (assumes server is running on the local host)
//   Result might be:   {"resultCode":100,"result":"CUST2","failureMessage":""}
// The last path portion of the URL is the command name  
/////////////////////////////////
  public ParkingResult performCommand(SparkParkingCommand command) {
    int code = 100; // OK
    String failureMessage = "";
    // Look up the command
    logger.info("Received command: " + command);

    Command cmd = commands.get(command.getCommandName());
    if (cmd == null) {
      cmd = new DefaultCommand(command.getCommandName());
      code = 400;
    }

    // Execute the command with the properties
    String result = "";
    try {
      result = cmd.execute(command.getCommandParameters());
    } catch (NullPointerException ex) {
      logger.severe("Command execution failure: " + ex.getLocalizedMessage());
      failureMessage = ex.getLocalizedMessage();
      code = 500;
    }

    ParkingResult parkingResult = new ParkingResult(code, result, failureMessage);
    return parkingResult;
  }

  public ParkingResult getCommand(Request request, Response response) {
    // Command is from the last component of the URL
    String path = request.pathInfo();
    String[] components = path.split("/");
    String command = components[components.length - 1].toUpperCase();
    // Parameters are from parsing the request query parameters
    Properties properties = new Properties();
    for (String key : request.queryParams()) {
      properties.setProperty(key, request.queryParams(key));
    }
    logger.info("Request for general command: " + command + " with parameters: " + properties);
    return performCommand(new SparkParkingCommand(command, properties));

    // return command + " :: "+properties.toString(); // Temporary
  }

  ///////////////////////////////////////////////////////////
  public String getHomePage(Request request, Response response) {
    Map<String, Object> model = Map.of("name", request.params("name"));
    return new ThymeleafTemplateEngine().render(
        new ModelAndView(model, "views/index")
    );
  }

  public String[] listCommands() {
    return commands.keySet().toArray(String[]::new);
  }

  public String getListOfCommands(Request request, Response response) {
    Map<String, Object> model = Map.of("commands", listCommands());
    String result
        = new ThymeleafTemplateEngine().render(
            new ModelAndView(model, "views/commands")
        );
    return result;
  }

  public boolean isRunning() {
    return true;
  }

  public String getStatus(Request request, Response response) {
    Map<String, Object> model = Map.of("status", isRunning() ? "Parking Office is Running" : "");
    logger.info("Request to check SparkService");
    return new ThymeleafTemplateEngine().render(
        new ModelAndView(model, "views/running")
    );
  }

  public String getParkingLotList(Request request, Response response) {
    String[] lotIds = parkingOffice.getLotIds();
    Map<String, Object> model = Map.of("lots", lotIds);
    String result
        = new ThymeleafTemplateEngine().render(
            new ModelAndView(model, "views/lotids")
        );
    return result;
  }

  public String getParkingLotListDetail(Request request, Response response) {
    String[] lotIds = parkingOffice.getLotIds();
    List<ParkingLot> lots = new ArrayList<>();
    for (String lotId : lotIds) {
      ParkingLot lot = parkingOffice.getParkingLot(lotId);
      lots.add(lot);
    }
    Map<String, Object> model = Map.of("lotdetails", lots);
    String result
        = new ThymeleafTemplateEngine().render(
            new ModelAndView(model, "views/lots")
        );
    return result;
  }

  // TODO: Merge form fields into the commands
  public String getCustomers(Request request, Response response) {
    String[] customerIds = parkingOffice.getCustomerIds();
    // Fields could include address fields as well.
    SparkFormField[] formFields = {
      new SparkFormField("First Name"),
      new SparkFormField("Last Name"),
      new SparkFormField("Phone Number")
    };

    Map<String, Object> model = Map.of("customers", customerIds,
        "formFields", formFields);

    String result = new ThymeleafTemplateEngine().render(
        new ModelAndView(model, "views/customers")
    );
    return result;
  }

  public String getCustomerDetails(Request request, Response response) {
    String[] customerIds = parkingOffice.getCustomerIds();
    // Fields could include address fields as well.
    SparkFormField[] formFields = {
      new SparkFormField("First Name"),
      new SparkFormField("Last Name"),
      new SparkFormField("Phone Number")
    };
    List<Customer> customers = new ArrayList<>();
    for (String id : customerIds) {
      customers.add(parkingOffice.getCustomer(id));
    }

    Map<String, Object> model = Map.of("customers", customers,
        "formFields", formFields);

    String result = new ThymeleafTemplateEngine().render(
        new ModelAndView(model, "views/customerDetails")
    );
    return result;
  }

  public String newCustomer(Request request, Response response) {
    logger.info(request.queryParamOrDefault("firstname", "First name missing"));
    logger.info(request.queryParamOrDefault("lastname", "Last name missing"));
    logger.info(request.queryParamOrDefault("phonenumber", "phone number missing"));

    Customer customer = new Customer();
    customer.setFirstName(request.queryParamOrDefault("firstname", "missing"));
    customer.setLastName(request.queryParamOrDefault("lastname", "missing"));
    customer.setPhoneNumber(request.queryParamOrDefault("phonenumber", ""));
    String id = parkingOffice.register(customer);
    logger.info("New customer created with id " + id);

    String[] customerIds = parkingOffice.getCustomerIds();
    List<Customer> customers = new ArrayList<>();
    for (String cid : customerIds) {
      customers.add(parkingOffice.getCustomer(cid));
    }
    SparkFormField[] formFields = {
      new SparkFormField("First Name"),
      new SparkFormField("Last Name"),
      new SparkFormField("Phone Number")
    };

    Map<String, Object> model = Map.of("customers", customers,
        "formFields", formFields);

    String result = new ThymeleafTemplateEngine().render(
        new ModelAndView(model, "views/customerdetails")
    );
    return result;
  }
}
