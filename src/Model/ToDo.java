package Model;


public class ToDo extends Observable {
    private int id;
    private String task;
    private String category;

    public ToDo(int id , String task, String category){
        this.id = id;
        this.task = task;
        this.category = category;
    }

    public ToDo(String task, String category){
        this.id = -1;
        this.task = task;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
