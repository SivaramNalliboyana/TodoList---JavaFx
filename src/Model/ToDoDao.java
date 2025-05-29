package Model;

import java.util.List;

public class ToDoDao {
    private final DatabaseHelper databaseHelper;

    public ToDoDao(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public int saveTask(ToDo toDo){
       return this.databaseHelper.insertToDo(toDo);
    }

    public List<ToDo> getToDos(){
        return this.databaseHelper.selectAllToDos();
    }
}
