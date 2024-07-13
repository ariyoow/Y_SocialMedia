import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class User {

    private Profile profile;
    private List<String> followers;
    private List<String> followings;
    private List<Post> posts;
    private Scanner sc;


    public User(Profile profile) {
        this.profile = profile;
        this.posts = new ArrayList<>();
        this.followers = new LinkedList<>();
        this.followings = new LinkedList<>();
        this.sc = new Scanner(System.in);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowings() {
        return followings;
    }

    public void setFollowings(ArrayList<String> followings) {
        this.followings = followings;
    }

    public void addFollower(String follower) {
        this.followers.add(follower);
    }

    public void removeFollower(String follower) {
        this.followers.remove(follower);
    }

    public void addFollowing(String following) {
        this.followings.add(following);
    }

    public void removeFollowing(String following) {
        this.followings.remove(following);
    }

    public boolean hasFollow(String follower) {
        return followings.contains(follower);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }


    public String userInfo() {
        return "User{" +
                "name='" + profile.getName() + '\'' +
                ", userName='" + profile.getUserName() + '\'' +
                '}';
    }


    public String userFullInfo() {
        return "Profile{" +
                "name='" + profile.getName() + '\'' +
                ", userName='" + profile.getUserName() + '\'' +
                ", bio='" + profile.getBio() + '\'' +
                ", email='" + profile.getEmail() + '\'' +
                ", password='" + profile.getPassword() + '\'' +
                '}';
    }
}
