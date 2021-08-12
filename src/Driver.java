/**
 * @author Allen Tran
 * Partners: Yasaman Baher, Luan Truong, Shubham Goswami
 */

import GraphPackage.LinkedQueue;

import java.text.DecimalFormat;
import java.util.*;

public class Driver {
    private static final int MIN_AGE = 13;
    private static Scanner input = new Scanner(System.in);
    private static DataBase dataBase = new DataBase();
    private static Profile myProfile = null;


    /*
    MAIN METHOD
     */
    public static void main(String[] args) {
        System.out.println("\n");
        boolean exit = false;

        initializeDataBase();


        while (!exit) {
            exit = signInOption();
            if (!exit)
                showHomeScreen();
        }
    }


    // Create a few accounts in the data base
    public static void initializeDataBase() {

        // initialize the people's first name
        String[] names = new String[]{"Alex", "Alice", "Allen", "Luan", "Shubham", "Yasaman", "John",
                "Steve", "Wanda", "Mia", "Mike", "Mirsaeid", "Jonathan", "Henry", "Thanos", "Tony",
                "Roger", "Amanda", "Logan", "Kevin"};

        // initialize the people's last names
        String[] lastNames = new String[]{"A", "B", "Tran", "Truong", "Goswami", "Baher", "Doe",
                "Rogers", "Maximoff", "Tran", "Pence", "Abol", "X", "X", "X", "Stark", "X", "Waller",
                "X", "Do"};

        // initialize the passwords for the accounts. I use the same one for all so we don't have to look up.
        String[] passwords = new String[names.length];
        Arrays.fill(passwords, "12345");

        // initialize the people's date of birth randomly
        String[] birthdays = new String[names.length];
        Random rand = new Random();
        DecimalFormat myFormat = new DecimalFormat("##");
        for (int i = 0; i < birthdays.length; i++) {
            int year = rand.nextInt(2021 - MIN_AGE - 1940) + 1940;
            int month = rand.nextInt(11) + 1;
            int dayBound = 31;
            if (month == 2)
                dayBound = 28;
            else if ((month % 2 == 0 && month < 8) || (month % 2 != 0 && month > 8))
                dayBound = 30;
            int day = rand.nextInt(dayBound - 1) + 1;
            birthdays[i] = myFormat.format(month) + "/" + myFormat.format(day) + "/" + year;
        }

        // create profiles and add them to our data base
        Profile[] profiles = new Profile[names.length];
        for (int i = 0; i < names.length; i++) {
            profiles[i] = new Profile(names[i], lastNames[i], birthdays[i], passwords[i], "Hello, world!");
            dataBase.add(names[i] + " " + lastNames[i], profiles[i]);
        }

        // assign friends randomly
        for (int i = 0; i < names.length * 4; i++)
            dataBase.createFriendship(profiles[rand.nextInt(names.length)], profiles[rand.nextInt(names.length)]);

        /*for (Profile profile : profiles) {
            System.out.print("\n");
            printFriendListOf(profile);
            System.out.print("\n");
        }*/

        pressAnyKeyToContinue();
    }


    // The main screen where we will do sign in or create account
    public static boolean signInOption() {
        boolean exit = false;
        boolean goBackToMainScreen = true;

        while (goBackToMainScreen) {
            // display the main screen and show the users options to sign in
            System.out.print("1. Log in\n" + "2. Create new account\n" + "3. Exit\n" +
                    "\n       Please enter an integer to perform action: ");
            int choice = getInt(3, 1);


            // Option 1, sign in to an existing account
            if (choice == 1) {
                goBackToMainScreen = login();
            }


            // Option 2, create a new account
            else if (choice == 2) {
                System.out.print("\n\nLet's create a new account for you!\n");

                System.out.print("\nLet's get started by entering your first name: ");
                String firstName = input.next();
                System.out.print("Now please enter your last name: ");
                String lastName = input.next();
                String fullName = firstName + " " + lastName;

                String password = setPassword();

                System.out.println("\nPlease enter your day, month, year of birth respectively: ");
                final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
                DecimalFormat myFormat = new DecimalFormat("##");
                System.out.print("Day: ");
                int day = getInt(31, 1);
                System.out.print("Month: ");
                int month = getInt(12, 1);
                System.out.print("Year: ");
                int year = getInt(CURRENT_YEAR, CURRENT_YEAR - 100);
                String dateOfBirth = myFormat.format(month) + "/" + myFormat.format(day) + "/" + year;

                if (CURRENT_YEAR - year < MIN_AGE)
                    System.out.println("\nSorry, you are under the required age to join social network" +
                            "\nLet's come back later when you're " + MIN_AGE + " years old, thank you!");
                else {
                    dataBase.add(fullName, new Profile(firstName, lastName, dateOfBirth, password, null));
                    System.out.println("\nCongratulations! Your account is created. Let's try login to your account!");
                    goBackToMainScreen = login();
                }
            }


            // Option 3, exit the program
            else {
                exit = true;
                goBackToMainScreen = false;
            }
        }
        return exit;
    }


