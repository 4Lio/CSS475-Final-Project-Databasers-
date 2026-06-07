package FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
                    return Server_ShipmentArrived(params.get(1));
                case 2:
                    return Server_MoveStock(params.get(1), Integer.parseInt(params.get(2)));
                case 3:
                    return Server_MemberSales(null, 0);
                case 4:
                    return Server_DrinkStats();
                case 5:
                    return Server_MonthlyProfits(Integer.parseInt(params.get(1)));
                case 6:
                    return Server_MonthlyCosts(Integer.parseInt(params.get(1)));
                case 7:
                    return Server_UpdateDrinkPrice(null, 0);
                case 8:
                    return Server_UpdateDrinkStatus(null, false);
                case 9:
                    return Server_InventoryScan();
                case 10:
                    return Server_MostPurchases();
                case 11:
                    return Server_FindUndeliveredShipments();
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

    // Inputs - order number (from supplier), supplier email, order date, estimated arrival date, cost of order, all drinks in the shipment => SKU, 
    // quantity. The pair (supplierEmail, orderNumber) uniquely identifies a shipment. 
    // Output - prints success or failure (failure == shipment already exists or something went wrong) message before returning true or false
    // Purpose - allow managers to add new shipment info
    // combines addshipment and adddrinktoshipment apis
    private boolean AddShipment() {

        return false;
    }
    
    // Inputs - shipment number/shipment order number
    // Output - Prints success message (x quantity added to x drink in x location) for each drink in the shipment, or a failure 
    // (failure == shipment doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow the quantities of drinks in the gym to be updated when the shipment arrives to the gym
    private boolean Server_ShipmentArrived(String orderNumber) {
boolean outcome = false;

    String findShipmentSql = """
        SELECT id, arrived_date
        FROM shipment
        WHERE order_number = ?
        FOR UPDATE;
    """;

    String getStorageLocationSql = """
        SELECT id
        FROM drinklocation
        WHERE name = 'Storage';
    """;

    String getShipmentItemsSql = """
        SELECT
            stdc.drinkcatid,
            dc.sku,
            dc.brand,
            dc.flavor,
            stdc.quantity_shipped
        FROM shipmenttodrinkcat stdc
        JOIN drinkcat dc
            ON dc.id = stdc.drinkcatid
        WHERE stdc.shipmentid = ?;
    """;

    String updateStockSql = """
        UPDATE drink
        SET quantity_in_stock = quantity_in_stock + ?
        WHERE drinkcatid = ?
          AND drinklocationid = ?;
    """;

    String insertStockSql = """
        INSERT INTO drink(drinkcatid, drinklocationid, quantity_in_stock)
        VALUES (?, ?, ?);
    """;

    String markArrivedSql = """
        UPDATE shipment
        SET arrived_date = CURRENT_TIMESTAMP
        WHERE id = ?;
    """;

    try {
        connection.setAutoCommit(false);

        int shipmentId;
        Timestamp arrivedDate;

        // 1. Find and lock shipment
        try (PreparedStatement stmt = connection.prepareStatement(findShipmentSql)) {
            stmt.setString(1, orderNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("ShipmentArrived failed: shipment order number not found.");
                    outcome = false;
                    return false;
                }

                shipmentId = rs.getInt("id");
                arrivedDate = rs.getTimestamp("arrived_date");

                if (rs.next()) {
                    System.out.println("ShipmentArrived failed: multiple shipments have this order number.");
                    System.out.println("Use supplier email also if order numbers are not unique.");
                    outcome = false;
                    return false;
                }
            }
        }

        if (arrivedDate != null) {
            System.out.println("ShipmentArrived failed: this shipment has already arrived.");
            outcome = false;
            return false;
        }

        // 2. Get Storage location ID
        int storageLocationId;

        try (PreparedStatement stmt = connection.prepareStatement(getStorageLocationSql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                System.out.println("ShipmentArrived failed: Storage location does not exist.");
                outcome = false;
                return false;
            }

            storageLocationId = rs.getInt("id");
        }

        // 3. Get drinks in shipment and add them to Storage
        int itemCount = 0;

        try (PreparedStatement itemStmt = connection.prepareStatement(getShipmentItemsSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateStockSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertStockSql)) {

            itemStmt.setInt(1, shipmentId);

            try (ResultSet rs = itemStmt.executeQuery()) {
                while (rs.next()) {
                    itemCount++;

                    int drinkCatId = rs.getInt("drinkcatid");
                    String sku = rs.getString("sku");
                    String brand = rs.getString("brand");
                    String flavor = rs.getString("flavor");
                    int quantity = rs.getInt("quantity_shipped");

                    // Try to update existing Storage stock row
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, drinkCatId);
                    updateStmt.setInt(3, storageLocationId);

                    int rowsUpdated = updateStmt.executeUpdate();

                    // If no existing stock row, create one
                    if (rowsUpdated == 0) {
                        insertStmt.setInt(1, drinkCatId);
                        insertStmt.setInt(2, storageLocationId);
                        insertStmt.setInt(3, quantity);
                        insertStmt.executeUpdate();
                    }

                    System.out.println(quantity + " units added to Storage for "
                            + sku + " (" + brand + " " + flavor + ")");
                }
            }
        }

        if (itemCount == 0) {
            System.out.println("ShipmentArrived failed: shipment has no drinks.");
            outcome = false;
            return false;
        }

        // 4. Mark shipment as arrived
        try (PreparedStatement stmt = connection.prepareStatement(markArrivedSql)) {
            stmt.setInt(1, shipmentId);
            stmt.executeUpdate();
        }

        outcome = true;
        System.out.println("Shipment " + orderNumber + " marked as arrived.");
        return true;

    } catch (SQLException e) {
        System.out.println("ShipmentArrived failed: " + e.getMessage());
        outcome = false;
        return false;

    } finally {
        try {
            if (!connection.getAutoCommit()) {
                if (outcome) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            System.out.println("ShipmentArrived transaction failed: " + e.getMessage());
        }

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Failed to reset auto commit: " + e.getMessage());
        }
    }
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
// Outputs - prints month, total units sold, and total revenue
// Purpose - Shows how much revenue was made from drink sales for each month
//           in the selected year.
public boolean Server_MonthlyProfits(int year) {

    String revenueSql = """
        SELECT
            COALESCE(SUM(dtp.quantity_purchased), 0) AS units_sold,
            COALESCE(SUM(dtp.quantity_purchased * get_price(dc.id)::numeric), 0) AS total_revenue
        FROM purchase p
        JOIN drinktopurchase dtp
            ON dtp.purchaseid = p.id
        JOIN drink d
            ON d.id = dtp.drinkid
        JOIN drinkcat dc
            ON dc.id = d.drinkcatid
        WHERE p.date >= ?
          AND p.date < ?
          AND COALESCE(p.void_purchase, false) = false;
    """;

    try (PreparedStatement stmt = connection.prepareStatement(revenueSql)) {

        boolean found = false;

        System.out.println("Monthly Revenue for " + year);
        System.out.println("---------------------------------------------");
        System.out.printf("%-10s %-15s %-20s%n",
                "Month", "Units Sold", "Total Revenue");

        for (int month = 1; month <= 12; month++) {

            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1);

            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int unitsSold = rs.getInt("units_sold");
                    double totalRevenue = rs.getDouble("total_revenue");

                    if (unitsSold > 0 || totalRevenue > 0) {
                        found = true;

                        System.out.printf("%-10d %-15d $%-19.2f%n",
                                month,
                                unitsSold,
                                totalRevenue);
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No revenue records found for year " + year + ".");
            return false;
        }

        return true;

    } catch (SQLException e) {
        System.out.println("MonthlyRevenue failed: " + e.getMessage());
        return false;
    }
}
    
    // Inputs - none
    // Output - prints information about any shipments in the system not yet delivered or failure message before returning true or false
    // Purpose - find any missing shipments or solve stock discrepancies in the system
    private boolean Server_FindUndeliveredShipments() {
    String query = """
        SELECT
            s.order_number AS order_number,
            sup.name AS supplier_name,
            sup.email AS supplier_email,
            s.order_date AS order_date,
            s.estimated_arrival_date AS estimated_arrival_date
        FROM shipment s
        JOIN supplier sup ON s.supplierid = sup.id
        WHERE s.arrived_date IS NULL
        ORDER BY s.estimated_arrival_date;
    """;

    try (PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        boolean found = false;

        System.out.println("Undelivered Shipments");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-20s %-20s %-25s %-20s %-20s%n",
                "Order Number",
                "Supplier",
                "Supplier Email",
                "Order Date",
                "Estimated Arrival");

        while (rs.next()) {
            found = true;

            String orderNumber = rs.getString("order_number");
            String supplierName = rs.getString("supplier_name");
            String supplierEmail = rs.getString("supplier_email");
            String orderDate = rs.getString("order_date");
            String estimatedArrivalDate = rs.getString("estimated_arrival_date");

            System.out.printf("%-20s %-20s %-25s %-20s %-20s%n",
                    orderNumber,
                    supplierName,
                    supplierEmail,
                    orderDate,
                    estimatedArrivalDate);
        }

        if (!found) {
            System.out.println("No undelivered shipments found.");
        }

        return true;

    } catch (SQLException e) {
        System.out.println("UndeliveredShipments failed: " + e.getMessage());
        return false;
    }
}
    
