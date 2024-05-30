// Sam Bosworth - Student Number: c3477699
// Alex Rubin - Student Number: c3486124
import java.util.Scanner;
public class SystemInterface
{
    static Scanner input;
    SmartCard[] smartCards;
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
                ConsoleUtil.GetInt("test");
                System.out.println(ConsoleUtil.GetString("test"));
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
            System.out.println(inputMessage);
            return input.nextLine();
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
                    showError("Invalid Integer input!");
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
