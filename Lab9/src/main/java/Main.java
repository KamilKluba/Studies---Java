import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Main extends Application {
    private ScriptEngine scriptEngine;
    private TicTacToeController controller;

    public static void main(String args[]){
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicTacToe.fxml"));

        Pane pane = loader.load();
        controller = loader.getController();

        controller.setMain(this);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Najlepsze kółko i krzyżyk na świecie!");
        primaryStage.show();
    }

    public int moveAIJS(char[] board, char player){
        double move = -1;
        try {
            scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
            scriptEngine.eval(new FileReader("D:\\Programy\\IntelliJ IDEA 2019.1\\Workspace\\Javazaawansowana\\Lab9\\src\\main\\engines\\Engine9000.js"));
            Invocable invocable = (Invocable)scriptEngine;

            move = (double)invocable.invokeFunction("makeNextMove", board, player);
        }catch(Exception e){
            e.printStackTrace();
        }
        return (int)move;
    }

    public native int moveAIC(char[] board, char player);

    public int halo(){
        return 1;
    }
}
