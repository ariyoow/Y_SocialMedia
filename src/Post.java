import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Post {
    private String txt;
    private List<String> comments;
    private int like;
    private List <String> likers;

    public Post(String txt) {
        this.txt = txt;
        this.comments = new ArrayList<>();
        this.like = 0;
        this.likers = new ArrayList<>();
    }

    public Post(String txt, int like) {
        this.txt = txt;
        this.comments = new ArrayList<>();
        this.like = like;
        this.likers = new ArrayList<>();
    }

    public Post(String txt, ArrayList<String> comments, int like, ArrayList<String> likers) {
        this.txt = txt;
        this.comments = comments;
        this.like = like;
        this.likers = likers;
    }

    public int addLike(String liker){
        likers.add(liker);
        return this.like++;
    }

    public int removeLike(String liker){
        likers.remove(liker);
        return this.like--;
    }

    public boolean hasLiked(String liker) {
        return likers.contains(liker);
    }

    public void addComment(String txt){
        this.comments.add(txt);
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void showDetailPost(){
        System.out.println(txt);
        System.out.println();
        System.out.println(like + "'likes");
        System.out.println(comments.toString());
    }
}
