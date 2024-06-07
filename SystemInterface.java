// Sam Bosworth - Student Number: c3477699
// Alex Rubin - Student Number: c3486124
import java.util.Scanner;
import java.io.*;
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
                                                                                                        "Total Fare Costs",
                                                                                                        "Import Smart Cards and Journeys",
                                                                                                        "Export Smart Cards and Journeys"});
            switch (inputString) {
                case 1:  
                CreateSmartCardPage();
                // Create Smart Card
                break;
                case 2:
                DeleteSmartCardPage();
                // Delete a Smart Card
                break; 
                case 3:
                DeleteJourneyPage();
                // Delete a Journey
                break;
                case 4:
                ListAllSmartCardsPage();
                    // List all Smart Cards
                    break;
                case 5:
                    ListAllJourneysSpecificTransportPage();
                    // List all Journeys with a specific transport type
                    break;
                case 6:
                    // List all Journeys on a specific Smart Card
                    ListJourneysOnSmartCardPage();
                    break;
                case 7:
                    // Print Fare Costs
                    CalculateTotalFareCostsPage();
                    break;
                case 8:
                    // Import Smartcards and Journeys from external file
                    importSmartCardsAndJourneysPage();
                    break;
                case 9:
                    // export Smartcards and Journeys to external file
                    exportSmartCardsAndJourneysPage();
                    break;
                default:
                    ConsoleUtil.showError("Invalid input. Please enter a number between 1 and 8.");
            }
        } while (true);
    }
    //Import Smartcards and Journeys from external file
    void importSmartCardsAndJourneysPage() {
        ConsoleUtil.clearScreen();
        System.out.print("Enter the file name to import: ");
        String fileName = input.next();
        try (Scanner inputStream = new Scanner(new File(fileName))) {
            int index = 0;
            while (inputStream.hasNextLine() && index < smartCards.length) {
                String line = inputStream.nextLine();
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                char type = data[1].charAt(0);
                double balance = Double.parseDouble(data[2]);
                SmartCard smartCard = new SmartCard(id, type, balance);
                smartCards[index++] = smartCard;

                for (int i = 3; i < data.length; i += 5) {
                    int journeyID = Integer.parseInt(data[i]);
                    String transportMode = data[i + 1];
                    int start = Integer.parseInt(data[i + 2]);
                    int end = Integer.parseInt(data[i + 3]);
                    int distance = Integer.parseInt(data[i + 4]);
                    Journey journey = new Journey(journeyID, transportMode, start, end, distance);
                    smartCard.FillFirstEmptyJourney(journey);
                }
            }
            System.out.println("Smart Cards and Journeys imported successfully!");
        } catch (FileNotFoundException e) {
            ConsoleUtil.showError("File not found: " + fileName);
        } catch (Exception e) {
            ConsoleUtil.showError("An error occurred while importing data.");
        }
        ConsoleUtil.waitForKeyPress();
        ConsoleUtil.clearScreen();
    }
    // Export Smartcards and Journeys from external file
    void exportSmartCardsAndJourneysPage() {
        ConsoleUtil.clearScreen();
        System.out.print("Enter the file name to export to: ");
        String fileName = input.next();
        try (PrintWriter outFile = new PrintWriter(fileName)) {
            for (SmartCard smartCard : smartCards) {
                if (smartCard == null) continue;
                outFile.println("SmartCard");
                outFile.println("ID " + smartCard.getSmartCardID());
                outFile.println("Type " + smartCard.getTypeFormatted());
                outFile.println("Balance " + smartCard.getBalance());
                outFile.println("Journeys");
                for (Journey journey : smartCard.getJourneys()) {
                    if (journey == null) continue;
                    outFile.println("ID " + journey.getJourneyID());
                    outFile.println("Mode " + journey.getTransportMode());
                    outFile.println("Start " + journey.getStartOfJourney());
                    outFile.println("End " + journey.getEndOfJourney());
                    outFile.println("Distance " + journey.getDistanceOfJourney());
                }
                outFile.println(); // Add a blank line after each SmartCard's journeys
            }
            System.out.println("Smart Cards and Journeys exported successfully!");
        } catch (FileNotFoundException e) {
            ConsoleUtil.showError("File not found: " + fileName);
        } catch (Exception e) {
            ConsoleUtil.showError("An error occurred while exporting data.");
        }
        ConsoleUtil.waitForKeyPress();
        ConsoleUtil.clearScreen();
    }

   void CreateSmartCardPage() {
        ConsoleUtil.clearScreen();
        if(!SmartCard.isAnySlotAvailable()) { // Check if no slots are empty (a)
            ConsoleUtil.showError("There are no available slots to enter a new Smart Card!");
            displayMainScreen();
        }
        int newCardID = ConsoleUtil.GetInt("Enter an ID for the Smartcard (between 0 and 9999): ", 0, 9999); // Ask for id of the Smartcard (b)
            while(SmartCard.getFromID(newCardID) != null) { // There is an existing smartcard with this ID. Assign a random ID. This while loop should only
                newCardID = randomInt(0, 9999);     // once, but may run more if the randomInt() function happens to spit out an already existing smart card
            }
        double cardBalance = ConsoleUtil.GetDouble("Enter a balance for the Smartcard (minimum of $5): ", 5, Double.MAX_VALUE); // Get balance for the smartcard (c)
        String cardType = ConsoleUtil.GetString("Enter a Smartcard type ('C' for Child, 'A' for Adult, or 'S' for Senior): ").toUpperCase();
        while(!SmartCard.isValidType(cardType.toUpperCase().charAt(0))) { // Ensure SmartCard type is either 'C', 'A', or 'S'. Menu cannot be used as brief specifies something is inputted. (d)
            ConsoleUtil.showError("Ensure your response is either 'C', 'A', or 'S'.");
            cardType = ConsoleUtil.GetString("Enter a Smartcard type ('C' for Child, 'A' for Adult, or 'S' for Senior): ").toUpperCase();
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
        }
        ConsoleUtil.clearScreen();
        displayMainScreen();
   }
   void DeleteSmartCardPage() {
    ConsoleUtil.clearScreen();
    int userInputID = ConsoleUtil.GetInt("Enter Smart Card ID for which you would like to delete: ", 0, 9999);
    if(SmartCard.getFromID(userInputID) != null) {
        SmartCard.getFromID(userInputID).Delete();
        ConsoleUtil.clearScreen();
        System.out.println("Smart Card #" + userInputID + " has been deleted successfully!");
        ConsoleUtil.waitForKeyPress();
    } else {
        ConsoleUtil.showError("Smart Card with ID #" + userInputID + " could not be found!");
    }
   }
   void DeleteJourneyPage() {
    ConsoleUtil.clearScreen();
    int userInputCardID = ConsoleUtil.GetInt("Enter Smart Card ID for which the journey can be found: ", 0, 9999);
    if(SmartCard.getFromID(userInputCardID) != null) {
        int userInputJourneyID = ConsoleUtil.GetInt("Enter Journey ID for which you would like to delete: ", 0, 9999);
        if(SmartCard.getFromID(userInputCardID).getJourneyByID(userInputJourneyID) != null) {
            SmartCard.getFromID(userInputCardID).getJourneyByID(userInputJourneyID).Delete();
            ConsoleUtil.clearScreen();
            System.out.println("Journey #" + userInputJourneyID + " on card #" + userInputCardID + " has been deleted successfully!");
            ConsoleUtil.waitForKeyPress();
        } else {
            ConsoleUtil.showError("Journey with ID #" + userInputJourneyID + " could not be found!");
        }
    } else {
        ConsoleUtil.showError("Smart Card with ID #" + userInputCardID + " could not be found!");
    }      
   }
    // List all Smart Cards and Journeys.
   void ListAllSmartCardsPage() {
        ConsoleUtil.clearScreen();
        if(SmartCard.getTotalCards() == 0) {
            ConsoleUtil.showError("No Cards have been created!");
            return;
        }
        for(int sc = 0; sc < smartCards.length; sc++) {
            SmartCard currentSmartCard = smartCards[sc];
            if(currentSmartCard == null) continue;
            System.out.println("Smartcard #" + currentSmartCard.getSmartCardID() + " has type " + currentSmartCard.getTypeFormatted() + " and " + currentSmartCard.getTotalJourneys() + " journey(s)");
            for(int j = 0; j < smartCards[sc].getJourneys().length; j++) {
                Journey currentJourney = currentSmartCard.getJourneys()[j];
                if(currentJourney == null) continue;
                System.out.println("        Journey #" + currentJourney.getJourneyID() + " has transport mode " + currentJourney.getTransportMode());
            }
        }
        ConsoleUtil.waitForKeyPress();
   }
   // List all Smart Cards and Journeys with a specific transport mode.
   void ListAllJourneysSpecificTransportPage() {
    if(SmartCard.getTotalCards() == 0) {
        ConsoleUtil.showError("There are no Smart Cards Created yet!");
        return;
    }
    String selectedTransport = "";
    switch (ConsoleUtil.GetFromMenu("Enter a Journey Type", new String[] {"Train", "Bus", "Tram"})) {
        case 1:
        selectedTransport = "TRAIN";
            break;
        case 2:
        selectedTransport = "BUS";
            break;
        case 3:
        selectedTransport = "TRAM";
            break;
    }
    int numOfCorrectJourneys = 0;
    for(int x = 0; x < smartCards.length; x++) {
        if(smartCards[x] == null) continue;
        
        for(int y = 0; y < smartCards[x].getJourneys().length; y++) {
            if(smartCards[x].getTotalJourneys() == 0) break;
            Journey j = smartCards[x].getJourneys()[y];
            if(j == null) continue;
            if(j.getTransportMode() == selectedTransport) {
                numOfCorrectJourneys++;
                System.out.println("Journey #" + j.getJourneyID() + " has that transport mode, and it belongs to smartcard #" + smartCards[x].getSmartCardID());
            }
        }
    }
    if(numOfCorrectJourneys == 0) {
        ConsoleUtil.showError("There are no Journeys with this transport mode!");
    } else {
        ConsoleUtil.waitForKeyPress();
    }
   }
   void ListJourneysOnSmartCardPage() {
    ConsoleUtil.clearScreen();
    if(SmartCard.getTotalCards() == 0) {
        ConsoleUtil.showError("There are no Smart Cards Created yet!");
        return;
    }
    int smartCardID = ConsoleUtil.GetInt("Enter Smart Card ID (0 - 9999): ", 0, 9999);
    if(SmartCard.getFromID(smartCardID) == null) {
        ConsoleUtil.showError("Could not find a Smart Card with this ID!");
        return;
    }
    SmartCard smartCard = SmartCard.getFromID(smartCardID);
    if(smartCard.getTotalJourneys() == 0) {
        ConsoleUtil.showError("Smart Card #" + smartCardID + " has no Journeys linked to it!");
        return;
    }
    ConsoleUtil.clearScreen();
    Journey[] j = smartCard.getJourneys();
    for(int x = 0; x < smartCard.getJourneys().length; x++) {
        if(j[x] == null) continue;
        System.out.println("Journey #" + j[x].getJourneyID() + " has transport mode " + j[x].getTransportMode() 
                            + " starting from Stop #" + j[x].getStartOfJourney() + " and ending at Stop #" + j[x].getEndOfJourney()
                            + "with journey distance of " + j[x].getDistanceOfJourney() + " stop(s).");
    }
    ConsoleUtil.waitForKeyPress();
   }
   void CalculateTotalFareCostsPage() {
    ConsoleUtil.clearScreen();
    double totalFareCost = 0, totalBusCost = 0, totalTrainCost = 0, totalTramCost = 0;
    if(SmartCard.getTotalCards() == 0) {
        ConsoleUtil.showError("There are no Smart Cards Created yet!");
        return;
    }
    for(int x = 0; x < smartCards.length; x++) {
        if(smartCards[x] == null) continue;
        
        for(int y = 0; y < smartCards[x].getJourneys().length; y++) {
            if(smartCards[x].getTotalJourneys() == 0) break;
            Journey j = smartCards[x].getJourneys()[y];
            if(j == null) continue;
            switch (j.getTransportMode()) {
                case "TRAIN":
                    totalTrainCost += 1.5 * 1.86 * j.getDistanceOfJourney();
                    totalFareCost += 1.5 * 1.86 * j.getDistanceOfJourney();
                    break;
                case "TRAM":
                    totalTramCost += 1.5 + 2.24 * j.getDistanceOfJourney();
                    totalFareCost += 1.5 + 2.24 * j.getDistanceOfJourney();
                    break;
                case "BUS":
                    totalBusCost += 1.5 + 1.60 * j.getDistanceOfJourney();
                    totalFareCost += 1.5 + 1.60 * j.getDistanceOfJourney();
                    break;
                default:
                    break;
            }
        }
    }
    ConsoleUtil.clearScreen();
    System.out.println("Total transport mode journeys cost/fare: $" + totalFareCost);
    System.out.println("=============================================================");
    System.out.println("Total cost of Train Journeys is $" + totalTrainCost);
    System.out.println("Total cost of Bus Journeys is $" + totalBusCost);
    System.out.println("Total cost of Tram Journeys is $" + totalTramCost);
    ConsoleUtil.waitForKeyPress();
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
