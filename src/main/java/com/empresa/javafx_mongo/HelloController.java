package com.empresa.javafx_mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    protected void onAddUserButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.uay2677.mongodb.net/");
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("usuario");

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        Document newUser = new Document("name", name)
                .append("email", email)
                .append("password", password);

        collection.insertOne(newUser);

        // Realizar una consulta find y mostrar los resultados
        ObservableList<User> users = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            users.add(new User(
                    doc.getString("name"),
                    doc.getString("email"),
                    doc.getString("password")
            ));
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        userTable.setItems(users);

        // Limpiar los campos de texto
        nameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}