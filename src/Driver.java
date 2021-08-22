// Hoang Phuc Luan Truong (Luan)
import GraphPackage.LinkedQueue;
import java.text.DecimalFormat;
import java.util.*;


public class Driver {
    private static final int MIN_AGE = 13;
    private static final Scanner input = new Scanner(System.in);
    private static DataBase dataBase = FileIO.read();       // the database
    private static Profile myProfile = null;                // the user profile


    /*****************************
    ********* MAIN METHOD ********
    *****************************/
    public static void main(String[] args) {
        boolean exit = false;
        
        while (!exit) {
            exit = signInOption();
            if (!exit)
                showHomeScreen();
        }
    }


    /***************************************************************************************
     * Where the user is asked to log in
     * The user can choose to login directly, or create a new account and login
     * @return: whether to quit the program or not
     */
    public static boolean signInOption() {
        boolean exit = false;
        boolean goBackToMainScreen = true;
        /*
        Main screen is where the users 
        login, or create account, or exit the program.
        */
        while (goBackToMainScreen) {
            Util.clearScreen();
            Util.makeWordArt("TNetwork", Util.GREEN, '@', false, 130, 20, 18);
            System.out.println();
            // display the main screen and show the users their options
            Util.println("\t\t                                                                  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\n", null);
            Util.print("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\t\t      1.    LOG IN                    ", Util.PURPLE_BOLD_BRIGHT);
            Util.println("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\t\t      2.    CREATE NEW ACCOUNT        ", Util.PURPLE_BOLD_BRIGHT);
            Util.println("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\t\t      3.    EXIT                      ", Util.PURPLE_BOLD_BRIGHT);
            Util.println("\t\t  ", Util.YELLOW_BG_BRIGHT);
            Util.println("\t\t", null);
            Util.println("\t\t                                                                  ", Util.YELLOW_BG_BRIGHT);
            Util.print("\n\t\tPlease enter your option as an integer: ", Util.GREEN);
            
            /*
            prompt the user for the option they wants.
            and the perform the corresponding action.
            */
            int option = Util.getInt(3, 1);
            // Option 1, sign in to an existing account
            // Option 2, create a new account
            // Option 3, exit the program
            switch (option) {
                case 1 -> goBackToMainScreen = login();
                case 2 -> goBackToMainScreen = createNewAccount();
                case 3 -> {
                    exit = true;
                    goBackToMainScreen = false;
                }
            }
        }
        return exit;
    }




     /**************************************************************************************
     * Perform the log in process
     * prompt the user to enter their account name(not real name), and then look it up
     * if the account name exists, then prompt for the password
     * if the password matches the account password, then log the user in
     * if the password does not match, then check if the user wants to retry, if not, go back to main screen
     * 
     * @return whether to go back to the main screen(where users do log in) or not
     */
    public static boolean login() {
        boolean pass = false;
        do {
            Util.clearScreen();
            Util.makeWordArt("LOG  IN", Util.YELLOW, '¶', true, 80, 22, 15);
            Util.print("\n\nPlease enter your account name: ", Util.GREEN);
            String accountName = input.nextLine();
            
            myProfile = dataBase.logIn(accountName);
            if (myProfile == null) {
                if (!Util.yesNoResponse("\n\t⚠ We cannot find your account.\n\tDo you want to re-try? (y/n): "))
                    return true; // show sign in option if user does not retry
            }
            else {
                boolean match = false;
                while (!match) {
                    String password = Util.promptPassword("Please enter your password: ");

                    if (password.equals(myProfile.getPassword()))
                        pass = match = true;
                    else {
                        if (!Util.yesNoResponse("Wrong password! Do you want to re-try? (y/n): "))
                            return true; // show sign in option if user does not retry
                    }
                }
            }
        } while (!pass);
        return false;
    }




