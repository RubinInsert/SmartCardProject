import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class SystemInterface
{
    static Scanner input;
    static int currentIDNumber = 0;
    SmartCard smartCard1;
    SmartCard smartCard2;
    SmartCard smartCard3;
    private void run()
    {
        clearScreen();
        System.out.println(displayMainScreen());
        //This method should control the flow of the program
    }
    int displayMainScreen() { // Returns the number the user returns when selecting a menu item. I.e. "Create a new Smart Card" -> returns 1.
        System.out.println("===============- Smart Card Project - Main Menu -===============");
        System.out.println("1. Create a new Smart Card");
        System.out.println("2. Create a new Journey");
        System.out.println("3. Delete a Smart Card");
        System.out.println("4. Delete a Journey");
        System.out.println("5. List Smart Cards");
        System.out.println("6. List Journeys on Smart Card");
        System.out.println("7. List Transport specific Journeys");
        System.out.println("8. Total Fare Costs");
        System.out.println("=================================================================");
        System.out.print("Input Menu Number: ");
        return input.nextInt();
    }
    void selectPage(int pageNumber) {
        switch (pageNumber) {
            case 0:
                displayMainScreen();
                break;
            case 1: // Create a new Smart Card
                break;
            case 2: // Create a new Journey
                break;
            case 3: // Delete a Smart Card
                break;
            case 4: // Delete a Journey
                break;
            case 5: // List Smart Cards
                break;
            case 6: // List Journeys on Smart Card
                break;
            case 7: // List Transport specific Journeys
                break;
            case 8: // Total Fare Costs
                break;
            default:
            System.out.println("Page Number Invalid!");
                break;
        }
    }
    void clearScreen() { // Creates a lot of new lines to clear the window -> Not system specific unlike "cls" for windows.
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    void createSmartCard() {
        if(smartCard1 != null || smartCard2 != null || smartCard3 != null) { // If there is an empty card slot available 
            SmartCard newTempCard = new SmartCard();
            newTempCard.setSmartCardID(currentIDNumber); // Assign a new ID number and add one to the global variable to make sure the next card has a new number
            currentIDNumber += 1;
        } else { // There is no empty card slot
            clearScreen();
            System.out.println("You have reached max number of cards. You cannot create a new card until you delete one!");
            displayMainScreen();
        }
    }
    public static void main(String[] args)
    {
        SystemInterface systemUI = new SystemInterface();
        input = new Scanner(System.in);
        systemUI.run();
    }
    
}
