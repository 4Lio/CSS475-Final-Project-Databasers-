package FinalProject;

import java.util.Scanner;

// Handles user interaction, boots up service side and makes calls to the client side - Implemented by Nolan Kelly
public class DriverInterface {
    public static void main(String[] args) {
        ServiceSide services = new ServiceSide();
        ClientSide client = new ClientSide();
        services.APIBootUp();
        Scanner scanner = new Scanner(System.in);
        String userInput = null;

        // handles ui
        while(true) {
            client.DisplayOptions();
            userInput = scanner.next();
            if(userInput.equals("exit")) {
                break;
            }
            else {
                client.ProcessInput(userInput);
            }
        }

        //services.TestConnection();
        services.APIBootDown();
        scanner.close();
    }
}