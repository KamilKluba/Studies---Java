package ServersRegistry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistryMain extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServerRegistry.fxml"));
        Pane pane = loader.load();

        ServersRegistry serversRegistry = new ServersRegistry();
        ServersRegistryController serversRegistryController= (ServersRegistryController)loader.getController();
        serversRegistryController.setServerRegistry(serversRegistry);
        serversRegistry.setTextArea(serversRegistryController.getTextAreaServers());

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
