package FinalProject;

import java.util.Scanner;

// Handles user interaction, boots up service side and makes calls to the client side - Implemented by Nolan Kelly
public class DriverInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServerSide service = new ServerSide();
        ClientSide client = new ClientSide(scanner, service);
        String userInput = null;
        boolean run = service.BootUp();

        if(run) {   // if we are good to go/server BootUp worked
            // handles ui
            while(true) {
                client.DisplayOptions();
                userInput = scanner.next();
                if(userInput.equals("exit")) {
                    break;
                }
                else {
                    try {
                        switch(Integer.parseInt(userInput)) {
                            case 0:
                                client.Client_AddMember();
                                break;
                            case 1:
                                client.Client_ShipmentArrived();
                                break;
                            case 2:
                                client.Client_MoveStock();
                                break;
                            case 3:
                                client.Client_MemberSales();
                                break;
                            case 4:
                                client.Client_DrinkStats();
                                break;
                            case 5:
                                client.Client_MonthlyProfits();
                                break;
                            case 6:
                                client.Client_MonthlyCosts();
                                break;
                            case 7:
                                client.Client_UpdateDrinkPrice();
                                break;
                            case 8:
                                client.Client_UpdateDrinkStatus();
                                break;
                            case 9:
                                client.Client_InventoryScan();
                                break;
                            case 10:
                                client.Client_MostPurchases();
                                break;
                            case 11:
                                client.Client_FindUndeliveredShipments();
                                break;
                            default:
                                System.out.println("\nInvalid input\n");
                                break;
                        }
                    }
                    catch(NullPointerException e) {
                        System.err.println("\nBad input: " + e.getMessage() + "\n");
                    }
                    catch(NumberFormatException e) {
                        System.err.println("\nBad input: " + e.getMessage() + "\n");
                    }
                }
            }
        }

        //services.TestConnection();
        service.BootDown();
        scanner.close();
    }
}