    // The home screen after we sign in to the program, where you can explore your account
    public static void showHomeScreen() {
        boolean done = false;
        while (!done) {
            // Show the user what they can do
            System.out.print("\n\n====================");
            System.out.print("\n1. Manage your account\n" + "2. Find Someone\n" + "3. Check your friends list\n" +
                    "4. See people that you may know\n" + "5. See the friend list of a friend\n" + "6. Log out\n");
            System.out.print("\n      Please enter the action you want to do: ");
            int action = getInt(6, 1);


            ////// Manage Account
            if (action == 1) {
                System.out.print("\n1. Update profile" + "\n2. Change your password" + "\n3. Delete account\n");
                System.out.print("      Please enter the action you want to do next: ");
                int action2 = getInt(3, 1);

                /// Update profile
                if (action2 == 1) {
                    Profile oldProfile = myProfile;
                    System.out.print("\n1. Name\n" + "2. Date of birth\n" + "3. status\n" + "4. change your password\n");
                    System.out.print("      Please select the option that you want to update: ");
                    int option = getInt(4, 1);

                    if (option == 1) {
                        System.out.print("\nPlease enter your new first name: ");
                        String newFirstName = input.next();
                        System.out.print("Please enter your new last name: ");
                        String newLastName = input.next();
                        myProfile.setFirstName(newFirstName);
                        myProfile.setLastName(newLastName);
                        System.out.print("Your name is now set as " + myProfile.getName());
                    } else if (option == 2) {
                        System.out.println("\nPlease enter your day, month, year of birth respectively: ");
                        final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
                        DecimalFormat myFormat = new DecimalFormat("##");
                        System.out.print("Day: ");
                        int day = getInt(31, 1);
                        System.out.print("Month: ");
                        int month = getInt(12, 1);
                        System.out.print("Year: ");
                        int year = getInt(CURRENT_YEAR, CURRENT_YEAR - 100);
                        String dateOfBirth = myFormat.format(day) + "/" + myFormat.format(month) + "/" + year;
                        myProfile.setDateOfBirth(dateOfBirth);
                        System.out.println("Your date of birth is now successfully set as: " + myProfile.getDateOfBirth());
                    } else if (option == 3) {
                        System.out.println("\nTell us what's in your mind, " + myProfile.getFirstName() + ": ");
                        input.nextLine();
                        String status = input.nextLine();
                        myProfile.setStatus(status);
                        System.out.println("Thanks for sharing!");
                    }
                    //dataBase.updateProfile(oldProfile, myProfile);
                }

                //// Reset Password
                else if (action2 == 2) {
                    System.out.print("\nPlease enter your current password first: ");
                    String password = input.next();
                    if (password.equals(myProfile.getPassword())) {
                        String newPassword = setPassword();
                        myProfile.setPassword(newPassword);
                        System.out.println("\nYour password is successfully reset!");
                    } else
                        System.out.print("Incorrect password! You will be brought back to the main screen.\n\n\n");
                }


                //// Delete account
                else if (action2 == 3) {
                    System.out.print("\n\nAre you sure you want to permanently delete your account?" +
                            "\nPlease enter 'y' or 'n': ");
                    if (yesNoResponse()) {
                        System.out.print("\nPlease enter your password to delete your account: ");
                        String password = input.next();
                        if (password.equals(myProfile.getPassword())) {
                            dataBase.remove(myProfile.getName());
                            done = true;
                        } else
                            System.out.print("Incorrect password! You will be brought back to the main screen.\n\n\n");
                    }
                }
            }


            ///// Find someone in the social network
            else if (action == 2) {
                System.out.print("\nPlease enter the name of the person you want to look for: ");
                input.nextLine();
                String lookFor = input.nextLine();
                System.out.print("\n");

                Profile foundProfile = dataBase.getProfile(lookFor);
                if (foundProfile != null) {
                    foundProfile.printProfile();
                    if (!dataBase.areFriends(myProfile, foundProfile) && !foundProfile.equals(myProfile)) {
                        System.out.println();
                        showMutual(myProfile, foundProfile);
                    }
                    makeFriend(foundProfile);
                } else {
                    System.out.print("\n" + lookFor + " cannot be found in data base\n");
                }
            }


            ///// See who's in your friend list and search for specific friend
            else if (action == 3) {
                System.out.print("\n1. See your friend list" + "\n2. Search for a friend in your friend list");
                System.out.print("\n        Please enter the action you want to do next: ");
                int response = getInt(2, 1);
                if (response == 1) {
                    if (showFriendList(myProfile))
                        response = 2;
                }
                if (response == 2) {
                    Profile foundFriend = findPersonInFriendList(myProfile);
                    if (foundFriend != null) {
                        foundFriend.printProfile();

                        System.out.print("\n\nDo you want to remove " + foundFriend.getFirstName()
                                + " from the friend list?" + "\n    Please enter 'y' or 'n': ");
                        if (yesNoResponse()) {
                            dataBase.endFriendship(myProfile, foundFriend);
                            System.out.println(foundFriend.getFirstName() + " is successfully removed from the friend list!\n");
                        }
                    }
                }
            }

            ////// Check out the people that I may know
            else if (action == 4) {
                LinkedQueue<Profile> queue = dataBase.getFriendsOfFriends(myProfile);

                if (!queue.isEmpty()) {
                    System.out.print("\n\nSome people you may know: \n\n");
                    while (!queue.isEmpty()) {
                        Profile personIMayKnow = queue.dequeue();
                        System.out.print("***  " + personIMayKnow.getName() + "\n");
                        showMutual(personIMayKnow, myProfile);
                    }
                } else {
                    System.out.print("Sorry, we cannot find any friend recommendation for you!\n");
                }
                System.out.println();
            }


            ////// See the friend list of a chosen friend
            else if (action == 5) {
                System.out.print("\nPlease enter the name of your friend that you would like to see their friends of: ");
                input.nextLine();
                String lookFor = input.nextLine();
                Profile foundFriend = dataBase.findFriend(myProfile, lookFor);
                if (foundFriend != null) {
                    System.out.println("\nHere are " + lookFor + "'s friends: ");
                    if (showFriendList(foundFriend)) {
                        Profile found = findPersonInFriendList(foundFriend);
                        if (found != null) {
                            System.out.println("\n");
                            found.printProfile();
                            makeFriend(found);
                        }
                    }
                }
            }

            ////// Log out
            else if (action == 6) {
                System.out.print("\n\nAre you sure you want to log out?\n" + "  Please enter 'y' or 'n': ");
                if (yesNoResponse())
                    done = true;
            }
        }
        pressAnyKeyToContinue();
    }


