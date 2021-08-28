import java.util.Calendar;

import ListPackage.ArrayList;

import java.io.Serializable;

public class Profile implements Serializable{
    private String firstName;
    private String lastName;
    private String name;
    private String birthday;
    private int age;
    private String status;
    private String password;
    private String accountName;
    private ArrayList<Post> newsFeed;
    private final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    
    public Profile(String firstName, String lastName, String accName, 
                   String birthday, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.password = password;
        this.accountName = accName;
        this.newsFeed = new ArrayList<Post>();
        if (status == null)
            status = "Hello, I am " + firstName;
        this.status = status;
        setName();
        calculateAge();
    }


    public String getName() { return name; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; setName(); }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; setName(); }

    public String getDateOfBirth() { return birthday; }

    public void setDateOfBirth(String dateOfBirth) { birthday = dateOfBirth; }

    public int getAge() { return age; }

    public String getStatus() { return status; }

    public void setStatus(String onlineStatus) { this.status = onlineStatus; }

    public String getPassword() { return password; }

    public String getAccountName() { return accountName; }

    public void setPassword(String password) { this.password = password; }


    /**
     * add a post to the end of the news feed
     * @param post the post to be added
     */
    public void addPostToNewsFeed(Post post) {
        newsFeed.add(post);
    }


    /**
     * print out the news feed in reverse order so that
     * the latest posts get to be printed out first.
     */
    public void showNewsFeed() {
        int count = 0;
        for (int i = newsFeed.getLength(); i > 0; i--) {
            Post post = newsFeed.getEntry(i);
            post.printPost();

            if (count == 2) {
                if (i > 1) {
                    if (Util.yesNoResponse("\n\nDo you want to see older posts? (y/n): ")) {
                        Util.clearScreen();
                        count = 0;
                    }
                    else
                        break;
                }
            }
            count++;
        }
    }


    // print out the publicable information of the profile
    public void printProfile() {
        String nameDisplayed = firstName + " " + lastName.charAt(0);
        Util.makeWordArt(nameDisplayed, Util.PURPLE, '⁕', false, 130, 22, 15);
        System.out.println(Util.YELLOW_BOLD_BRIGHT);
        System.out.print("\t" + age + " years old\n");
        System.out.print("\tBirthday: " + birthday);
        System.out.println("\n\tStatus: \"" + status + "\"");
        System.out.print(Util.RESET);
    }


    // Combine first name and last name to get fullname
    private void setName() { name = firstName + " " + lastName; }


    // calculate the age of the person according to the birthday
    private void calculateAge() {
        String[] str = birthday.split("/");
        age = CURRENT_YEAR - Integer.parseInt(str[2]);
    }

}
