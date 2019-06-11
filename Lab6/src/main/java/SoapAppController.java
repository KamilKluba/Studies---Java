
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.soap.SOAPException;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;;

public class SoapAppController implements Initializable {

    private SoapApp node;

    private ObservableList<String> logs = FXCollections.observableArrayList();

    @FXML
    Button buttonSendMessage;
    @FXML
    Label labelMessageSender;
    @FXML
    TextField textFieldMessage;
    @FXML
    TextField textFieldTargetNode;
    @FXML
    Label labelCurrentNodePort;
    @FXML
    Label labelCurrentNodeId;
    @FXML
    ListView<String> listViewLog;
    @FXML
    Label labelReceivedMessage;
    @FXML
    BorderPane paneReceivedMessage;
    @FXML
    Label labelNextNodePort;
    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewLog.setItems(logs);

        logs.addListener(new ListChangeListener<String>() {

            @Override
            public void onChanged(Change<? extends String> c) {
                listViewLog.scrollTo(listViewLog.getItems().size() - 1);
            }
        });
    }

    public void setNode(SoapApp node) {
        this.node = node;

        labelCurrentNodeId.setText(node.getNodeIdentifier());
        labelCurrentNodePort.setText(Integer.toString(node.getPort()));
    }

    @FXML
    public void sendMessage() {
        try {
            node.sendMessage(textFieldTargetNode.getText(), textFieldMessage.getText());

        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
    }

    public void showReceivedMessage(String sender, String message) {

        Platform.runLater(() -> {
            labelMessageSender.setText(sender);
            labelReceivedMessage.setText(message);

            signalReceivingMessage();
        });

    }

    private void signalReceivingMessage() {

            new Thread(() -> {
                Platform.runLater(() -> pane.setStyle("-fx-background-color: yellow"));
                try {
                    Thread.sleep(500);
                } catch(Exception e){
                    e.printStackTrace();
                }
                Platform.runLater(() -> pane.setStyle("-fx-background-color: white"));
            }).start();
    }

    public void showError(String title, String text) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(text);

        alert.show();
    }

    public void log(String message) {
        Platform.runLater(() -> {
            DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());
            String time = df.format(new Date());
            this.logs.add(time + ": " + message);
        });
    }

    public void setSimpleNode(SoapApp simpleNode) {
        this.setNode(simpleNode);

        labelNextNodePort.setText(Integer.toString(simpleNode.getNextNodePort()));
    }

}
