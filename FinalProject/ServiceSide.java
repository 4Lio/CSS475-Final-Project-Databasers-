package FinalProject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;


// Talks to database
public class ServiceSide {
    Connection connection = null;

    // establish database connection - Implemented by Nolan Kelly
    public void APIBootUp() {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.vdkswcrblirexukuyrwl&password=databases schemas queries";
        String user = "postgres.vdkswcrblirexukuyrwl";
        String password = "databases schemas queries";
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to Supabase successfully!");
            }
            else {
                System.out.println("Connection failure");
            }
        } catch(SQLException e) {
            System.err.println("Connection failure: " + e.getMessage());
        }
    }

    // close database connection - Implemented by Nolan Kelly
    public void APIBootDown() {
        try {
            connection.close();
        } catch(SQLException e) {
            System.err.println("Connection close failure: " + e.getMessage());
        }
    }

    public void TestConnection() {
        String query = "SELECT COUNT(*) FROM DrinkCat";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            //if(result.next());
            while(result.next()) {
                int id = result.getInt("count");
                System.out.println(id);
            }
        } catch(SQLException e) {
             System.err.println("Query failure: " + e.getMessage());
        }
    }

    // process input from client API and make calls to server API
    public void ProcessInput(ArrayList<String> params) {
        
    }

    // All private

    // Inputs - name, brand, flavor, drink type, price, and SKU
    // Output - prints success or failure message (failure == drink already exists or something went wrong) before returning true or false
    // Purpose - allows managers to add new drink types to the system to be tracked in the inventory system
    private boolean AddDrink() {

        return false;
    }

    // Inputs - SKU or name, brand, flavor, new price
    // Output - prints success or failure (failure == drink doesn’t exists or something went wrong)  message before returning true or false
    // Purpose - allow for a manger to increase/decrease the cost of drinks in the gym as needed (price changes, discounts, etc)
    private boolean UpdateDrinkPrice() {

        return false;
    }
    
    // Inputs - SKU or name, brand, flavor, new isActive status (true or false)
    // Output - prints success or failure (failure == drink doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow for a manager to label drinks in the system as no longer actively sold, will allow those drinks to not be included in 
    // certain reports (inventory scans, etc.)
    private boolean UpdateDrinkStatus() {

        return false;
    }

    // Inputs - agreement number, first name, last name
    // Output -  prints success or failure (failure == member already exists or something went wrong) message before returning true or false
    // Purpose - allow for a new member to be added to our system to track their sale and drink purchase histories
    private boolean AddMember() {

        return false;
    }

    // Inputs - order number (from supplier), supplier email or NULL, order date, estimated arrival date
    // Output - prints success or failure (failure == shipment already exists or something went wrong) message before returning true or false
    // Purpose - allow managers to add new shipment info 
    private boolean AddShipment() {

        return false;
    }

    // Inputs - shipment number, drink info for the shipment in the format: name, brand, flavor, or SKU, quantity, cost of purchase
    // Output - prints success or failure (failure == drink/shipment doesn’t exists or something went wrong)  message before returning true or false
    // Purpose - allows managers to add actual drinks to a shipment that will added to the inventory upon shipment arrival (drinks must be created 
    // in system before adding them to shipment)
    private boolean AddDrinkToShipment() {

        return false;
    }
    
    // Inputs - shipment number/shipment order number
    // Output - Prints success message (x quantity added to x drink in x location) for each drink in the shipment, or a failure 
    // (failure == shipment doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow the quantities of drinks in the gym to be updated when the shipment arrives to the gym
    private boolean ShipmentArrived() {

        return false;
    }

    // Inputs - SKU, or drink name, brand, flavor, and quantity moved from backstock to display
    // Outputs - prints success or failure (failure == drink doesn’t exists or something went wrong) message displayed before returning true or false
    // Purpose - allow for locations of drink quantities to stay accurate and correctly represent the gym with the system
    private boolean MoveStock() {

        return false;
    }

    // Inputs - none
    // Output - prints info about most recent sale (purchase number, member, date, drinks purchased, total cost) OR prints failure message before 
    // returning true or false 
    // Purpose - allow the most recent purchase to be viewed
    private boolean MostRecentSale() {

        return false;
    }
    
    // Inputs - member agreement number, number of rows (sales) they want returned
    // Output - prints info about members sales (purchase number, date, drinks purchased, total cost) OR prints failure (failure == member doesn’t 
    // exists or something went wrong) message before returning true or false
    // Purpose - analyze member patterns or find specific member transaction
    private boolean GetMemberSales() {

        return false;
    }

    // Inputs -  purchase number
    // Output - prints success or failure (failure == purchase doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow for a sale to be canceled for a refund, etc.
    private boolean CancelSale() {

        return false;
    }
    
    // Inputs - member agreement number or NULL, is void purchase (true or false), drink info: name, brand, flavor, or SKU, quantity
    // Output - prints success or failure (failure == member or drink doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allows for purchases to be registered in the system upon a successful POS checkout (only pulls purchases from display drink location id)
    private boolean MakePurchase() {

        return false;
    }
    
    // Inputs - none
    // Output - prints information about most profitable drink (cost to buy, sell price, margin) or failure message before returning true or false
    // Purpose - find most profitable drink for the gym to sell to make business decisions
    private boolean FindMostProfitableDrink() {

        return false;
    }
    
    // Inputs - year
    // Output - prints information about most profitable month for given year (year, month, profits) or failure (failure == year hasn’t finished or 
    // something went wrong) message before returning true or false
    // Purpose - find most profitable month in a year to make business decisions
    private boolean FindMostProfitableMonth() {

        return false;
    }
    
    // Inputs - none
    // Output - prints information about most profitable year for past 5 years (year, profits) or failure message before returning true or false
    // Purpose - find most profitable year in a 5 year span to make business decisions
    private boolean FindMostProfitableYear() {

        return false;
    }
    
    // Inputs - none
    // Output - prints information about any shipments in the system not yet delivered or failure message before returning true or false
    // Purpose - find any missing shipments or solve stock discrepancies in the system
    private boolean FindUndeliveredShipments() {

        return false;
    }
    
    // Inputs - none
    // Outputs - print information about most costly year in past 5 (year, money spend, margins?) or failure message before returning true or false
    // Purpose - find most expensive spending year recently for business purposes
    private boolean FindMostCostlyYear() {

        return false;
    }
    
    // Inputs - none
    // Outputs - print information about most costly month for the gym from past year (month, money spent, margins?) or failure message before 
    // returning true or false
    // Purpose - find the most expensive month in the past year for the gym for business decisions
    private boolean FindMostCostlyMonth() {

        return false;
    }
    
    // Inputs - none
    // Outputs - prints inventory information about all active drinks in the system (drink, location, quantity in stock) or failure message before 
    // returning true or false
    // Purpose - view quantities of drinks in the gym
    private boolean InventoryScan() {

        return false;
    }
    
    // Inputs - none
    // Outputs - print out any discrepancies between sales and stock (drink, expected stock, actual stock) or failure message before returning true or 
    // false
    // Purpose - identify and prevent shrinkage loss
    private boolean ShrinkageScan() {

        return false;
    }

}