    public static boolean showFriendList(Profile profile) {
        System.out.print("\n");
        if (printFriendListOf(profile)) {
            System.out.print("\nDo you want to search for a specific person in this list? (y/n): ");
            return yesNoResponse();
        }
        return false;
    }


    public static Profile findPersonInFriendList(Profile profile) {
        Profile result;
        System.out.print("\n\nPlease enter the name of the person you are looking for: ");
        input.nextLine();
        String lookFor = input.nextLine();
        result = dataBase.findFriend(profile, lookFor);
        return result;
    }


    public static void makeFriend(Profile profile) {
        if (!dataBase.areFriends(profile, myProfile) && !profile.equals(myProfile)) {
            System.out.println("\nDo you want to make friend with " +
                    profile.getName() + "?\n Please enter 'y' or 'n': ");
            if (yesNoResponse()) {
                dataBase.createFriendship(profile, myProfile);
                System.out.println("\n");
            }
        } else {
            if (dataBase.areFriends(myProfile, profile))
                System.out.print("\nYou and " + profile.getName() + " are already friends\n\n");
        }
    }


    public static boolean printFriendListOf(Profile profile) {
        LinkedQueue<Profile> queue = dataBase.getFriendListOf(profile);
        boolean isNotEmpty;

        if (!queue.isEmpty()) {
            isNotEmpty = true;
            while (!queue.isEmpty()) {
                System.out.print(queue.dequeue().getName() + "   ");
            }
            System.out.println();
        } else {
            isNotEmpty = false;
            System.out.println(profile.getName() + " has no friend yet!");
        }
        return isNotEmpty;
    }


