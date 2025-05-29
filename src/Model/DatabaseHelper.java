package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper{
    private final String SQLItE_CONNECTION;
    private final String TODOS_TABLE = "todos";
    private final Connection conn;

    public DatabaseHelper(String path){
        this.SQLItE_CONNECTION = path;
        this.conn = connect();
    }

    public synchronized Connection connect() {
        try {
            return DriverManager.getConnection(SQLItE_CONNECTION);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e);
        }
    }

    public synchronized void initializeDatabase() {
        try (Statement statement = conn.createStatement()) {

            String createProducts = "CREATE TABLE IF NOT EXISTS " + TODOS_TABLE + " (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	task text NOT NULL,\n"
                    + "	category text NOT NULL\n"  // ← No comma here
                    + ");";

            statement.addBatch(createProducts);

            statement.executeBatch();

        } catch (SQLException e) {
            throw  new RuntimeException("Error initializing the database", e);
        }
    }

    public synchronized void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }

    public synchronized int insertToDo(ToDo toDo){
        String sql = "INSERT INTO " + TODOS_TABLE + "(task,category) VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, toDo.getTask());
            preparedStatement.setString(2, toDo.getCategory());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public List<ToDo> selectAllToDos() {
        String sql = "SELECT * FROM " + TODOS_TABLE;

        try {
            Statement statement = conn.createStatement();
            return parseResultToToDo(statement.executeQuery(sql));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<ToDo> parseResultToToDo(ResultSet resultSet) {
        List<ToDo> products = new ArrayList<>();
        try {
            while (resultSet.next()) {
                products.add(new ToDo(resultSet.getInt("id"), resultSet.getString("task"),
                        resultSet.getString("category")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return List.of();
        }

        return products;
    }

}
