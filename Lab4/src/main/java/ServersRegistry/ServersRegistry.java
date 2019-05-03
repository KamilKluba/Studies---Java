package ServersRegistry;

import Server.ServerDesc;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServersRegistry extends UnicastRemoteObject implements ServersRegistryInterface {
    private Registry registry;
    private ArrayList<ServerDesc> servers = new ArrayList<ServerDesc>();
    private TextArea textArea;

    public ServersRegistry() throws RemoteException {
        super();
        registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ListOfServers", this);
    }

    public boolean register(ServerDesc serverDesc) {
        boolean success = servers.add(serverDesc);
        textArea.clear();
        for(ServerDesc sd : servers)
            textArea.setText(textArea.getText() + "\n" + sd.getName() + " "+ sd.getDescription());

        return success;
    }

    public List<ServerDesc> getServers() {
        return servers;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
}
