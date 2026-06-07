package FinalProject;
import java.util.ArrayList;
import java.util.Scanner;



// Intermediate between user interface and API's in service side
public class ClientSide {

    private final Scanner scanner;
    private final ServerSide server;

    public ClientSide(Scanner scanner, ServerSide server) {
        this.scanner = scanner;
        this.server = server;
    }
    
    // prints API options to the screen for user to read - Implemented by Nolan Kelly
    public void DisplayOptions() {
        System.out.println("Select a API or exit");
        System.out.println("AddMember = 0" + "           ShipmentArrived = 1");
        System.out.println("MoveStock = 2" + "           MemberSales = 3");
        System.out.println("DrinkStats = 4" + "          MonthlyProfits = 5");
        System.out.println("MonthlyCosts = 6" + "        UpdateDrinkPrice = 7");
        System.out.println("UpdateDrinkStatus = 8" + "   InventoryScan = 9");
        System.out.println("MostPurchases = 10" + "      FindUndeliveredShipments = 11");
    }

    // All public

    // Inputs - name, brand, flavor, drink type, price, and SKU
    // Output - prints success or failure message (failure == drink already exists or something went wrong) before returning true or false
    // Purpose - allows managers to add new drink types to the system to be tracked in the inventory system
    public boolean AddDrink() {

        return false;
    }

    // Inputs - SKU or name, brand, flavor, new price
    // Output - prints success or failure (failure == drink doesn’t exists or something went wrong)  message before returning true or false
    // Purpose - allow for a manger to increase/decrease the cost of drinks in the gym as needed (price changes, discounts, etc)
    public boolean Client_UpdateDrinkPrice() {

        return false;
    }
    
    // Inputs - SKU or name, brand, flavor, new isActive status (true or false)
    // Output - prints success or failure (failure == drink doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow for a manager to label drinks in the system as no longer actively sold, will allow those drinks to not be included in 
    // certain reports (inventory scans, etc.)
    public boolean Client_UpdateDrinkStatus() {

        return false;
    }

    // Inputs - agreement number, first name, last name
    // Output -  prints success or failure (failure == member already exists or something went wrong) message before returning true or false
    // Purpose - allow for a new member to be added to our system to track their sale and drink purchase histories
    public boolean Client_AddMember() {

        return false;
    }

    // Inputs - order number (from supplier), supplier email, order date, estimated arrival date, cost of order, all drinks in the shipment => SKU, 
    // quantity. The pair (supplierEmail, orderNumber) uniquely identifies a shipment. 
    // Output - prints success or failure (failure == shipment already exists or something went wrong) message before returning true or false
    // Purpose - allow managers to add new shipment info
    // combines addshipment and adddrinktoshipment apis 
    public boolean AddShipment() {

        return false;
    }
    
    // Inputs - shipment number/shipment order number
    // Output - Prints success message (x quantity added to x drink in x location) for each drink in the shipment, or a failure 
    // (failure == shipment doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow the quantities of drinks in the gym to be updated when the shipment arrives to the gym
    public boolean Client_ShipmentArrived() {
        String orderNumber = scanner.nextLine().trim();

        if (orderNumber.isEmpty()) {
            System.out.println("Invalid shipment order number.");
            return false;
        }

        ArrayList<String> params = new ArrayList<>();
        params.add("1");            // API number for ShipmentArrived
        params.add(orderNumber);    // shipment order number

        return server.ProcessInput(params);
    }

    // Needs drink SKU and quantity of drinks to move from storage to display
    public boolean Client_MoveStock() {
        ArrayList<String> input = new ArrayList<>();
        input.add("2");
        System.out.print("\nDrink SKU: ");
        input.add(scanner.next());
        System.out.print("Drink quantity: ");
        input.add(scanner.next());
        if(Integer.parseInt(input.get(2)) <= 0) {
            System.err.println("\nInvalid quantity\n");
            return false;
        }
        else {  // quantity > 0
            return server.ProcessInput(input);
        } 
    }

