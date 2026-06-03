package FinalProject;
import java.lang.reflect.Array;
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
        System.out.println("AddMember = 0" + "    ShipmentArrived = 1");
        System.out.println("MoveStock = 2" + "    MemberSales = 3");
        System.out.println("DrinkStats = 4" + "   MonthlyProfits = 5");
        System.out.println("MonthlyCosts = 6");
        //System.out.println(" = 7" + " = 8");
        //System.out.println("= 9" + " = 10");
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

    // Inputs - order number (from supplier), supplier email or NULL, order date, estimated arrival date
    // Output - prints success or failure (failure == shipment already exists or something went wrong) message before returning true or false
    // Purpose - allow managers to add new shipment info 
    public boolean AddShipment() {

        return false;
    }

    // Inputs - shipment number, drink info for the shipment in the format: name, brand, flavor, or SKU, quantity, cost of purchase
    // Output - prints success or failure (failure == drink/shipment doesn’t exists or something went wrong)  message before returning true or false
    // Purpose - allows managers to add actual drinks to a shipment that will added to the inventory upon shipment arrival (drinks must be created 
    // in system before adding them to shipment)
    public boolean AddDrinkToShipment() {

        return false;
    }
    
    // Inputs - shipment number/shipment order number
    // Output - Prints success message (x quantity added to x drink in x location) for each drink in the shipment, or a failure 
    // (failure == shipment doesn’t exists or something went wrong) message before returning true or false
    // Purpose - allow the quantities of drinks in the gym to be updated when the shipment arrives to the gym
    public boolean ShipmentArrived() {

        return false;
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
    // Output - prints information about most profitable month for given year (year, month, profits) or failure (failure == year hasn’t finished or 
    // something went wrong) message before returning true or false
    // Purpose - find most profitable month in a year to make business decisions
    public boolean MonthlyProfits() {

        return false;
    }
    
    // Inputs - none
    // Output - prints information about most profitable year for past 5 years (year, profits) or failure message before returning true or false
    // Purpose - find most profitable year in a 5 year span to make business decisions
    public boolean FindMostProfitableYear() {

        return false;
    }
    
    // Inputs - none
    // Output - prints information about any shipments in the system not yet delivered or failure message before returning true or false
    // Purpose - find any missing shipments or solve stock discrepancies in the system
    public boolean FindUndeliveredShipments() {

        return false;
    }
    
    // Inputs - none
    // Outputs - print information about most costly year in past 5 (year, money spend, margins?) or failure message before returning true or false
    // Purpose - find most expensive spending year recently for business purposes
    public boolean FindMostCostlyYear() {

        return false;
    }
    
    // Inputs - none
    // Outputs - print information about most costly month for the gym from past year (month, money spent, margins?) or failure message before 
    // returning true or false
    // Purpose - find the most expensive month in the past year for the gym for business decisions
    public boolean MonthlyCosts() {

        return false;
    }
    
    // Inputs - none
    // Outputs - prints inventory information about all active drinks in the system (drink, location, quantity in stock) or failure message before 
    // returning true or false
    // Purpose - view quantities of drinks in the gym
    // Implemented by: Codie Aragon
    public boolean Client_InventoryScan() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning inventory...");
        input.add("9");
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