package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model;

/**
 * Created by root on 01/04/17.
 */

public class memo {
    private String title;
    private String content;
    private String time;
    private String date;
    public memo(){

    }

    public memo(String title, String content, String time, String date){
        this.title = title;
        this.content = content;
        this.time = time;
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(){
        this.time = time;
    }

    public String getDate(){
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

