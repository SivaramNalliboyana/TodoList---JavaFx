import Controller.Controller;
import Model.DatabaseHelper;
import Model.ToDo;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class ToDoMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseHelper databaseHelper = new DatabaseHelper("jdbc:sqlite:todo.db");
        databaseHelper.initializeDatabase();
        Controller controller = new Controller(databaseHelper);

        List<ToDo> todosList = controller.getTodos();

        if (todosList.isEmpty()){
            todosList.add(new ToDo("Shopping", "Outside"));
        }

        MainView mainView = new MainView(controller, todosList);
        mainView.show();

        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            databaseHelper.closeConnection();
            System.out.println("DB CLOSED");
        }));
    }

    public static void main(String[] args) {
        launch();
    }
}