    public static void showMutual(Profile left, Profile right) {
        System.out.print("This person has " + dataBase.findMutualFriends(left, right) +
                " mutual friends with you");

        LinkedQueue<Profile> mutualFriends = dataBase.getMutualFriends(left, right);
        if (!mutualFriends.isEmpty()) {
            System.out.print(", including:\n");
            while (!mutualFriends.isEmpty()) {
                System.out.print(mutualFriends.dequeue().getName() + "   ");
            }
            System.out.println("\n");
        } else
            System.out.println("\n");
    }


    // get an integer and check if in desired range
    public static int getInt(int max, int min) {
        int result = 0;
        boolean pass;
        do {
            pass = true;
            try {
                result = input.nextInt();
            } catch (NumberFormatException e) {
                System.out.print("Could not convert input to an integer. Please re-enter: ");
                pass = false;
            } catch (Exception e) {
                System.out.print("There was an error with System.in. Please re-enter: ");
                pass = false;
            }
            if (pass)
                if (result > max || result < min) {
                    System.out.print("Input out of range, please re-enter: ");
                    pass = false;
                }
        } while (!pass);
        return result;
    }


    // get yes no response from the user
    public static boolean yesNoResponse() {
        char response;
        boolean pass = false;
        boolean result = false;

        while (!pass) {
            response = input.next().charAt(0);
            response = Character.toLowerCase(response);

            if (response == 'y') {
                pass = true;
                result = true;
            } else if (response == 'n') {
                pass = true;
                result = false;
            } else
                System.out.print("Invalid input! Please re-enter (y/n): ");
        }
        return result;
    }


    // the login process
    public static boolean login() {
        boolean pass = false;
        do {
            System.out.print("\nPlease enter your first name: ");
            String firstName = input.next();
            System.out.print("Please enter your last name: ");
            String lastName = input.next();
            String accountName = firstName + " " + lastName;

            myProfile = dataBase.getProfile(accountName);
            if (myProfile == null)
                System.out.println("We cannot find your account. Let's try it one more time!");
            else {
                boolean match = false;
                System.out.print("Please enter your password: ");
                while (!match) {
                    String password = input.next();

                    if (password.equals(myProfile.getPassword()))
                        pass = match = true;
                    else
                        System.out.print("Wrong password! Please re-enter: ");
                }
            }
        } while (!pass);
        return false;
    }


    // the process of setting the password for the account
    public static String setPassword() {
        String password = "";
        boolean match = false;
        while (!match) {
            System.out.print("\nPlease enter your password: ");
            password = input.next();
            System.out.print("Please confirm your password: ");
            String confirmPass = input.next();
            if (confirmPass.equals(password)) {
                match = true;
            } else
                System.out.println("\nThis does not match your password, let's try it one more time!");
        }
        return password;
    }


