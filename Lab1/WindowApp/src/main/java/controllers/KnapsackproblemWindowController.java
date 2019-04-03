package controllers;

import java.nio.file.FileSystemNotFoundException;
import java.text.*;
import java.util.*;

import com.sun.jnlp.ApiDialog;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javazaawansowana.lab1.Item;
import javazaawansowana.lab1.KnapsackProblem;
import main.App;

public class KnapsackproblemWindowController {
    @FXML
    private Pane pane;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> tableColumnItemsName;
    @FXML
    private TableColumn<Item, String> tableColumnItemsWeight;
    @FXML
    private TableColumn<Item, String> tableColumnItemsValue;
    @FXML
    private ComboBox<String> comboBoxAlgorithm;
    @FXML
    private TextField textFieldItemName;
    @FXML
    private TextField textFieldItemWeight;
    @FXML
    private TextField textFieldItemValue;
    @FXML
    private Button buttonAddItem;
    @FXML
    private Button buttonStart;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelError;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem menuItemStart;
    @FXML
    private MenuItem menuItemGreedy;
    @FXML
    private MenuItem menuItemRandom;
    @FXML
    private MenuItem menuItemBruteForce;
    @FXML
    private MenuItem menuItemAbout;
    @FXML
    private MenuItem menuItemLanguagePL;
    @FXML
    private MenuItem menuItemLanguageENGB;
    @FXML
    private MenuItem menuItemLanguageENUSA;
    @FXML
    private TextArea textAreaResult;
    @FXML
    private TextField textFieldCapacity;
    @FXML
    private TextField textFieldRepetitions;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelItemsAmount;
    @FXML
    private Slider slider;

    private ArrayList<Item> listOfItems;
    private ObservableList<Item> observableListOfItems;
    private ListProperty<Item> listProperty;
    private ObservableList<String> algorithmList;
    private KnapsackProblem kp;
    private App app;
    private ResourceBundle rb;

