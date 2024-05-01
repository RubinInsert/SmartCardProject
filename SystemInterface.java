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
        displayMainScreen();
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
        int in = input.nextInt();
        selectPage(in);
        return in;
    }
    void selectPage(int pageNumber) {
        switch (pageNumber) {
            case 0:
                displayMainScreen();
                break;
            case 1: // Create a new Smart Card
                createSmartCard();
                break;
            case 2: // Create a new Journey
                createJourney();
                break;
            case 3: // Delete a Smart Card
                deleteSmartCard();
                break;
            case 4: // Delete a Journey
                break;
            case 5: // List Smart Cards
            System.out.println("testing");
                listAllSmartCards();
                displayMainScreen();
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
        if(smartCard1 == null || smartCard2 == null || smartCard3 == null) { // If there is an empty card slot available 
            char type = ' ';
            double balance;
            clearScreen();
            System.out.println("What Type of Card are you creating?");
            System.out.println("1. Child");
            System.out.println("2. Senior");
            System.out.println("3. Adult");
            System.out.print("Enter menu number: ");
            int in = input.nextInt();
            switch (in) {
                case 1: // User chose "Child"
                    type = 'C';
                    break;
                case 2: // User chose "Senior"
                    type = 'S';
                    break;
                case 3: // User chose "Adult"
                    type = 'A';
                    break;
                default:
                    showError("The number you inputted was out of the range of the menu!");
                    return;
            }
            System.out.println("Enter the balance you wish to load onto the card: "); // TODO: Add input checks e.g. if user inputs negative number, or some bs.
            balance = input.nextDouble();
            SmartCard newTempCard = new SmartCard(currentIDNumber, type, balance);
            System.out.println("SmartCard created under ID: " + currentIDNumber);
            currentIDNumber += 1; // add one to the global variable to make sure the next card has a new number for it's ID.
            sortSmartCards(newTempCard);
            displayMainScreen();
            
        } else { // There is no empty card slot
            showError("You have reached max number of cards. You cannot create a new card until you delete one!");
        }
    }
    void showError(String errorMessage) {
        clearScreen();
        System.out.println("==============================Error==============================");
        System.out.println(errorMessage);
        System.out.println("=================================================================");
        displayMainScreen();

    }
    void sortSmartCards(SmartCard sc) { // Place the new smartcard in the earliest available slot
        if(smartCard1 == null) { 
            smartCard1 = sc;
        } else if(smartCard2 == null) {
            smartCard2 = sc;
        } else if(smartCard3 == null) {
            smartCard3 = sc;
        }
    }
    void listAllSmartCards() {
        clearScreen();
        printSmartCard(smartCard1);
        printSmartCard(smartCard2);
        printSmartCard(smartCard3);
    }
    void printSmartCard(SmartCard sc) {
        if(sc == null) return;
        System.out.println("============================SMART CARD===========================");
        System.out.println("SmartID: " + sc.getSmartCardID());
        System.out.println("Type: " + sc.getType());
        System.out.println("Card Balance: " + sc.getBalance());
        System.out.println("=================================================================");

    }
    void deleteSmartCard() {
        clearScreen();
        System.out.print("Enter the Smart Card ID you want to delete: ");
        int idToDelete = input.nextInt();
        if (smartCard1 != null && smartCard1.getSmartCardID() == idToDelete) {
            smartCard1 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else if (smartCard2 != null && smartCard2.getSmartCardID() == idToDelete) {
            smartCard2 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else if (smartCard3 != null && smartCard3.getSmartCardID() == idToDelete) {
            smartCard3 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else {
            showError("Smart Card with ID " + idToDelete + " not found.");
        }
        displayMainScreen();
    }

    void createJourney() {
        int executionCount = 0;
        String transportMode;
        int startOfJourney, endOfJourney;
        clearScreen();
        input.nextLine(); // Consume newline character
        System.out.println("============================Creating a new Journey============================");

        // Prompt user for journey details
        System.out.print("Enter Journey ID: ");
        int journeyID = input.nextInt();

        input.nextLine(); // Consume newline character



        do {
        if(executionCount > 0) System.out.print("Please enter only one of the provided responses."); // If this isnt the first time executing this code, send an error to user.
        System.out.print("Enter Transport Mode (train/bus/tram): ");
        transportMode = input.nextLine().toUpperCase().trim(); // Make response uppercase for consistency, and trim any spaces before or after text. I.e. "  train" --> "TRAIN"
        executionCount++;
        } while (!transportMode.equals("train") // If response does not match TRAIN
                && !transportMode.equals("bus") // or BUS
                && !transportMode.equals("tram")); // or TRAM, repeat question and ask for input until user inputs correctly



        System.out.print("Enter Start of Journey (1-10): ");
        startOfJourney = input.nextInt();
        do {
            if(executionCount > 0) System.out.print("Please enter only a number between 1 and 10"); // If this isnt the first time executing this code, send an error to user.
            System.out.print("Enter End of Journey (1-10, different from start): ");
            endOfJourney = input.nextInt();
            executionCount++;
        } while (endOfJourney < 1 || endOfJourney > 10); // If user input is not within defined ranges ask question again.
        executionCount = 0;
        /* Oi sam ya dog, the end of the journey needs to be
         * a further stop than the start journey right? Like
         * I should check if the endOfJourney is larger than 
         * startOfJourney and throw and error if it isnt?
         */


        System.out.print("Enter Distance of Journey: "); // What the flip do we do here gang
        int distanceOfJourney = input.nextInt();

        // Create the journey object
        Journey newJourney = new Journey(journeyID, transportMode, startOfJourney, endOfJourney, distanceOfJourney);

        // Add the journey to a smart card (not implemented yet)

        System.out.println("Journey created successfully.");

        displayMainScreen();
    }


    public static void main(String[] args)
    {
        SystemInterface systemUI = new SystemInterface();
        input = new Scanner(System.in);
        systemUI.run();
    }
    
}
