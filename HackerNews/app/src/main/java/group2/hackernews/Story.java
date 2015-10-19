package group2.hackernews;

/**
 * Created by Zovin on 10/7/2015.
 * These are the properties of the acquired stories
 */
public class Story {
    private String title;
    private String by;
    private String score;
    private String uri;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBy(){
        return by;
    }

    public void setBy(String by){
        this.by = by;
    }

    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score = score;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }
}
