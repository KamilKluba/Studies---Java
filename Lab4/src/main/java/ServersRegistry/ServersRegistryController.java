package ServersRegistry;

import Server.ServerDesc;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


public class ServersRegistryController {
    private ServersRegistry serverRegistry;

    @FXML
    private TextArea textAreaServers;
    @FXML
    private Button buttonRefresh;

    public void initialize(){
    }

    public void setServerRegistry(ServersRegistry serverRegistry) {
        this.serverRegistry = serverRegistry;
    }

    public TextArea getTextAreaServers() {
        return textAreaServers;
    }

    public void actionRefresh(){
        textAreaServers.clear();
        for(ServerDesc sd : serverRegistry.getServers())
            textAreaServers.setText(textAreaServers.getText() + "\n" + sd.getName() + " " + sd.getDescription());
    }
}
