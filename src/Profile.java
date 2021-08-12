import java.util.Objects;
import java.util.Calendar;

public class Profile {
    private String firstName;
    private String lastName;
    private String name;
    private String birthday;
    private int age;
    private String status;
    private String password;
    private final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    public Profile(String firstName, String lastName, String birthday, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.password = password;
        this.status = status;
        setName();
        calculateAge();
    }


    public String getName() {
        return name;
    }


    // Combine first name and last name to get fullname
    private void setName() {
        name = firstName + " " + lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName();
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName();
    }


    public String getDateOfBirth() {
        return birthday;
    }


    public void setDateOfBirth(String dateOfBirth) {
        birthday = dateOfBirth;
    }


    // calculate the age of the person according to the birthday
    private void calculateAge() {
        String[] str = birthday.split("/");
        age = CURRENT_YEAR - Integer.parseInt(str[2]);
    }


    public int getAge() {
        return age;
    }


    // print out the information of the person
    public void printProfile() {
        System.out.print(name + " - " + age + " years old\n");
        System.out.print("Birthday: " + birthday);
        System.out.println("\nStatus: \"" + status + "\"");
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String onlineStatus) {
        this.status = onlineStatus;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // check if the profile matches another one
    @Override
    public boolean equals(Object object) {
        // self check
        if (this == object)
            return true;
        // null check
        if (object == null)
            return false;
        // type check and case
        if (getClass() != object.getClass())
            return false;
        Profile profile = (Profile) object;
        // field comparison
        return Objects.equals(name, profile.name) && Objects.equals(age, profile.age)
                && Objects.equals(password, profile.password);
    }
}
