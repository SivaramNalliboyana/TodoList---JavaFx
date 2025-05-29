package View;
import Controller.Controller;
import Model.ToDo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;


public class MainView extends Stage{
   public MainView(Controller controller, List<ToDo> toDos){
       ToDoView myView = new ToDoView(controller, toDos);
       Scene scene = new Scene(myView, 600, 400);
       controller.setToDoView(myView);
       setTitle("TO DO LIST");
       setScene(scene);
   }
}
