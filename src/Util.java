import java.util.Scanner;
import java.io.Console;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class Util {
    private static final Scanner input = new Scanner(System.in);

    /*
    print out a message with some type of effect
    the effect can be foreground color, background color, bold, etc.
    */
    public static void print(String message, String effect) {
        if (effect == null)
            effect = "";
        // print out the message with the desired color
        System.out.print(effect + message);
        // reset the color back to default
        System.out.print(RESET + "");
    }


    
    public static void println(String message, String effect) {
        print(message, effect);
        System.out.println();
    }


    /*
    Ask the user a yes/no question
    @return: True if answer is yes, False if answer is no
    */
    public static boolean yesNoResponse(String message) {
        char response;
        boolean pass = false;
        boolean result = false;

        print(message, RED);
        while (!pass) {
            response = input.next().charAt(0);
            response = Character.toLowerCase(response);
            if (response == 'y') {
                pass = true;
                result = true;
            }
            else if (response == 'n') {
                pass = true;
                result = false;
            }
            else
                System.out.print("Invalid input! Please re-enter (y/n): ");
        }
        return result;
    }



    /**
     * prompt the user to enter an integer in the desired range
     * if the input is not in desired range or not an integer, re-prompt another one
     * 
     * @param max the maximum limit
     * @param min the minimum limit
     * @return the input integer
     */
    public static int getInt(int max, int min) {
        int result = 0;
        boolean pass;
        do {
            pass = true;
            if (input.hasNextInt()) {
                result = input.nextInt();
            }
            else {
                pass = false;
                input.next();
                print("\nPlease re-enter an appropriate integer: ", RED);
            }
            if (pass)
                if (result > max || result < min) {
                    print("\nInput out of range, please re-enter: ", RED);
                    pass = false;
                }
        } while (!pass);
        return result;
    }



    /*
    * prompt the user to enter password.
    * The password will not be visible on the screen

    * @return: the password entered
    */
    public static String promptPassword(String message) {
        Console console = System.console();
        char[] pass = console.readPassword(GREEN + message);
        return new String(pass);
    }



    /**
     * The process of setting a new password
     * Prompt the user to enter their password twice and check if both of them match each other
     * This method will be used when user creates accoutnt, or when they change password
     * 
     * @return the password entered
     */
    public static String setPassword() {
        String password = "";
        boolean match = false;
        while (!match) {
            password = promptPassword("\nPlease enter your password: ");
            String confirmPass = promptPassword("Please confirm your password: ");
            if (confirmPass.equals(password)) {
                match = true;
            }
            else {
                Util.println("\nThis does not match your password, let's try it one more time!", Util.RED);
            }
        }
        return password;
    }



    /*
    * Clear the terminal
    */
    public static void clearScreen() {
        String os = System.getProperty("os.name");
        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
                
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }



    /*
    * the "Press any key to continue..." feature
    */
    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        try{System.in.read();}
        catch(Exception ignored){}
        Util.clearScreen();
    }



    /**
     * print a big string with color on the terminal
     * 
     * @param message the message to print on the screen
     * @param color the color of the word
     * @param madeOf the character that makes up the big word art
     * @param makeBg whether to color the word, or the background
     * @param width the width of the art box
     * @param height the height of the art box
     * @param fontSize the size of the word art
     */
    public static void makeWordArt(String message, String color, char madeOf, boolean makeBg,
                                   int width, int height, int fontSize) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();
        graphics.setFont(new Font("Arial", Font.BOLD, fontSize));

        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2d.drawString(message, 10, 20);
        
        char fg = madeOf;
        char bg = ' ';
        if (makeBg) {
            fg = ' ';
            bg = madeOf;
        }
        for (int h = 0; h < height; h++) {
            StringBuilder strBuilder = new StringBuilder();
            for (int w = 0; w < width; w++) {
                strBuilder.append(img.getRGB(w, h) == -16777216 ? bg : fg);
            }
            System.out.println(color + strBuilder);
        }
    }




    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Background
    public static final String BLACK_BG = "\033[40m";  // BLACK
    public static final String RED_BG = "\033[41m";    // RED
    public static final String GREEN_BG = "\033[42m";  // GREEN
    public static final String YELLOW_BG = "\033[43m"; // YELLOW
    public static final String BLUE_BG = "\033[44m";   // BLUE
    public static final String PURPLE_BG = "\033[45m"; // PURPLE
    public static final String CYAN_BG = "\033[46m";   // CYAN
    public static final String WHITE_BG = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BG_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BG_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BG_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BG_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BG_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BG_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BG_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BG_BRIGHT = "\033[0;107m";   // WHITE
}
