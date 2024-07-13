import java.util.Scanner;

public class UserMenu {
    private User user;
    private DataBase data;
    private final Scanner sc;

    public UserMenu(User user, DataBase data) {
        this.user = user;
        this.data = data;
        this.sc = new Scanner(System.in);
        userMenu();
    }

    public void userMenu() {
        while (true) {
            System.out.println("""
                    #--------------------------------------------#
                    |                 User Menu                  |
                    ----------------------------------------------
                    |Edit information : type i                   |
                    |Post Page : type p                          |
                    |New Post : type n                           |
                    |Search Menu : type s                        |
                    |Exit : type e                               |
                    |Delete Account : type d                     |
                    #--------------------------------------------#
                    """);
            System.out.print("Type your request : ");
            char command = Character.toLowerCase(sc.nextLine().charAt(0));

            switch (command) {
                case 'i':
                    editInformation();
                    break;
                case 'p':
                    postPage(this.user);
                    accessToOwner();
                    break;
                case 'n':
                    newPost();
                    break;
                case 's':
                    searchMenu();
                    break;
                case 'e':
                    return;
                case 'd':
                    this.data.getUsers().remove(this.user);
                    System.out.println("Your account has been deleted.");
                    return;
                default:
                    System.out.println("Access denied - You must choose between 'i', 'p', 'n', 's', 'e' and 'd'.\nPlease choose again.");
            }
        }
    }

    public void editInformation() {
        System.out.println("""
                #--------------------------------------------#
                |          User Menu / information           |
                ----------------------------------------------
                |Show Full Information : type s              |
                |Edit Name : type n                          |
                |Edit Username : type u                      |
                |Edit Email : type e                         |
                |Edit Password : type p                      |
                |Edit Bio : type b                           |
                #--------------------------------------------#
                """);
        System.out.print("Type your request : ");
        char command = Character.toLowerCase(sc.nextLine().charAt(0));

        switch (command) {
            case 's':
                System.out.println(this.user.userFullInfo());
                break;
            case 'n':
                System.out.print("Enter new first name: ");
                String firstName = getName();
                System.out.print("Enter new last name: ");
                String lastName = getName();
                this.user.getProfile().setName(firstName + " " + lastName);
                break;
            case 'u':
                System.out.print("Enter new user name: ");
                this.user.getProfile().setUserName(getUsername());
                break;
            case 'e':
                System.out.print("Enter new email: ");
                this.user.getProfile().setEmail(sc.nextLine());
                break;

            case 'p':
                newPassword();
                break;
            case 'b':
                System.out.println("Enter new bio: ");
                this.user.getProfile().setBio(sc.nextLine());
                break;
            default:
                System.out.println("Access denied - You must choose between 'f', 'u', 'e' or 'p'.\nPlease choose again.");
                editInformation();
        }
    }

    private void newPassword() {
        while (true) {
            System.out.println("Enter your previous password: ");
            String lPass = sc.nextLine();
            if (lPass.equals(this.user.getProfile().getPassword())) {
                System.out.println("Enter new password: ");
                this.user.getProfile().setPassword(getPassword());
            }
        }
    }

    public void postPage(User user) {
        System.out.println("""
                #--------------------------------------------#
                |                 Post Page                  |
                ----------------------------------------------
                """);
        System.out.println(user.getProfile().information());
        System.out.println("Followings" + user.getFollowings().toString());
        System.out.println("Followers" + user.getFollowings().toString());

        for (Post post : user.getPosts()) {
            System.out.println((user.getPosts().indexOf(post) + 1) + "- " + post.getTxt());
        }

        System.out.println("#--------------------------------------------#");

    }

    private void accessToOwner() {
        System.out.println("""
                |Detail Of Posts : type d                    |
                |Delete Any Posts : type h                   |
                |Exit : type anything                        |
                #--------------------------------------------#
                """);
        char input = Character.toLowerCase(sc.nextLine().charAt(0));

        if (input == 'd') {
            System.out.print("type number of post : ");
            try {
                int postNumber = Integer.parseInt(sc.nextLine());
                if (postNumber >= 1 && postNumber <= this.user.getPosts().size()) {
                    detailOfPost(postNumber, this.user);
                } else {
                    System.out.println("Invalid post number. Please try again.");
                    accessToOwner();
                }
            } catch (NumberFormatException e) {
                System.err.println(e);
                System.out.println("Invalid input. Please enter a valid post number or 'n'.");
                accessToOwner();
            }
        } else if (input == 'h') {
            System.out.print("type number of post : ");
            try {
                int postNumber = Integer.parseInt(sc.nextLine());

                if (postNumber >= 1 && postNumber <= user.getPosts().size()) {
                    postNumber -= 1;
                    this.user.getPosts().remove(postNumber);
                    System.out.println("Post deleted successfully.");
                    postPage(this.user);
                } else {
                    System.out.println("Invalid post number. Please try again.");
                    accessToOwner();
                }
            } catch (NumberFormatException e) {
                System.err.println(e);
                System.out.println("Invalid input. Please enter a valid post number or 'n'.");
                accessToOwner();
            }
        }
    }