    /**************************************************************************************
     * Create a new account for the user
     * prompt the user to input their information, including name, account name, password, and birthdate
     * if account name already exists, ask them to enter a different one
     * Then create a new profile and add that to the database.
     * Finally, take the user to the log in process where they log in to their new account.
     * 
     * @return whether or not to go back to the main screen(where the users log in)
     */
    public static boolean createNewAccount() {
        Util.clearScreen();
        Util.makeWordArt("welcome  (●'v'●)", Util.YELLOW, '¶', true, 130, 24, 15);
        Util.print("\n\t\tLet's create a new account for you!\n", Util.YELLOW);
        Util.print("\nPlease enter your first name: ", Util.GREEN);
        String firstName = input.next();
        Util.print("Please enter your last name: ", Util.GREEN);
        String lastName = input.next();
        String fullName = firstName + " " + lastName;
        String accName;
        input.nextLine();
        do {
            Util.print("Please enter your account name: ", Util.GREEN);
            accName = input.nextLine();

            if (dataBase.logIn(accName) != null) {
                if (!Util.yesNoResponse("Your account name already exist! Do you want to retry? (y/n): "))
                    return true;
            }
            else
                break;
        } while(true);
        
        String password = Util.setPassword();

        Util.println("\nPlease enter your day, month, year of birth respectively: ", Util.GREEN);
        final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
        DecimalFormat myFormat = new DecimalFormat("##");
        Util.print("Day: ", Util.GREEN);
        int day = Util.getInt(31, 1);
        Util.print("Month: ", Util.GREEN);
        int month = Util.getInt(12, 1);
        Util.print("Year: ", Util.GREEN);
        int year = Util.getInt(CURRENT_YEAR, CURRENT_YEAR - 100);
        String dateOfBirth = myFormat.format(month) + "/" + myFormat.format(day) + "/" + year;

        if (CURRENT_YEAR - year < MIN_AGE)
            Util.println("\n Sorry, you must be at least" + MIN_AGE + " to join social network ", Util.RED_BG_BRIGHT);
        else {
            dataBase.add(accName, fullName, new Profile(firstName, lastName, accName, dateOfBirth, password, null));
            Util.clearScreen();
            Util.println("\nCongratulations! Your account is created. Let's try login to your account!", Util.YELLOW_BOLD);
            Util.pressEnterToContinue();
            input.nextLine();
            return login();
        }
        return true;
    }




    /***************************************************************************************
     * This is the user's home screen, where they will be taken to after logging in
     * The user will be prompted to enter an action they want to do, including:
     *      manage their account,
     *      find someone in the network
     *      check and perform actions on their friend list
     *      See the people that they may know(friends of friends)
     *      See the friend list of one of a friends
     *      log out
     */
    public static void showHomeScreen() {
        // check whether to go back to the home screen
        // *(only true when user logs out or delete account)
        boolean exitHomeScreen = false;

        //
        while (!exitHomeScreen) {
            Util.clearScreen();
            Util.makeWordArt("Home", Util.CYAN_BRIGHT, '⁋', false, 50, 21, 13);
            // Display the options to the user
            Util.print("                                              \n ", Util.YELLOW_BG);
            Util.print("                                            ", null);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    1.  Manage your account                 ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    2.  Find someone                        ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    3.  Check your friend list              ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    4.  See people you may know             ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    5.  See the friend list of a friend     ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("    6.  Log out                             ", Util.CYAN_BOLD_BRIGHT);
            Util.print(" \n ", Util.YELLOW_BG);
            Util.print("                                            ", null);
            Util.println(" \n                                              \n", Util.YELLOW_BG);

            Util.print("Please enter the action you want to do: ", Util.GREEN);
            int action = Util.getInt(6, 1);

            Util.clearScreen();
            switch(action) {
                case 1:
                    ////// Manage Account
                    exitHomeScreen = manageAccount();
                    break;

                case 2:
                    ///// Find someone in the social network
                    findPeople();
                    break;

                case 3:
                    ///// See who's in your friend list and search for specific friend
                    manageFriendList();
                    break;
            
                ////// Check out the people that I may know
                case 4:
                    showFriendsOfFriends();
                    break;
                
                case 5:
                    ////// See the friend list of a chosen friend
                    Util.print("\nPlease enter the FULL NAME of a friend: ", Util.GREEN);
                    String lookFor = input.nextLine();
                    Util.clearScreen();
                    // check if the friend list has the input profile
                    Profile foundFriend = dataBase.findFriend(myProfile, lookFor);

                    if (foundFriend != null) {
                        Util.makeWordArt(foundFriend.getFirstName() + "'s friends :", 
                                         Util.CYAN, '*', true, 120, 22, 12);
                        if (showFriendList(foundFriend)) {
                            Profile found = findPersonInFriendList(foundFriend);
                            if (found != null) {
                                Util.clearScreen();
                                found.printProfile();
                                makeFriend(found);
                            }
                        }
                    }
                    break;

                case 6:
                    ////// Log out
                    if (Util.yesNoResponse("\n\nAre you sure you want to log out?\n" + "  Please enter 'y' or 'n': "))
                        exitHomeScreen = true;
                    break;
            }
            Util.pressEnterToContinue();
        }
    }



