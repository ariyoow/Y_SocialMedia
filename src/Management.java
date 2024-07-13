import java.util.Scanner;

public class Management {
    private final Scanner sc;
    private DataBase data;
    private UserMenu userMenu;

    public Management() {
        this.sc = new Scanner(System.in);
        this.data = new DataBase();
    }

    public void start() {
        System.out.println("""
                $--------------------------------------------$
                |    Welcome to                              |
                |                    *   *                   |
                |                     * *                    |
                |                      *                     |
                |                      *                     |
                |                      *                     |
                |                              Community.    |
                $--------------------------------------------$
                """);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Show Users");
            System.out.println("4. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    signInMenu();
                    break;
                case 2:
                    signUpMenu();
                    break;
                case 3:
                    showUsers();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
            }
        }
    }

    private void showUsers() {
        this.data.showUsers();
    }

    private int getUserChoice() {
        while (true)
            try {
                int choice;
                System.out.println("Enter your choice: ");
                if (sc.hasNextLine()) {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice >= 1 && choice <= 4) {
                        return choice;
                    } else
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println(e);
                System.out.println("invalid input, please try again..");
            }
    }

    public void signInMenu() {
        System.out.println("""
                #--------------------------------------------#
                |                   Sign In                  |
                #--------------------------------------------#
                """);
        System.out.print("| User Name: ");
        String username = sc.nextLine();
        System.out.print("| Password: ");
        String password = sc.nextLine();
        isExist(username, password);
    }

    private void isExist(String username, String pass) {
        boolean flag = false;

        for (User obj : this.data.getUsers()) {
            if (obj.getProfile().getUserName().equals(username)) {
                flag = true;
                if (obj.getProfile().getPassword().equals(pass))
                    this.userMenu = new UserMenu(obj, data);
                else {
                    System.out.println("Passwords is not correct. login failed. Please try again");
                    signInMenu();
                    return;
                }
            }
        }
        if (!flag) {
            System.out.println("This account does not exist. Create an account.");
            signUpMenu();
        }

    }


    public void signUpMenu() {
        System.out.print("""
                #--------------------------------------------#
                |                  Sign Up                   |
                #--------------------------------------------#
                """);
        System.out.print("| First Name: ");
        String firstName = getName();
        System.out.print("| Last Name: ");
        String lastName = getName();
        System.out.print("| User Name: ");
        String userName = getUsername();
        System.out.print("| Email: ");
        String email = sc.nextLine();
        System.out.print("| Password: ");
        String password = getPassword();

        System.out.println("Your account has been created");

        Profile prof = new Profile(firstName, lastName, userName, email, password);

        User user = new User(prof);
        this.data.getUsers().add(user);

        userMenu = new UserMenu(user, this.data);
    }

    private String getName() {
        while (true) {
            String name;
            if (sc.hasNextLine()) {
                name = sc.nextLine();
                if (isAlphabetic(name))
                    return name;
                else
                    System.out.print("Your name must contain only alphabetic characters, Please type again: ");
            }
        }
    }

    private boolean isAlphabetic(String name) {
        if (name.isEmpty()) {
            return false;
        } else
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c))
                    return false;
            }
        return true;
    }

    private String getUsername() {
        while (true) {
            String username;
            if (sc.hasNextLine()) {
                username = sc.nextLine();
                if (!beingRepetitive(username))
                    return username;
                else
                    System.out.print("Your username is repetitive, please type another again: ");
            }
        }
    }

    private boolean beingRepetitive(String username) {
        for (User user : data.getUsers()) {
            if (user.getProfile().getUserName().equals(username))
                return true;
        }
        return false;
    }

    private String getPassword() {
        while (true) {
            if (sc.hasNextLine()) {
                String pass = sc.nextLine();
                if (isValidPassword(pass)) {
                    System.out.print("| Confirm Password: ");
                    if (sc.hasNextLine()) {
                        String confPass = sc.nextLine();
                        if (!pass.equals(confPass)) {
                            System.out.print("Passwords do not match. Registration failed. Please try again.\n| Password: ");
                        } else
                            return pass;
                    }
                } else {
                    System.out.print("Your Password Must contain Upper and Lower Case letter , number and symbol. Please try again.\n| Password: ");
                }
            }
        }
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }

            if (hasLower && hasUpper && hasDigit && hasSpecial) {
                return true;
            }
        }

        return false;
    }
}
