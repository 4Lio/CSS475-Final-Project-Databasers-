package FinalProject;



// Handles user interaction and makes calls to the client side
public class DriverInterface {
    public static void main(String[] args) {
        ServiceSide services = new ServiceSide();
        services.APIBootUp();
        services.APIBootDown();
    }
}