    private void newPost() {
        System.out.println("Enter your new text. Your text must have a maximum of 200 characters:");
        String newText = sc.nextLine();

        if (newText.length() > 200) {
            System.out.println("Text exceeds the maximum limit of 200 characters. Post not created.");
        } else {
            this.user.getPosts().add(new Post(newText));
            System.out.println("Post added successfully!");
        }
        userMenu();
    }

    private void detailOfPost(int postNumber, User user) {
        try {
            postNumber = postNumber - 1;
            System.out.println("--------------------------------------------");
            user.getPosts().get(postNumber).showDetailPost();
            likeOrUnlikePost(postNumber, user);
            commentPost(postNumber, user);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("your post number is unavailable , please try again later...");
        }
    }

    private void likeOrUnlikePost(int selectedPostIndex, User user) {
        System.out.println("For Like, type 'l'; for Unlike, type 'u'; for nothing, type 'n':");
        char userChoice = Character.toLowerCase(sc.nextLine().charAt(0));

        try {
            if (userChoice == 'l') {
                if (!user.getPosts().get(selectedPostIndex).hasLiked(this.user.getProfile().getUserName())) {
                    user.getPosts().get(selectedPostIndex).addLike(this.user.getProfile().getUserName());
                    System.out.println("You liked the post.");
                } else {
                    System.out.println("You have already liked this post.");
                }
            } else if (userChoice == 'u') {
                if (user.getPosts().get(selectedPostIndex).hasLiked(this.user.getProfile().getUserName())) {
                    user.getPosts().get(selectedPostIndex).removeLike(this.user.getProfile().getUserName());
                    System.out.println("You unliked the post.");
                } else {
                    System.out.println("You haven't liked this post yet.");
                }
            } else if (userChoice != 'n') {
                throw new IllegalArgumentException("Invalid input. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            likeOrUnlikePost(selectedPostIndex, user);
        }
    }


    private void commentPost(int selectedPostIndex, User user) {
        System.out.println("If you want to comment, type 'y'; otherwise, type 'n':");
        char userInput = Character.toLowerCase(sc.nextLine().charAt(0));

        try {
            if (userInput == 'y') {
                System.out.println("Type your comment:");
                String comment = sc.nextLine();
                user.getPosts().get(selectedPostIndex).addComment(comment);
                System.out.println("Your comment has been added.");
            } else if (userInput != 'n') {
                throw new IllegalArgumentException("Invalid input. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            commentPost(selectedPostIndex, user);
        }
    }

    private void searchMenu() {
        System.out.print("""
                #--------------------------------------------#
                |                Search Menu                 |
                ----------------------------------------------
                |Enter username:\s""");
        String username = sc.nextLine();
        System.out.println("#--------------------------------------------#");
        User user = null;
        for (User i : data.getUsers())
            if (i.getProfile().getUserName().equals(username)) {
                user = i;
                postPage(user);
                accessToVisitor(user);
            }
    }

    private void accessToVisitor(User user) {
        System.out.println("""
                |Detail Of Posts : type d                    |
                |Follow The Page : type f                    |
                |Exit : type anything                        |
                #--------------------------------------------#
                """);
        char input = Character.toLowerCase(sc.nextLine().charAt(0));
        if (input == 'd') {
            System.out.print("type number of post : ");
            try {
                int postNumber = Integer.parseInt(sc.nextLine());
                if (postNumber >= 1 && postNumber <= user.getPosts().size()) {
                    detailOfPost(postNumber, user);
                } else {
                    System.out.println("Invalid post number. Please try again.");
                    accessToVisitor(user);
                }
            } catch (NumberFormatException e) {
                System.err.println(e);
                System.out.println("Invalid input. Please enter a valid post number or 'n'.");
                accessToVisitor(user);
            }
        } else if (input == 'f') {
            follow(user);
        }
    }

    private void follow(User user) {
        System.out.println("for FOLLOW type f and for UNFOLLOW type u : ");

        char command = Character.toLowerCase(sc.nextLine().charAt(0));

        if (command == 'f') {
            if (!this.user.hasFollow(this.user.getProfile().getUserName())) {
                user.addFollower(this.user.getProfile().getUserName());
                this.user.addFollowing(user.getProfile().getUserName());
                System.out.println("Follow was successfully!");
            } else
                System.out.println("You have already follow this account.");
        } else if (command == 'u') {
            if (user.hasFollow(this.user.getProfile().getUserName())) {
                user.removeFollower(this.user.getProfile().getUserName());
                this.user.removeFollowing(this.user.getProfile().getUserName());
                System.out.println("Unfollow was successfully!");
            } else
                System.out.println("You have not follow this account yet.");
        } else {
            System.out.println("Access denied - You must choose between 'f' and 'u'.Please choose again.");
            follow(user);
        }
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
                    System.out.print("| Enter Confirm Password: ");
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