    // Inputs - none
    // Output - prints info about most recent sale (purchase number, member, date, drinks purchased, total cost) OR prints failure message before 
    // returning true or false 
    // Purpose - allow the most recent purchase to be viewed
    public boolean MostRecentSale() {

        return false;
    }
    
    // Inputs - member agreement number, number of rows (sales) they want returned
    // Output - prints info about members sales (purchase number, date, drinks purchased, total cost) OR prints failure (failure == member doesn’t 
    // exists or something went wrong) message before returning true or false
    // Purpose - analyze member patterns or find specific member transaction
    public boolean Client_MemberSales() {

        return false;
    }

    // Inputs -  purchase number
    // Output - prints success or failure (failure == purchase doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow for a sale to be canceled for a refund, etc.
    public boolean CancelSale() {

        return false;
    }
    
    // Inputs - member agreement number or NULL, is void purchase (true or false), drink info: name, brand, flavor, or SKU, quantity
    // Output - prints success or failure (failure == member or drink doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allows for purchases to be registered in the system upon a successful POS checkout (only pulls purchases from display drink location id)
    public boolean MakePurchase() {

        return false;
    }
    
    // No input needed, just make call to the server
    public boolean Client_DrinkStats() {
        //System.out.println("No inputs required");
        ArrayList<String> list = new ArrayList<>();
        list.add("4");  // 4 is the integer assigned to this API - used in the switch
        return server.ProcessInput(list);
    }
    
   // Inputs - year
// Output - prints information about most profitable month for given year
//          (month, totalRevenue, totalCost, profit) or failure message
// Purpose - Shows profit for each month in the selected year to make business decisions
public boolean Client_MonthlyProfits() {
    System.out.print("Enter year for MonthlyProfits: ");

    int year;
    try {
        year = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid year. Please enter a number like 2026.");
        return false;
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("5");                  // API number for MonthlyProfits
    params.add(String.valueOf(year));  // year parameter

    return server.ProcessInput(params);
}
    
// Inputs - none
// Output - prints information about any shipments in the system not yet delivered
// Purpose - find any missing shipments or solve stock discrepancies in the system
public boolean Client_FindUndeliveredShipments() {
    ArrayList<String> params = new ArrayList<>();
    params.add("11"); // API number for FindUndeliveredShipments

    return server.ProcessInput(params);
}
    
// Inputs - year
// Outputs - print information about most costly month for the gym from the input year
//           (month, totalShipmentCost, totalDrinkUnitsPurchased)
// Purpose - Shows how much money was spent on drink shipments for each month
public boolean Client_MonthlyCosts() {
    System.out.print("Enter year for MonthlyCosts: ");

    int year;
    try {
        year = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid year. Please enter a number like 2026.");
        return false;
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("6");                  // API number for MonthlyCosts
    params.add(String.valueOf(year));  // year parameter

    return server.ProcessInput(params);
}
    
    // Inputs - none
    // Outputs - prints inventory information about all active drinks in the system (brand, flavor, location, quantity in stock) or failure message before
    // returning true or false
    // Purpose - view quantities of drinks in the gym
    public boolean Client_InventoryScan() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning inventory...");
        input.add("9");
        return server.ProcessInput(input);
    }

    // Inputs - none
    // Outputs - list of 10 members with the most purchases - name, agreement number, purchase count or failure message
    // Purpose - Identify the members who make the most purchases at the gym to give them rewards, etc.
    public boolean Client_MostPurchases() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning most purchases...");
        input.add("10");

        return server.ProcessInput(input);
    }
    
    // Inputs - none
    // Outputs - print out any discrepancies between sales and stock (drink, expected stock, actual stock) or failure message before returning true or 
    // false
    // Purpose - identify and prevent shrinkage loss
    public boolean ShrinkageScan() {

        return false;
    }

    // HELPER METHODS
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}