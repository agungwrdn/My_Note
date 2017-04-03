package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model;

/**
 * Created by root on 01/04/17.
 */

public class reminder {
    private String title;
    private String detail;
    private String date;
    private String time;

    public reminder(){

    }

    public reminder(String title, String content, String time, String date){
        this.title = title;
        this.detail = content;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String content) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