    /*****************************************************************************
     * Let the user manage their account. They can choose to:
     *      update their profile, which including changing name, birthdate, and status
     *      reset their password
     *      delete their account
     *
     * @return whether to exit the home screen.
     *         (only return true when the user delete the account)
     */
    public static boolean manageAccount() {
        Util.clearScreen();
        Util.makeWordArt("Account Setting", Util.CYAN, '§', true, 120, 23, 12);
        // Show the changes that the user can make
        Util.println("\t※※※※※※※※※※※※※※※※※※※※※※※※※※※※", Util.CYAN);
        Util.println("\t※※  1. Update profile     ※※", Util.CYAN_BRIGHT);
        Util.println("\t※※  2. Change password    ※※", Util.CYAN_BRIGHT);
        Util.println("\t※※  3. Delete acccount    ※※", Util.CYAN_BRIGHT);
        Util.println("\t※※  4. Go back            ※※", Util.CYAN_BRIGHT);
        Util.println("\t※※※※※※※※※※※※※※※※※※※※※※※※※※※※", Util.CYAN);

        // prompt for input from user
        Util.print("\nPlease enter the action you want to do next: ", Util.GREEN);
        int action = Util.getInt(4, 1);
        Util.clearScreen();

        /// Update profile
        //// Reset Password
        //// Delete account
        switch (action) {
            case 1 -> {
                Profile oldProfile = myProfile;
                Util.println("1. Name", Util.CYAN_BRIGHT);
                Util.println("1. Date of Birth", Util.CYAN_BRIGHT);
                Util.println("3. Status\n", Util.CYAN_BRIGHT);
                Util.print("Please select the option that you want to update: ", Util.GREEN);
                int option = Util.getInt(4, 1);
                if (option == 1) {
                    Util.print("\nPlease enter your new first name: ", Util.GREEN);
                    String newFirstName = input.next();
                    Util.print("Please enter your new last name: ", Util.GREEN);
                    String newLastName = input.next();
                    myProfile.setFirstName(newFirstName);
                    myProfile.setLastName(newLastName);
                    Util.println("Your name is now set as " + myProfile.getName() + "\n", Util.YELLOW_BOLD);
                } else if (option == 2) {
                    Util.println("\nPlease enter your day, month, year of birth respectively: ", Util.GREEN);
                    final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
                    DecimalFormat myFormat = new DecimalFormat("##");
                    Util.print("Day: ", Util.GREEN);
                    int day = Util.getInt(31, 1);
                    Util.print("Month: ", Util.GREEN);
                    int month = Util.getInt(12, 1);
                    Util.print("Year: ", Util.GREEN);
                    int year = Util.getInt(CURRENT_YEAR, CURRENT_YEAR - 100);
                    String dateOfBirth = myFormat.format(day) + "/" + myFormat.format(month) + "/" + year;
                    myProfile.setDateOfBirth(dateOfBirth);
                    Util.println("Your date of birth is now successfully set as: " +
                            myProfile.getDateOfBirth(), Util.YELLOW_BOLD);
                } else if (option == 3) {
                    Util.println("\nTell us what's in your mind, " + myProfile.getFirstName() + ": ", Util.GREEN);
                    String status = input.nextLine();
                    myProfile.setStatus(status);
                    Util.println("Thanks for sharing!", Util.PURPLE_BOLD_BRIGHT);
                }
                dataBase.updateProfile(oldProfile, myProfile);
            }
            case 2 -> {
                String password = Util.promptPassword("\nPlease enter your current password first: ");
                if (password.equals(myProfile.getPassword())) {
                    String newPassword = Util.setPassword();
                    myProfile.setPassword(newPassword);
                    Util.println("\nYour password is successfully reset!", Util.YELLOW_BOLD);
                } else
                    Util.println("Incorrect password! You will be brought back to the main screen.",
                            Util.YELLOW_BOLD);
            }
            case 3 -> {
                Util.clearScreen();
                Util.print("(T_T) Are you sure you want to permanently delete your account? \n",
                        Util.RED_BOLD_BRIGHT);
                if (Util.yesNoResponse("Please enter 'y' or 'n': ")) {
                    Util.print("\nPlease enter your password to delete your account: ", Util.GREEN);
                    String passw = input.next();
                    if (passw.equals(myProfile.getPassword())) {
                        dataBase.remove(myProfile.getAccountName());
                        Util.println("Your account is successfully removed!\n" +
                                "You will be taken back the main screen.", Util.YELLOW_BOLD);
                        return true;
                    } else
                        Util.println("Incorrect password! You will be brought back to the main screen.", Util.YELLOW_BOLD);
                }
            }
        }
        return false;
    }