    @FXML
    void initialize() {
        listOfItems = new ArrayList<Item>();
//        listOfItems.add(new Item("Diament", 1, 1000.5));
//        listOfItems.add(new Item("Sztabka złota", 100, 500));
//        listOfItems.add(new Item("Cegła", 150, 2.5));
//        listOfItems.add(new Item("Kamień", 100, 1.1));
//        listOfItems.add(new Item("Marmur", 100, 50));
//        listOfItems.add(new Item("Przedmiot0", 100, 30));
//        listOfItems.add(new Item("Przedmiot1", 70, 300));
//        listOfItems.add(new Item("Przedmiot2", 40, 70));
//        listOfItems.add(new Item("Przedmiot3", 85, 200));
//        listOfItems.add(new Item("Przedmiot4", 120, 400));
//        listOfItems.add(new Item("Przedmiot5", 50, 430));
//        listOfItems.add(new Item("Przedmiot6", 300, 300));
        rb = ResourceBundle.getBundle("bundles.messages");

        listProperty = new SimpleListProperty<>();
        observableListOfItems = FXCollections.observableArrayList(listOfItems);
        listProperty.set(observableListOfItems);
        tableView.setItems(observableListOfItems);
        tableView.itemsProperty().bindBidirectional(listProperty);

        tableColumnItemsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnItemsWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tableColumnItemsValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                double value = param.getValue().getItemValue();
                DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.getDefault()));
                return new SimpleStringProperty(df.format(value));
            }
        });

        algorithmList = FXCollections.observableArrayList(KnapsackProblem.getListOfAlgorithms());
        comboBoxAlgorithm.setItems(algorithmList);
        comboBoxAlgorithm.getSelectionModel().select(0);

        showDate();
        showLabel();
    }

    @FXML
    public void actionButtonAddItem() {
        if (listOfItems.size() < 32)
            try {
                observableListOfItems.add(new Item(textFieldItemName.getText(),
                        Integer.parseInt(textFieldItemWeight.getText()), Double.parseDouble(textFieldItemValue.getText())));
                listOfItems.add(new Item(textFieldItemName.getText(),
                        Integer.parseInt(textFieldItemWeight.getText()), Double.parseDouble(textFieldItemValue.getText())));
                showLabel();
            } catch (Exception e) {
                showError();
            }
        else
            showError();
        textFieldItemName.clear();
        textFieldItemWeight.clear();
        textFieldItemValue.clear();
    }

    @FXML
    public void actionClose() {
        System.exit(0);
    }

    @FXML
    public void actionStart() {
        try {
            int capacity = Integer.parseInt(textFieldCapacity.getText());
            int repetitions = 0;

            if (comboBoxAlgorithm.getSelectionModel().getSelectedIndex() == 1)
                repetitions = Integer.parseInt(textFieldRepetitions.getText());

            kp = new KnapsackProblem(capacity, listOfItems);
            kp.chooseAlgorithmToRun(comboBoxAlgorithm.getSelectionModel().getSelectedItem(), repetitions);
            textAreaResult.setText(kp.getResult());
        } catch (Exception e) {
            showError();
        }
    }

    @FXML
    public void actionLanguage(ActionEvent event) {
        try {
            String source = event.getSource().toString();

            if (source.equals("MenuItem[id=menuItemLanguagePL, styleClass=[menu-item]]"))
                Locale.setDefault(new Locale("pl"));
            else if (source.equals("MenuItem[id=menuItemLanguageENGB, styleClass=[menu-item]]"))
                Locale.setDefault(Locale.UK);
            else
                Locale.setDefault(Locale.US);

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/KnapsackProblemWindow.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
            loader.setResources(bundle);
            Pane pane = loader.load();
            Scene scene = new Scene(pane);
            app.getPrimaryStage().setScene(scene);
            app.setLoader(loader);
            app.setPane(pane);
            app.setScene(scene);
            app.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void actionAlgorithm(ActionEvent event) {
        String source = event.getSource().toString();
        if (source.equals("MenuItem[id=menuItemGreedy, styleClass=[menu-item]]"))
            comboBoxAlgorithm.getSelectionModel().select(0);
        else if (source.equals("MenuItem[id=menuItemRandom, styleClass=[menu-item]]"))
            comboBoxAlgorithm.getSelectionModel().select(1);
        else
            comboBoxAlgorithm.getSelectionModel().select(2);
    }

    @FXML
    public void actionAbout() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setContentText(rb.getString("aboutProgram"));
       alert.show();
    }

    public void actionGenerate() {
        int itemsQuantity = (int) slider.getValue();
        Random random = new Random();

        for (int i = listOfItems.size(); i < itemsQuantity; i++)
            if (listOfItems.size() < 32) {
                Item item = new Item(rb.getString("item") + (listOfItems.size() + 1), Math.abs(random.nextInt() % 1000), random.nextDouble() * 1000 + random.nextDouble());
                listOfItems.add(item);
                observableListOfItems.add(item);
            }
        showLabel();
    }

    public void actionRemoveAll(){
        listOfItems.clear();
        observableListOfItems.clear();
        showLabel();
    }

    private void showError() {
        final StringProperty sp = new SimpleStringProperty();
        labelError.textProperty().bindBidirectional(sp);
        new Thread(new Task<Void>() {
            @Override
            public Void call() {
                try {
                    Platform.runLater(() -> sp.setValue("Błąd!"));
                    Thread.sleep(3000);
                    Platform.runLater(() -> sp.setValue(""));
                } catch (Exception e) {
                }
                return null;
            }

            ;
        }).start();
    }

    private void showDate() {
        final StringProperty sp = new SimpleStringProperty();
        labelDate.textProperty().bindBidirectional(sp);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());

        new Thread(new Task<Void>() {
            @Override
            public Void call() {
                try {
                    while (true) {
                        Platform.runLater(() -> sp.setValue(df.format(new Date())));
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                }
                return null;
            }

            ;
        }).start();
    }

    public void setApp(App app) {
        this.app = app;
    }


    private void showLabel() {
        MessageFormat mf = new MessageFormat("");
        mf.setLocale(Locale.getDefault());

        double[] fileLimits = {0, 1, 2, 3, 4, 5};
        String[] fileStrings = {
                rb.getString("noItems"),
                rb.getString("oneItem"),
                rb.getString("2-4items"),
                rb.getString("2-4items"),
                rb.getString("2-4items"),
                rb.getString("multipleItems")
        };

        ChoiceFormat cf = new ChoiceFormat(fileLimits, fileStrings);

        String pattern = rb.getString("pattern");
        mf.applyPattern(pattern);

        Format[] f = {cf, null, NumberFormat.getInstance()};
        mf.setFormats(f);

        Object[] messageArguments = {null, "list", null};

        messageArguments[0] = listOfItems.size();
        messageArguments[2] = listOfItems.size();

        labelItemsAmount.setText(mf.format(messageArguments));

    }

}
