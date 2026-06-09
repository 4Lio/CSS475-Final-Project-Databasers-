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
        System.out.println("MostRecentSale = 12");
    }

    // All public

    // Purpose - allow for a manger to increase/decrease the cost of drinks in the gym as needed (price changes, discounts, etc)
    public boolean Client_UpdateDrinkPrice() {
        ArrayList<String> input = new ArrayList<>();
        String SKU;
        String price;
        
        input.add("7");

        System.out.print("Drink SKU: ");
        SKU = scanner.next();
        input.add(SKU);

        System.out.print("New drink price: ");
        price = scanner.next();
        input.add(price);

        if(Double.parseDouble(input.get(2)) <= 0) {
            System.err.println("\nInvalid price\n");
            return false;
        } else { 
            return server.ProcessInput(input);
        } 
    }
    
    // Purpose - allow for a manager to label drinks in the system as no longer actively sold, will allow those drinks to not be included in 
    // certain reports (inventory scans, etc.)
    public boolean Client_UpdateDrinkStatus() {
        ArrayList<String> input = new ArrayList<>();
        String SKU;
        String newStatus;
        
        input.add("8");

        System.out.print("Drink SKU: ");
        SKU = scanner.next();
        input.add(SKU);

        System.out.print("New drink status (true/false): ");
        newStatus = scanner.next();
        
        if (!newStatus.equals("true") && !newStatus.equals("false")) {
            System.err.println("Invalid status, must be true or false\n");
            return false;
        }

        input.add(newStatus);
        return server.ProcessInput(input);
    }

    // Purpose - allow for a new member to be added to our system to track their sale and drink purchase histories
    public boolean Client_AddMember() {
        ArrayList<String> input = new ArrayList<>();
        String agreementNum;
        String firstName;
        String lastName;
        
        input.add("0");

        System.out.print("Agreement number: ");
        agreementNum = scanner.next();
        if (!agreementNum.matches("\\d+")) {
            System.err.println("Invalid agreement number. Must contain only numbers\n");
            return false;
        } else {
            input.add(agreementNum);
        }
       
        System.out.print("First name: ");
        firstName = scanner.next();
        input.add(firstName);

        System.out.print("Last name: ");
        lastName = scanner.next();
        input.add(lastName);

        return server.ProcessInput(input);
    }
    
    // Purpose - allow the quantities of drinks in the gym to be updated when the shipment arrives to the gym
    public boolean Client_ShipmentArrived() {
    ArrayList<String> params = new ArrayList<>();
    String orderNumber;

    params.add("1"); // API number for ShipmentArrived

    System.out.print("Shipment order number: ");
    orderNumber = scanner.next();

    if (orderNumber.isEmpty()) {
        System.err.println("Invalid shipment order number.\n");
        return false;
    }

    params.add(orderNumber);
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
 
    // Purpose - allow the most recent purchase to be viewed
    public boolean Client_MostRecentSale() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning most recent sale...");
        input.add("12");

        return server.ProcessInput(input);
    }
    
    // Purpose - analyze member patterns or find specific member transaction
    public boolean Client_MemberSales() {
        ArrayList<String> input = new ArrayList<>();
        String agreementNum;
        String numRows;
        
        input.add("3");

        System.out.print("Agreement number: ");
        agreementNum = scanner.next();
        if (!agreementNum.matches("\\d+")) {
            System.err.println("Invalid agreement number. Must contain only numbers\n");
            return false;
        } else {
            input.add(agreementNum);
        }
       
        System.out.print("Number of rows to display: ");
        numRows = scanner.next();
        if (!numRows.matches("\\d+")) {
            System.err.println("Invalid input. Must contain only numbers\n");
            return false;
        } else {
            input.add(numRows);
        }

        return server.ProcessInput(input);
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
    ArrayList<String> params = new ArrayList<>();
    String year;

    params.add("5"); // API number for MonthlyProfits

    System.out.print("Year for MonthlyProfits: ");
    year = scanner.next();

    if (!year.matches("\\d{4}")) {
        System.err.println("Invalid year. Please enter a 4-digit year like 2026.\n");
        return false;
    }

    params.add(year);
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
    ArrayList<String> params = new ArrayList<>();
    String year;

    params.add("6"); // API number for MonthlyCosts

    System.out.print("Year for MonthlyCosts: ");
    year = scanner.next();

    if (!year.matches("\\d{4}")) {
        System.err.println("Invalid year. Please enter a 4-digit year like 2026.\n");
        return false;
    }

    params.add(year);
    return server.ProcessInput(params);
}
    
    // Purpose - view quantities of drinks in the gym
    public boolean Client_InventoryScan() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning inventory...");
        input.add("9");
        return server.ProcessInput(input);
    }

    // Purpose - Identify the members who make the most purchases at the gym to give them rewards, etc.
    public boolean Client_MostPurchases() {
        ArrayList<String> input = new ArrayList<>();

        System.out.println("Scanning most purchases...");
        input.add("10");

        return server.ProcessInput(input);
    }

    // HELPER METHODS
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}