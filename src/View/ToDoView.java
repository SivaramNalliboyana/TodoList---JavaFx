package View;

import Controller.Controller;
import Model.ToDo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;


public class ToDoView extends VBox implements Observer {
    protected final ObservableList<ToDo> todos;
    protected final Controller controller;


    public ToDoView(Controller controller, List<ToDo> todoList){
        this.controller = controller;
        this.todos  = FXCollections.observableArrayList(todoList);
        for (ToDo toDo : todos){
            toDo.addObserver(this);
        }
        generateUserInterface();
    }

    public void generateUserInterface(){
        TableView<ToDo> table = new TableView<>();
        table.setEditable(true);

        TableColumn<ToDo, String> nameColumn = new TableColumn<>("Task");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("task"));

        TableColumn<ToDo, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        table.getColumns().addAll(nameColumn, categoryColumn);
        table.setItems(todos);

        Button addButton = new Button("Add Todo");
        addButton.setOnAction(e -> createTodo());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table, addButton);
        this.getChildren().addAll(layout);
    }

    public void createTodo(){
        Dialog<ToDo> dialog = new Dialog<>();
        dialog.setTitle("Add New Todo");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Task Name");
        TextArea categoryField = new TextArea();
        categoryField.setPromptText("Task category");

        grid.add(new Label("Task:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Category:"), 0, 1);
        grid.add(categoryField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = nameField.getText();
                    String category = categoryField.getText();

                    return new ToDo(name, category);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<ToDo> result = dialog.showAndWait();
        result.ifPresent(controller::saveToDo);
    }

    public void addToDo(ToDo toDo){
        todos.add(toDo);
        toDo.addObserver(this);
    }

    @Override
    public void update() {
        List<ToDo> newProducts = controller.getTodos();
        todos.clear();
        todos.addAll(newProducts);
    }
}
