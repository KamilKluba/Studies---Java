package Client;

import Server.ServerDesc;
import ServersRegistry.ServersRegistryInterface;
import javafx.scene.control.TextArea;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Registry listOfServersRegistry;
    private Registry serverRegistry;
    private ClientController clientController;
    private ServersRegistryInterface serversRegistryInterface;
    private List<ServerDesc> list;

    public Client() throws RemoteException, NotBoundException {
        listOfServersRegistry = LocateRegistry.getRegistry(1099);
        serversRegistryInterface = (ServersRegistryInterface) listOfServersRegistry.lookup("ListOfServers");
        list = serversRegistryInterface.getServers();
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;

        for(ServerDesc sd : list)
            clientController.getComboBoxServers().getItems().add(sd);
    }
}