    /***********************************************************************************
     * find a person in the network. 
     * if the person cannot be found, inform the user the person does not exist
     * if the person is not friend with the user, then asks if the user wants to make friend
     */
    public static void findPeople() {
        Util.print("\nPlease enter the FULL NAME of the person you want to find: ", Util.GREEN);
        String lookFor = input.nextLine();
        Util.clearScreen();

        Profile foundProfile = dataBase.getProfile(lookFor);
        if (foundProfile != null) {
            foundProfile.printProfile();
            if (!dataBase.areFriends(myProfile, foundProfile) && !foundProfile.equals(myProfile)) {
                System.out.println();
                showMutual(myProfile, foundProfile);
            }
            makeFriend(foundProfile);
        }
        else {
            Util.print("\n" + lookFor + " cannot be found in data base\n", Util.YELLOW_BOLD);
        }
    }




    /**************************************************************************************
     * Let the user perform actions on their friendlist, including:
     *      see all the people on their friendlist
     *      look for a person in their friendlist, then unfriend the person if the user wants
     */
    public static void manageFriendList() {
        Util.makeWordArt("Friends Manager", Util.CYAN, '$', true, 130, 22, 12);
        Util.print("\n1. See your friend list" + "\n2. Search for a friend in your friend list", Util.CYAN);
        Util.print("\n\n        Please enter the action you want to do next: ", Util.GREEN);
        int response = Util.getInt(2, 1);
        
        Util.clearScreen();
        if (response == 1) {
            Util.clearScreen();
            Util.makeWordArt("Your friends are", Util.YELLOW, '⁕', true, 130, 20, 15);
            if (showFriendList(myProfile))
                response = 2;
        }
        if (response == 2) {
            Profile foundFriend = findPersonInFriendList(myProfile);
            if (foundFriend != null) {
                Util.clearScreen();
                foundFriend.printProfile();

                Util.print("\n\nDo you want to remove " + foundFriend.getFirstName()
                        + " from the friend list?" + "\n    ", Util.GREEN);
                if (Util.yesNoResponse("Please enter 'y' or 'n': ")) {
                    dataBase.endFriendship(myProfile, foundFriend);
                    Util.println(foundFriend.getFirstName() + " is successfully removed from the friend list!\n", Util.YELLOW_BOLD);
                }
            }
        }
    }




