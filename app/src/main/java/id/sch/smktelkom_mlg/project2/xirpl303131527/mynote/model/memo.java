package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model;


public class memo {
    private String content;
    private String time;

    public memo() {
        //empty constructor
    }

    public memo(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
