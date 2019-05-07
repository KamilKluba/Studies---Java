package Controllers;

import App.Connect;
import Data.Animal;
import Data.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class WindowController {
    private Connect connect;

    private String idText;
    private String firstName;
    private String lastName;
    private String salaryText;
    private String someText;
    private int idInt;
    private int salaryInt;

    private String idAnimalText;
    private String name;
    private String ownerIDText;
    private int idAnimalInt;
    private int ownerIDInt;

    @FXML
    private TextField textFieldPersonID;
    @FXML
    private TextField textFieldPersonFirstName;
    @FXML
    private TextField textFieldPersonLastName;
    @FXML
    private TextField textFieldPersonSalary;
    @FXML
    private  TextField textFieldPersonSomeField;
    @FXML
    private TextField textFieldAnimalID;
    @FXML
    private TextField textFieldAnimalName;
    @FXML
    private TextField textFieldAnimalOwnerID;
    @FXML
    private Label labelError;
    @FXML
    private TableView tableViewPerson;
    @FXML
    private TableColumn<Person, Integer> tableColumnPersonID;
    @FXML
    private TableColumn<Person, String> tableColumnPersonFirstName;
    @FXML
    private TableColumn<Person, String> tableColumnPersonLastName;
    @FXML
    private TableColumn<Person, Integer> tableColumnPersonSalary;
    @FXML
    private TableColumn<Person, String> tableColumnPersonSomeText;
    @FXML
    private TableView tableViewAnimal;
    @FXML
    private TableColumn<Animal, Integer> tableColumnAnimalID;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimalName;
    @FXML
    private TableColumn<Animal, Integer> tableColumnAnimalOwnerID;



    public void initialize(){
        connect = new Connect();
        connect.dbConnect();

        tableColumnPersonID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPersonFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnPersonLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnPersonSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        tableColumnPersonSomeText.setCellValueFactory(new PropertyValueFactory<>("someText"));

        tableColumnAnimalID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnAnimalName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnAnimalOwnerID.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
    }

    public void actionInsertPerson() {
        labelError.setText("");
        if(!checkFieldsPerson()) return;

        if(!connect.dbCreatePerson(idInt, firstName, lastName, salaryInt, someText)) labelError.setText("Błąd!");
    }

    public void actionUpdatePerson() {
        labelError.setText("");
        if(!checkFieldsPerson()) return;

        if(!connect.dbUpdatePerson(idInt, firstName, lastName, salaryInt, someText)) labelError.setText("Błąd!");
    }

    public void actionFillTablePerson(){
        ArrayList<Person> arrayListPeople = connect.dbQueryPeople();
        ObservableList<Person> observableListPeople = FXCollections.observableArrayList(arrayListPeople);
        tableViewPerson.setItems(observableListPeople);
    }

    public void actionInsertAnimal() {
        labelError.setText("");
        if(!checkFieldsAnimal()) return;

        if(!connect.dbCreateAnimal(idAnimalInt, name, ownerIDInt)) labelError.setText("Błąd!");
    }

    public void actionUpdateAnimal(){
        labelError.setText("");
        if(!checkFieldsAnimal()) return;

        if(!connect.dbUpdateAnimal(idAnimalInt, name, ownerIDInt)) labelError.setText("Błąd!");
    }

    public void actionFillTableAnimal(){
        ArrayList<Animal> arrayListAnimals = connect.dbQueryAnimals();
        ObservableList<Animal> observableListAnimals = FXCollections.observableArrayList(arrayListAnimals);
        tableViewAnimal.setItems(observableListAnimals);
    }

    private boolean checkFieldsPerson(){
        idText = textFieldPersonID.getText();
        firstName = textFieldPersonFirstName.getText();
        lastName = textFieldPersonLastName.getText();
        salaryText = textFieldPersonSalary.getText();
        someText = textFieldPersonSomeField.getText();

        if(idText.equals("") || firstName.equals("") || lastName.equals("") || salaryText.equals("") || someText.equals("")) {
            labelError.setText("Błąd!");
            return false;
        }
        try{
            idInt = Integer.parseInt(idText);
            salaryInt = Integer.parseInt(salaryText);
        } catch(Exception e){
            labelError.setText("Błąd!");
            return false;
        }
        return true;
    }

    private boolean checkFieldsAnimal(){
        idAnimalText = textFieldAnimalID.getText();
        name = textFieldAnimalName.getText();
        ownerIDText = textFieldAnimalOwnerID.getText();

        if(idAnimalText.equals("") || name.equals("") || ownerIDText.equals("")) {
            labelError.setText("Błąd!");
            return false;
        }
        try{
            idAnimalInt = Integer.parseInt(idAnimalText);
            ownerIDInt = Integer.parseInt(ownerIDText);
        } catch(Exception e){
            labelError.setText("Błąd!");
            return false;
        }
        return true;
    }

}
