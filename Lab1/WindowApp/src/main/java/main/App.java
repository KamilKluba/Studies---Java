package main;

import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.KnapsackproblemWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javazaawansowana.lab1.Item;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private FXMLLoader loader;
    private Pane pane;
    private Scene scene;
    private ResourceBundle bundle;
    private Stage primaryStage;
    private ArrayList<Item> listOfItems;
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Locale.setDefault(new Locale("en"));
        loader = new FXMLLoader(this.getClass().getResource("/fxml/KnapsackProblemWindow.fxml"));
        bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);
        pane = loader.load();
        scene = new Scene(pane);

        reload();
        initializeList();

        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Problem plecakowy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void reload() {
        KnapsackproblemWindowController controller = loader.getController();
        controller.setApp(this);
    }

    private void initializeList(){
        listOfItems = new ArrayList<Item>();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(ArrayList<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }
}
