package Client;

import Algorithms.Item;
import Server.ServerDesc;
import Server.ServerInterface;
import ServersRegistry.ServersRegistryInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

public class ClientController {
    private Client client;
    private ArrayList<Item> listOfItems = new ArrayList<Item>();
    private Registry serverRegistry;
    private ServerInterface serverInterface;

    @FXML
    TextArea textAreaResult;
    @FXML
    private ComboBox<ServerDesc> comboBoxServers;

    public void initialize() throws RemoteException, NotBoundException {
        setClient(new Client());
        client.setClientController(this);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TextArea getTextAreaResult() {
        return textAreaResult;
    }

    public ComboBox<ServerDesc> getComboBoxServers() {
        return comboBoxServers;
    }

    public void actionSolve() throws RemoteException, NotBoundException {
        textAreaResult.clear();
        String serverName = comboBoxServers.getSelectionModel().getSelectedItem().getName();
        int port = comboBoxServers.getSelectionModel().getSelectedItem().getPort();
        serverRegistry = LocateRegistry.getRegistry(port);
        serverInterface = (ServerInterface) serverRegistry.lookup(serverName);
        textAreaResult.setText(serverInterface.solve(listOfItems, 1000));
    }

    public void actionGenerateRandom(){
        Random random = new Random();
        listOfItems.clear();
        textAreaResult.clear();
        for (int i = listOfItems.size(); i < 32; i++)
            if (listOfItems.size() < 32) {
                Item item = new Item("item" + (listOfItems.size() + 1), Math.abs(random.nextInt() % 1000), random.nextDouble() * 1000 + random.nextDouble());
                listOfItems.add(item);
                textAreaResult.setText(textAreaResult.getText() + "\n" + item.getName() + " " + item.getWeight() + " " + item.getItemValue());
            }
    }
}
