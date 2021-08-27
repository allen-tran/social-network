/**
 * Represents a post on the social media
 * Contains the information about the content of the post,
 * the author, and the time the post is published.
 */

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;

public class Post implements Serializable {
    private String content;      // the content of the post
    private String author;       // the author of the post
    private Date timePosted;     // the time the post was up
    private boolean edited;      // whether the pose was editted


    public Post(String content, String author) {
        this.content = content;
        this.author = author; 
        this.timePosted = new Date();
        this.edited = false;
    }


    /**
     * print out the post on the screen
     */
    public void printPost() {
        System.out.println("\n");
        // print out the author 
        Util.print(author, Util.YELLOW_BOLD_BRIGHT);
        // print out the date
        Util.print("\t\t" + (edited ? "Edited" : "Posted") + " on ", Util.BLUE_BRIGHT);
        SimpleDateFormat fomatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Util.println(fomatter.format(timePosted), Util.BLUE_BRIGHT);
        // print the content of the post
        Util.println("\n" + content, null);
    }


    /**
     * edit the content of the post
     * 
     * @param newContent the new content after the edit
     */
    public void edit(String newContent) {
        content = newContent;
        timePosted = new Date();
        edited = true;
    }


    /**
     * get the name of the author
     * 
     * @return the author of the post
     */
    public String getAuthor() {
        return author;
    }


    /**
     * get the date and time that the user posted the post
     * 
     * @return the date and time that the post was up
     */
    public Date getTimePosted() {
        return timePosted;
    }
}
