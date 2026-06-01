package FinalProject;

import java.util.Scanner;

// Handles user interaction, boots up service side and makes calls to the client side - Implemented by Nolan Kelly
public class DriverInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServerSide service = new ServerSide();
        ClientSide client = new ClientSide(scanner, service);
        service.APIBootUp();
        String userInput = null;
        int input = -1;

        // handles ui
        while(true) {
            client.DisplayOptions();
            userInput = scanner.next();
            if(userInput.equals("exit")) {
                break;
            }
            else {
                input = Integer.parseInt(userInput);
                switch(input) {
                    case 0:
                        client.Client_AddMember();
                        break;
                    case 1:
                        //ShipmentArrived();
                        break;
                    case 2:
                        //MoveStock();
                        break;
                    case 3:
                        client.Client_MemberSales();
                        break;
                    case 4:
                        client.Client_DrinkStats();
                        break;
                    case 5:
                        //MonthlyProfits();
                        break;
                    case 6:
                        //MonthlyCosts();
                        break;
                    case 7:
                        client.Client_UpdateDrinkPrice();
                        break;
                    case 8:
                        client.Client_UpdateDrinkStatus();
                        break;
                    case 9:
                        // empty for now
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
        }

        //services.TestConnection();
        service.APIBootDown();
        scanner.close();
    }
}