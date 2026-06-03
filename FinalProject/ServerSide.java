package FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;


// Talks to database
public class ServerSide {
    Connection connection = null;

    // establish database connection
    public boolean BootUp() {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.vdkswcrblirexukuyrwl&password=databases schemas queries";
        String user = "postgres.vdkswcrblirexukuyrwl";
        String password = "databases schemas queries";
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Server connection established\n");
                return true;
            }
            else {
                System.out.println("Server connection failure\n");
                return false;
            }
        } catch(SQLException e) {
            System.err.println("Server connection failure: " + e.getMessage() + "\n");
            return false;
        }
    }

    // close database connection
    public void BootDown() {
        try {
            connection.close();
        } catch(SQLException e) {
            System.err.println("Connection close failure: " + e.getMessage());
        }
    }

    // process input from client API and make calls to server API
    public boolean ProcessInput(ArrayList<String> params) {
        if(params.size() > 0) {
            switch(Integer.parseInt(params.get(0))) {
                case 0:
                    return Server_AddMember(null, null, null);
                case 1:
                    return ShipmentArrived();
                case 2:
                    return Server_MoveStock(params.get(1), Integer.parseInt(params.get(2)));
                case 3:
                    return Server_MemberSales(null, 0);
                case 4:
                    return Server_DrinkStats();
                case 5:
                    // return MonthlyProfits();
                    break;
                case 6:
                    //return MonthlyCosts();
                    break;
                case 7:
                    return Server_UpdateDrinkPrice(null, 0);
                case 8:
                    return Server_UpdateDrinkStatus(null, false);
                case 9:
                    // return empty for now
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        return false;
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
    // Implemented by: Leo Nguyen; FUNCTION IS UNTESTED     // IMPLEMENTOR NOTE: Name, brand, and flavor are not needed?
                                                            //                   Check Chenault's comments on schema revisions
                                                            //
                                                            //                   We might need a date parameter if we only want to
                                                            //                   update the price of a drink at a certain shipment date
    private boolean Server_UpdateDrinkPrice(String SKU, double price) {
        try {
            String query = "UPDATE Price "
                         + "SET sell_price = ? "
                         + "WHERE ID = (SELECT ID FROM DrinkCat WHERE SKU = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, SKU);

            preparedStatement.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("Update failed. Possibly invalid SKU");

            return false;
        }
    }
    
    // Inputs - SKU or name, brand, flavor, new isActive status (true or false)
    // Output - prints success or failure (failure == drink doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow for a manager to label drinks in the system as no longer actively sold, will allow those drinks to not be included in 
    // certain reports (inventory scans, etc.)
    // Implemented by: Leo Nguyen; FUNCTION IS UNTESTED
    private boolean Server_UpdateDrinkStatus(String SKU, boolean newStatus) {
        try {
            String query = "UPDATE DrinkCat "
                         + "SET is_active = ? "
                         + "WHERE SKU = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBoolean(1, newStatus);
            preparedStatement.setString(2, SKU);

            preparedStatement.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("Update failed. Possibly invalid SKU");

            return false;
        }
    }

    // Inputs - agreement number, first name, last name
    // Output -  prints success or failure (failure == member already exists or something went wrong) message before returning true or false
    // Purpose - allow for a new member to be added to our system to track their sale and drink purchase histories
    // Implemented by: Leo Nguyen; FUNCTION IS UNTESTED
    private boolean Server_AddMember(String agreementNum, String firstName, String lastName) {
        try {
            String query = "INSERT INTO Member "
                         + "VALUES (?, ?, ?) "
                         + "RETURNING ID";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, agreementNum);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);

            preparedStatement.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("Update failed. Member possibly already exists");

            return false;
        }
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

    // Server_MoveStock -- Moves stock from storage to display for a drink identified by the SKU
    /* Query used to create functions used in this API:
    CREATE OR REPLACE FUNCTION validate_stock_move 
	    (in_sku varchar, in_quantity integer) 
    RETURNS bool
    AS 
    $$
    DECLARE stock integer;
    BEGIN
    SELECT Drink.quantity_in_stock INTO stock
    FROM Drink 
        JOIN DrinkCat ON (DrinkCat.ID = Drink.DrinkCatID) 
        JOIN DrinkLocation ON (DrinkLocation.ID = Drink.DrinkLocationID) 
    WHERE DrinkCat.SKU = in_sku AND DrinkLocation.name = 'Storage'
    FOR UPDATE;

    RETURN in_quantity <= COALESCE(stock, -1);
    END;
    $$ LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION move_stock 
        (in_sku varchar, in_quantity integer)
    RETURNS void
    AS $$
    UPDATE Drink
    SET quantity_in_stock = Drink.quantity_in_stock - in_quantity
    FROM DrinkCat, DrinkLocation
    WHERE Drink.DrinkCatID = DrinkCat.ID 
        AND Drink.DrinkLocationID = DrinkLocation.ID
        AND DrinkCat.SKU = in_sku
        AND DrinkLocation.name = 'Storage';

    UPDATE Drink
    SET quantity_in_stock = Drink.quantity_in_stock + in_quantity
    FROM DrinkCat, DrinkLocation
    WHERE Drink.DrinkCatID = DrinkCat.ID 
        AND Drink.DrinkLocationID = DrinkLocation.ID
        AND DrinkCat.SKU = in_sku 
        AND DrinkLocation.name = 'Display';
    $$ 
    LANGUAGE SQL;
    */
    private boolean Server_MoveStock(String sku, int quantity) {
        boolean outcome = false;
        boolean valid = false;
        String query1 = "SELECT validate_stock_move(?, ?);";
        String qeury2 = "SELECT move_stock(?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(query1)) {
            connection.setAutoCommit(false);
            statement.setString(1, sku);
            statement.setInt(2, quantity);
            try(ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    valid = result.getBoolean("validate_stock_move");    
                }    
            }
            
            if(valid) { // if we are good to procede with the stock move
                try(PreparedStatement statement2 = connection.prepareStatement(qeury2)) {
                    statement2.setString(1, sku);
                    statement2.setInt(2, quantity);
                    statement2.executeQuery();
                    System.out.println("\n" + quantity + " stock moved for " + sku + " from storage to display\n");
                    outcome = true;
                }
            }
            else {  // if too much quantity requested || // bad sku given
                System.err.println("\nBad input\n");
                outcome = false;
            }
        } catch(SQLException e) {
             System.err.println("\nQuery failure: " + e.getMessage() + "\n");
             outcome = false;
        } finally {
            try {
                if(connection.getAutoCommit() == false) {
                    if(outcome) {   // if all was a success/queries went through
                        connection.commit();
                    }
                    else {  // some failure in query
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                System.err.println("\nCommit failure: " + e.getMessage());
            }

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("\nConnection failure resetting auto-commit: " + e.getMessage());
                outcome = false;
            }
        }
        return outcome;
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
    // Implemented by: Leo Nguyen; FUNCTION IS UNTESTED     // IMPLEMENTOR NOTE: Implementation is unfinished (parameters and query)
                                                            //                   Not to sure how to grab the price based on date
    private boolean Server_MemberSales(String agreementNum, int numRows) {
        try {
            String query = "SELECT purchase_number, Purchase.date, drinkID, quantity_purchased, SUM(sell_price)"
                         + "FROM Purchase "
                         + "    JOIN Member ON (Member.ID = Purchase.MemberID "
                         + "    JOIN DrinkToPurchase ON (DrinkToPurchase.PurchaseID = Purchase.ID) "
                         + "    JOIN Drink ON (Drink.ID = DrinkToPurchase.DrinkID) "
                         + "    JOIN DrinkCat ON (DrinkCat.ID = Drink.DrinkCatID) "
                         + "    JOIN Price ON (Price.ID = DrinkCat.ID) "
                         + "WHERE agreement_number = ? "
                         + "LIMIT ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, agreementNum);
            preparedStatement.setInt(2, numRows);

            preparedStatement.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("Update failed. Member possibly doesn't exist");

            return false;
        }
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
    
    // Server_DrinkStats -- Prints information about most profitable drink (current sell price, total stock)
    /* Query used to create get_price function used in this API:
    CREATE OR REPLACE FUNCTION get_price (in_drinkcatid integer)
    RETURNS money
    AS
    $$
    SELECT sell_price
    FROM Price
    WHERE DrinkCatID = in_drinkcatid
    ORDER BY date DESC
    LIMIT 1;
    $$
    LANGUAGE SQL STABLE;
    */
    private boolean Server_DrinkStats() {
        int counter = 0;
        String brand;
        String flavor;
        String type;
        String price;
        int sales;
        String query = """
                        SELECT DrinkCat.brand, DrinkCat.flavor, DrinkType.name AS type, 
	                        get_price(DrinkCat.ID) AS sell_price,
	                        COALESCE(SUM(DrinkToPurchase.quantity_purchased), 0) AS total_sales
                        FROM DrinkCat
                            JOIN DrinkType ON(DrinkType.ID = DrinkCat.DrinkTypeID)
                            JOIN Drink ON(Drink.DrinkCatID = DrinkCat.ID)
                            LEFT JOIN DrinkToPurchase ON(DrinkToPurchase.DrinkID = Drink.ID)
                        GROUP BY DrinkCat.brand, DrinkCat.flavor, DrinkType.name, DrinkCat.ID
                        ORDER BY DrinkCat.ID;
                        """;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            try(ResultSet result = statement.executeQuery()) {
                // print out columns
                System.out.printf("%n%-10s | %-20s | %-10s | %-13s | %-5s%n", "brand", "flavor", "type", "current_price", "total_sales");
                System.out.println("-----------------------------------------------------------------------------");

                while(result.next()) {
                    counter++;
                    brand = result.getString("brand");
                    flavor = result.getString("flavor");
                    type = result.getString("type");
                    price = result.getString("sell_price");
                    sales = result.getInt("total_sales");
                    System.out.printf("%-10s | %-20s | %-10s | %-13s | %-5d%n", brand, flavor, type, price, sales);
                }
                System.out.println();
                if(counter > 0) {
                    return true;
                }
                else {  // counter == 0 -- no rows returned
                    System.out.print("No drinks in the system\n\n");
                    return false;
                }
            }
           
        } catch(SQLException e) {
             System.err.println("Query failure: " + e.getMessage() + "\n");
             return false;
        }
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