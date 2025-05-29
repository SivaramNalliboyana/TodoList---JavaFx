package Controller;

import Model.DatabaseHelper;
import Model.ToDo;
import Model.ToDoDao;
import View.ToDoView;

import java.util.List;

public class Controller {
    private final ToDoDao toDoDao;
    private ToDoView toDoView;

    public Controller(DatabaseHelper databaseHelper){
        this.toDoDao = new ToDoDao(databaseHelper);
    }

    public void setToDoView(ToDoView toDoView) {
        this.toDoView = toDoView;
    }

    public void saveToDo(ToDo toDo){
        ToDo savedToDo;
        int id = this.toDoDao.saveTask(toDo);
        savedToDo = new ToDo(id, toDo.getTask(), toDo.getCategory());

        if (toDoView != null){
            this.toDoView.addToDo(savedToDo);
        }

        savedToDo.notifyObservers();

    }

    public List<ToDo> getTodos(){
        return this.toDoDao.getToDos();
    }
}
