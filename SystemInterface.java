// Sam Bosworth - Student Number: c3477699
// Alex Rubin - Student Number: c3486124
import java.io.Console;
import java.util.Scanner;
public class SystemInterface
{
    static Scanner input;
    public static SmartCard[] smartCards = new SmartCard[10]; // static keyword indicates it is not a instance variable and therefore can be made public.
    ConsoleUtil consoleUtil; // Cant use static objects, have to make do with this :(
    private void run()
    {
        input = new Scanner(System.in);
        ConsoleUtil.clearScreen();
        displayMainScreen();
        //This method should control the flow of the program
    }
    int displayMainScreen() {
        int inputString;
        do {
            inputString = ConsoleUtil.GetFromMenu("Smart Card Project - Main Menu", new String[] {"Create a new Smart Card",
                                                                                                        "Delete a Smart Card",
                                                                                                        "Delete a Journey",
                                                                                                        "List Smart Cards",
                                                                                                        "List Transport specific Journeys",
                                                                                                        "List Journeys on Smart Card",
                                                                                                        "Total Fare Costs"});
            switch (inputString) {
                case 1:  
                CreateSmartCardPage();
                // Create Smart Card
                break;
                case 2:
                // Delete a Smart Card
                break; 
                case 3:
                // Delete a Journey
                break;
                case 4:
                    // List all Smart Cards
                    break;
                case 5:
                    // List all Journeys with a specific transport type
                    break;
                case 6:
                    // List all Journeys on a specific Smart Card
                    break;
                case 7:
                    // Print Fare Costs
                    break;
                default:
                    ConsoleUtil.showError("Invalid input. Please enter a number between 1 and 8.");
            }
        } while (true);
    }

