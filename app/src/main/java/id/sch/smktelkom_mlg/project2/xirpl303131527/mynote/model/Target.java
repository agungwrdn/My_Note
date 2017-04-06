package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.model;


public class Target {
    private String title;
    private String due;

    public Target(){
        //contructor
    }

    public Target(String title, String due) {
        this.title = title;
        this.due = due;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
