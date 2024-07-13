
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private List<User> users;

    public DataBase() {
        this.users = new LinkedList<>();
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void showUsers(){
        System.out.println("users[");
        for (User user : users) {
            System.out.print(users.indexOf(user)+1);
            System.out.println("_" + user.userInfo());
        }
        System.out.println("]");
    }
}