   void CreateSmartCardPage() {
        if(!SmartCard.isAnySlotAvailable()) { // Check if no slots are empty (a)
            ConsoleUtil.showError("There are no available slots to enter a new Smart Card!");
            displayMainScreen();
        }
        int newCardID = ConsoleUtil.GetInt("Enter an ID for the Smartcard (between 0 and 9999): ", 0, 9999); // Ask for id of the Smartcard (b)
            while(SmartCard.getFromID(newCardID) != null) { // There is an existing smartcard with this ID. Assign a random ID. This while loop should only
                newCardID = randomInt(0, 9999);     // once, but may run more if the randomInt() function happens to spit out an already existing smart card
            }
        double cardBalance = ConsoleUtil.GetDouble("Enter a balance for the Smartcard (minimum of $5)", 5, Double.MAX_VALUE); // Get balance for the smartcard (c)
        String cardType = ConsoleUtil.GetString("Enter a Smartcard type ('C' for Child, 'A' for Adult, or 'S' for Senior): ");
        while(!SmartCard.isValidType(cardType.toUpperCase().charAt(0))) { // Ensure SmartCard type is either 'C', 'A', or 'S'. Menu cannot be used as brief specifies something is inputted. (d)
            ConsoleUtil.showError("Ensure your response is either 'C', 'A', or 'S'.");
            cardType = ConsoleUtil.GetString("Enter a Smartcard type ('C' for Child, 'A' for Adult, or 'S' for Senior): ");
        }
        SmartCard smartCard = new SmartCard(newCardID, cardType.charAt(0), cardBalance);
        SmartCard.FillFirstEmpty(smartCard); // Add newly created smart card to index of smartcards
        int numberOfJourneys = ConsoleUtil.GetInt("Enter the number of journeys you would like on this card (0 - " + smartCard.getMaxJourneys() + "): ", 0, smartCard.getMaxJourneys());
        for(int x = 0; x < numberOfJourneys; x++) {
            int journeyID = ConsoleUtil.GetInt("Enter an ID for Journey #" + x + " (between 0 and 9999): "); // (e)
            while (smartCard.getJourneyByID(journeyID) != null) { // If the ID is already taken, show an error and repeat until a new ID is entered.
                ConsoleUtil.showError("ID #" + journeyID + " is already taken by another existing journey!");
                journeyID = ConsoleUtil.GetInt("Enter an ID for Journey #" + x + " (between 0 and 9999): ");
            }
            String journeyType = "";
            switch(ConsoleUtil.GetFromMenu("Choose Transport Mode for Journey", new String[] {"Train", "Bus", "Tram"})) { // (f)
                case 1: // Train
                journeyType = "TRAIN";
                break;
                case 2: // Bus
                journeyType = "BUS";
                break;
                case 3: // Tram
                journeyType = "TRAM";
                break;
            }
            int startOfJourney = ConsoleUtil.GetInt("Enter the Start point of the Journey (between 1 and 10): ", 1, 10);
            int endOfJourney = ConsoleUtil.GetInt("Enter the End point of the Journey (between 1-10): ", 1, 10); // Should this check if greater than startOfJourney or is the track two ways?
            int distanceOfJourney = Math.abs(endOfJourney - startOfJourney); // Calculate distance between journey.
            Journey createdJourney = new Journey(journeyID, journeyType, startOfJourney, endOfJourney, distanceOfJourney);
            if(smartCard.isDuplicateJourney(createdJourney)) {
                ConsoleUtil.showError("This Journey is a duplicate! Please enter other values!");
                x -= 1; // Make them re-enter values for the same Journey.
                continue;
            }
            smartCard.FillFirstEmptyJourney(createdJourney);
            ConsoleUtil.clearScreen();
            displayMainScreen();
        }
   }
   public int randomInt(int min, int max) {
    return (int)(Math.random() * ((max - min) + 1));
   }
    public static class ConsoleUtil {
        public static void waitForKeyPress() { // Wait for user to press Enter Key before proceeding with code execution.
            System.out.println("<Press Enter to Continue>");
            try{System.in.read();}
            catch(Exception e){}
            
            
        }
        public static void clearScreen() { // Creates a lot of new lines to clear the window -> Not system specific unlike "cls" for windows.
        System.out.println(System.lineSeparator().repeat(100));
        }
        public static String GetString(String inputMessage) {
            System.out.print(inputMessage);
            String inputText = input.next();
            input.nextLine();
            return inputText;
        }
        public static int GetInt(String inputMessage) {
            boolean isValidInput = false;
            int nextIntInput = 0;
            while (!isValidInput) {
                System.out.print(inputMessage);
                if(!input.hasNextInt()) {
                    showError("Invalid Integer input!");
                    input.next();
                } else {
                    try {
                        nextIntInput = input.nextInt();
                        input.nextLine();
                        isValidInput = true;
                    } catch (Exception e) {}
                }

            }
            return nextIntInput;
        }
        public static int GetInt(String inputMessage, int min, int max) {
            boolean isValidInput = false;
            int nextIntInput = 0;
            while (!isValidInput) {
                System.out.print(inputMessage);
                if(!input.hasNextInt()) {
                    showError("Invalid Integer input!");
                    input.next();
                } else {
                    try {
                        nextIntInput = input.nextInt();
                        input.nextLine();
                        isValidInput = true;
                        if(nextIntInput > max || nextIntInput < min) {
                            isValidInput = false;
                            showError("Enter number between " + min + " and " + max + "!");
                        }
                    } catch (Exception e) {}
                }

            }
            return nextIntInput;
        }
        public static double GetDouble(String inputMessage) {
            boolean isValidInput = false;
            double nextDoubleInput = 0;
            while (!isValidInput) {
                System.out.print(inputMessage);
                if(!input.hasNextDouble()) {
                    showError("Invalid Double input!");
                    input.next();
                } else {
                    try {
                        nextDoubleInput = input.nextDouble();
                        input.nextLine();
                        isValidInput = true;
                    } catch (Exception e) {}
                }

            }
            return nextDoubleInput;
        }
        public static double GetDouble(String inputMessage, double min, double max) {
            boolean isValidInput = false;
            double nextDoubleInput = 0;
            while (!isValidInput) {
                System.out.print(inputMessage);
                if(!input.hasNextDouble()) {
                    showError("Invalid Integer input!");
                    input.next();
                } else {
                    try {
                        nextDoubleInput = input.nextDouble();
                        input.nextLine();
                        isValidInput = true;
                        if(nextDoubleInput > max || nextDoubleInput < min) {
                            isValidInput = false;
                            showError("Enter number between " + min + " and " + max + "!");
                        }
                    } catch (Exception e) {}
                }

            }
            return nextDoubleInput;
        }
        public static int GetFromMenu(String title, String[] options) { // Returns the menu number (indexing starts at 1 not 0)
            String menuString = "===============- " + title + " -===============";
            for(int i = 0; i < options.length; i++) {
                menuString += "\n" + (i+1) + ". " + options[i];
            }
            menuString += "\n\nEnter a Menu Number: ";
            return GetInt(menuString, 1, options.length + 1);

        }
        public static void showError(String errorMessage) {
            ConsoleUtil.clearScreen();
            System.out.println("==============================Error==============================");
            System.out.println(errorMessage);
            System.out.println("=================================================================");
            ConsoleUtil.waitForKeyPress();
            ConsoleUtil.clearScreen();
        }
    
    }


    public static void main(String[] args)
    {
        SystemInterface systemUI = new SystemInterface();
        systemUI.run();
    }
    
}