// Inputs - year
// Outputs - prints month, shipment count, total shipment cost, total drink units purchased
// Purpose - Shows how much money was spent on drink shipments for each month
//           in the selected year.
public boolean Server_MonthlyCosts(int year) {

    String shipmentCostSql = """
        SELECT
            COUNT(*) AS shipment_count,
            COALESCE(SUM(shipment_cost::numeric), 0) AS total_cost
        FROM shipment
        WHERE order_date >= ?
          AND order_date < ?;
    """;

    String unitsSql = """
        SELECT
            COALESCE(SUM(stdc.quantity_shipped), 0) AS total_units
        FROM shipment s
        JOIN shipmenttodrinkcat stdc
            ON stdc.shipmentid = s.id
        WHERE s.order_date >= ?
          AND s.order_date < ?;
    """;

    try (PreparedStatement shipmentStmt = connection.prepareStatement(shipmentCostSql);
         PreparedStatement unitsStmt = connection.prepareStatement(unitsSql)) {

        boolean found = false;

        System.out.println("Monthly Costs for " + year);
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-18s %-20s %-20s%n",
                "Month", "Shipments", "Total Cost", "Units Purchased");

        for (int month = 1; month <= 12; month++) {

            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1);

            shipmentStmt.setDate(1, java.sql.Date.valueOf(startDate));
            shipmentStmt.setDate(2, java.sql.Date.valueOf(endDate));

            unitsStmt.setDate(1, java.sql.Date.valueOf(startDate));
            unitsStmt.setDate(2, java.sql.Date.valueOf(endDate));

            int shipmentCount = 0;
            double totalCost = 0.0;
            int totalUnits = 0;

            try (ResultSet rs = shipmentStmt.executeQuery()) {
                if (rs.next()) {
                    shipmentCount = rs.getInt("shipment_count");
                    totalCost = rs.getDouble("total_cost");
                }
            }

            try (ResultSet rs = unitsStmt.executeQuery()) {
                if (rs.next()) {
                    totalUnits = rs.getInt("total_units");
                }
            }

            if (shipmentCount > 0 || totalUnits > 0 || totalCost > 0) {
                found = true;

                System.out.printf("%-10d %-18d $%-19.2f %-20d%n",
                        month,
                        shipmentCount,
                        totalCost,
                        totalUnits);
            }
        }

        if (!found) {
            System.out.println("No shipment cost records found for year " + year + ".");
            return false;
        }

        return true;

    } catch (SQLException e) {
        System.out.println("MonthlyCosts failed: " + e.getMessage());
        return false;
    }
}
    
    // Inputs - none
    // Outputs - prints inventory information about all active drinks in the system (brand, flavor, location, quantity in stock) or failure message before
    // returning true or false
    // Purpose - view quantities of drinks in the gym
    // Implemented by: Codie Aragon
    private boolean Server_InventoryScan() {
        String query = """
                       SELECT brand, flavor, get_location_name(drinklocationid) AS location, quantity_in_stock
                       FROM Drink D
                           JOIN DrinkCat DC ON (DC.id = D.drinkcatid)
                       WHERE DC.is_active = true
                       ORDER BY brand, flavor, location;
                       """;
        try {
            PreparedStatement statement =  connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            System.out.printf("%n%-15s | %-20s | %-15s | %-20s%n", "brand", "flavor", "location", "quantity_in_stock");
            System.out.println("----------------------------------------------------------------------------");

            while(result.next()) {
                String brand = result.getString("brand");
                String flavor = result.getString("flavor");
                String location = result.getString("location");
                int quantity = result.getInt("quantity_in_stock");

                System.out.printf("%n%-15s | %-20s | %-15s | %-20s%n", brand, flavor, location, quantity);
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Query failure: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Inputs - none
    // Outputs - list of 10 members with the most purchases - name, agreement number, purchase count or failure message
    // Purpose - Identify the members who make the most purchases at the gym to give them rewards, etc.
    private boolean Server_MostPurchases() {
        String query = """
                       SELECT first_name, last_name, agreement_number, purchase_count
                       FROM Member M
                           JOIN (
                               SELECT memberid, count(*) AS purchase_count
                               FROM Purchase
                               GROUP BY memberid
                           ) AS T1 ON (M.id = T1.memberid)
                       ORDER BY purchase_count DESC
                       LIMIT 10;
                       """;
        try {
            PreparedStatement statement =  connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            System.out.printf("%n%-15s | %-15s | %-18s | %-10s%n", "first_name", "last_name", "agreement_number", "purchase_count");
            System.out.println("-----------------------------------------------------------------------");

            while(result.next()) {
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String agreement_number = result.getString("agreement_number");
                String purchase_count = result.getString("purchase_count");

                System.out.printf("%n%-15s | %-15s | %-18s | %-10s%n", first_name, last_name, agreement_number, purchase_count);
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Query failure: " + e.getMessage() + "\n");
            return false;
        }
    }
    
    // Inputs - none
    // Outputs - print out any discrepancies between sales and stock (drink, expected stock, actual stock) or failure message before returning true or 
    // false
    // Purpose - identify and prevent shrinkage loss
    private boolean ShrinkageScan() {

        return false;
    }
}