    // the "Press any key to continue..." feature
    private static void pressAnyKeyToContinue() {
        System.out.print("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
        System.out.print("\n\n\n");
    }
}
/*
Sample Run From Code:
Mirsaeid is now friend with Henry
Steve is now friend with Amanda
Allen is now friend with Shubham
Shubham is now friend with Amanda
Alex is now friend with Mirsaeid
Steve is now friend with Jonathan
Thanos is now friend with Jonathan
Thanos is now friend with Tony
Wanda is now friend with Tony
Tony is now friend with Alex
John is now friend with Yasaman
Luan is now friend with John
Thanos is now friend with Mirsaeid
Allen is now friend with Alex
Alice is now friend with Amanda
Thanos is now friend with Shubham
Alice is now friend with Shubham
Jonathan is now friend with John
Amanda is now friend with Yasaman
Wanda is now friend with Henry
Steve is now friend with Roger
Luan is now friend with Jonathan
Allen is now friend with Henry
Luan is now friend with Steve
Steve is now friend with Thanos
Mike is now friend with Kevin
Alice is now friend with Mike
Mike is now friend with Amanda
Roger is now friend with Amanda
Jonathan is now friend with Wanda
John is now friend with Kevin
Jonathan is now friend with Shubham
Shubham is now friend with Henry
Kevin is now friend with Amanda
Tony is now friend with Henry
Wanda is now friend with Logan
Alex is now friend with John
Steve is now friend with Wanda
Jonathan is now friend with Yasaman
Roger is now friend with Yasaman
Kevin is now friend with Luan
Henry is now friend with Steve
Wanda is now friend with Roger
Jonathan is now friend with Alex
Mirsaeid is now friend with Steve
Allen is now friend with Amanda
Steve is now friend with Alice
Yasaman is now friend with Alex
Luan is now friend with Roger
Jonathan is now friend with Kevin
Henry is now friend with Amanda
Alex is now friend with Henry
Mia is now friend with Mike
Thanos is now friend with John
Tony is now friend with Amanda
Luan is now friend with Thanos
Yasaman is now friend with Henry
John is now friend with Amanda
Alex is now friend with Steve
Shubham is now friend with Logan
Alex is now friend with Wanda
Tony is now friend with Yasaman
John is now friend with Henry
Amanda is now friend with Alex
Mirsaeid is now friend with Luan
Press Enter to continue...+



1. Log in
2. Create new account
3. Exit

       Please enter an integer to perform action: 1

Please enter your first name: Allen
Please enter your last name: Tran
Please enter your password: 12345


====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 2

Please enter the name of the person you want to look for: Yasaman Baher

Yasaman Baher - 16 years old
Birthday: 10/15/2005
Status: "Hello, world!"

This person has 3 mutual friends with you, including:
Alex A   Henry X   Amanda Waller


Do you want to make friend with Yasaman Baher?
 Please enter 'y' or 'n':
y
Yasaman is now friend with Allen




====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 4


Some people you may know:

***  Thanos X
This person has 1 mutual friends with you, including:
Shubham Goswami

***  Alice B
This person has 2 mutual friends with you, including:
Amanda Waller   Shubham Goswami

***  Jonathan X
This person has 3 mutual friends with you, including:
Shubham Goswami   Yasaman Baher   Alex A

***  Logan X
This person has 1 mutual friends with you, including:
Shubham Goswami

***  Mirsaeid Abol
This person has 2 mutual friends with you, including:
Henry X   Alex A

***  Tony Stark
This person has 4 mutual friends with you, including:
Alex A   Henry X   Amanda Waller   Yasaman Baher

***  John Doe
This person has 4 mutual friends with you, including:
Yasaman Baher   Alex A   Amanda Waller   Henry X

***  Steve Rogers
This person has 3 mutual friends with you, including:
Amanda Waller   Henry X   Alex A

***  Wanda Maximoff
This person has 2 mutual friends with you, including:
Henry X   Alex A

***  Mike Pence
This person has 1 mutual friends with you, including:
Amanda Waller

***  Roger X
This person has 2 mutual friends with you, including:
Amanda Waller   Yasaman Baher

***  Kevin Do
This person has 1 mutual friends with you, including:
Amanda Waller




====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 5

Please enter the name of your friend that you would like to see their friends of: Amanda Waller

Here are Amanda Waller's friends:

Steve Rogers   Shubham Goswami   Alice B   Yasaman Baher   Mike Pence   Roger X   Kevin Do   Allen Tran   Henry X   Tony Stark   John Doe   Alex A

Do you want to search for a specific person in this list? (y/n): n


====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 1

1. Update profile
2. Change your password
3. Delete account
      Please enter the action you want to do next: 1

1. Name
2. Date of birth
3. status
4. change your password
      Please select the option that you want to update: 3

Tell us what's in your mind, Allen:
Hello, world!
Thanks for sharing!


====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 2

Please enter the name of the person you want to look for: Shubham Goswami

Shubham Goswami - 81 years old
Birthday: 5/17/1940
Status: "Hello, world!"

You and Shubham Goswami are already friends



====================
1. Manage your account
2. Find Someone
3. Check your friend list
4. See people that you may know
5. See the friend list of a friend
6. Log out

      Please enter the action you want to do: 6


Are you sure you want to log out?
  Please enter 'y' or 'n': y
Press Enter to continue...+



1. Log in
2. Create new account
3. Exit

       Please enter an integer to perform action: 3

Process finished with exit code 0
 */