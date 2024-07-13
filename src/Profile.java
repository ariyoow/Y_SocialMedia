import java.util.Scanner;

public class Profile {
    private String name;
    private String userName;
    private String bio;
    private String email;
    private String password;

    public Profile(String firstName, String lastName, String userName, String email, String password) {
        this.name = firstName + " " + lastName;
        this.userName = userName;
        this.bio = "Go to edit information to fill your bio.";
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String information() {
        return name + "\n" + bio + "\n" + userName;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
