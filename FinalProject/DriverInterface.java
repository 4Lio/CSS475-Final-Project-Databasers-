package FinalProject;

import java.util.Scanner;

// Handles user interaction, boots up service side and makes calls to the client side - Implemented by Nolan Kelly
public class DriverInterface {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // setup client and service instances
        ServiceSide services = new ServiceSide();
        ClientSide client = new ClientSide(scanner, services);
        services.APIBootUp();

        String userInput = null;

        int input = -1;

        // handles ui
        while(true) {
            client.DisplayOptions();
            userInput = scanner.nextLine();

            if(userInput.equals("exit")) {
                break;
            }

            else {
                input = Integer.parseInt(userInput);
                switch(input) {
                    case 0:
                        client.AddMember();
                        break;
                    case 1:
                        client.ShipmentArrived();
                        break;
                    case 2:
                        client.MoveStock();
                        break;
                    case 3:
                        client.MemberSales();
                        break;
                    case 4:
                        client.DrinkStats();
                        break;
                    case 5:
                        client.MonthlyProfits();
                        break;
                    case 6:
                        client.MonthlyCosts();
                        break;
                    case 7:
                        client.AddDrink();
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
        }

        //services.TestConnection();
        services.APIBootDown();
        scanner.close();
    }
}