    /**************************************************************************************
     * the "People that you may know" feature
     * display the friends of the user's friends as a recommendation for them to expand network
     */
    public static void showFriendsOfFriends() {
        LinkedQueue<Profile> queue = dataBase.getFriendsOfFriends(myProfile);

        if (!queue.isEmpty()) {
            Util.makeWordArt("You may know :", Util.CYAN, '*', true, 120, 20, 15);
            System.out.println("\n");

            while (!queue.isEmpty()) {
                Profile personIMayKnow = queue.dequeue();
                Util.print("\t(☞ﾟヮﾟ)☞   " + personIMayKnow.getName() + "\n", Util.YELLOW_BOLD_BRIGHT);
                showMutual(personIMayKnow, myProfile);
            }
        }
        else {
            Util.print("Sorry, we cannot find any friend recommendation for you!\n", Util.YELLOW_BOLD);
        }
        System.out.println();
    }




    /**************************************************************************************
     * Display all the friends of a person (profile).
     * 
     * @param profile: the profile to show the friend lists of
     * @return: whether to search for a specific person in the friend list displayed
     */
    public static boolean showFriendList(Profile profile) {
        System.out.print("\n");
        if (printFriendListOf(profile)) {
            return Util.yesNoResponse("\nDo you want to search for a specific person in this list? (y/n): ");
        }
        return false;
    }




    /**************************************************************************************
     * search for a person in a profile's friend list. 
     * 
     * @param: the profile that the user wants to search for a friend in the friend list of
     * @return: the profile that the user searched for. If the profile does not exist, return null
     */
    public static Profile findPersonInFriendList(Profile profile) {
        Profile result;
        Util.print("\n\nPlease enter the name of the person you are looking for: ", Util.GREEN);
        String lookFor = input.nextLine();
        result = dataBase.findFriend(profile, lookFor);
        return result;
    }



    /**************************************************************************************
     * Create a friendship between the user and another profile
     * 
     * @param profile: the profile that the user will make friend with
     */
    public static void makeFriend(Profile profile) {
        if (!dataBase.areFriends(profile, myProfile) && !profile.equals(myProfile)) {
            Util.println("\nDo you want to make friend with " +
                    profile.getName() + "?", Util.PURPLE_BOLD_BRIGHT);
            if (Util.yesNoResponse("Please enter 'y' or 'n': ")) {
                System.out.println("\n");
                dataBase.createFriendship(profile, myProfile);
            }
        }
        else {
            if (dataBase.areFriends(myProfile, profile))
                Util.print("\nYou and " + profile.getName() + " are already friends\n\n", Util.PURPLE_BRIGHT);
        }
    }




    /**************************************************************************************
     * print out all the people in the friend list of a profile.
     * If the friend list is empty, inform that the person has no friend yet
     * 
     * @param profile: the profile that the user wants to see the friend list of
     * @return: whether or not the friend list of the input profile is empty
     */
    public static boolean printFriendListOf(Profile profile) {
        LinkedQueue<Profile> queue = dataBase.getFriendListOf(profile);
        boolean isNotEmpty;

        if (!queue.isEmpty()) {
            isNotEmpty = true;
            while (!queue.isEmpty()) {
                Util.print("\t(☞ﾟヮﾟ)☞  " + queue.dequeue().getName() + "\n", Util.PURPLE_BRIGHT);
            }
            System.out.println();
        }
        else {
            isNotEmpty = false;
            Util.clearScreen();
            Util.println(profile.getName() + " has no friend yet!", Util.RED_BRIGHT);
        }
        return isNotEmpty;
    }



    
    /**************************************************************************************
     * Take 2 profiles and then show the number of mutual friends between them
     * Then print the list of the mutual friends on the screen
     * 
     * @param left,right: show the mutual friends between these profiles
     */
    public static void showMutual(Profile left, Profile right) {
        Util.print("\tThis person has " + dataBase.findMutualFriends(left, right) +
                " mutual friends with you", Util.PURPLE);

        LinkedQueue<Profile> mutualFriends = dataBase.getMutualFriends(left, right);
        if (!mutualFriends.isEmpty()) {
            Util.print(", including:\n\t", Util.PURPLE);
            while (!mutualFriends.isEmpty()) {
                Util.print("⁕ " + mutualFriends.dequeue().getName() + "  ", Util.PURPLE_BOLD_BRIGHT);
            }
        }
        System.out.println("\n");
    }
}


// https://codehackersblog.blogspot.com/2015/06/image-to-ascii-art-converter-in-java.html
