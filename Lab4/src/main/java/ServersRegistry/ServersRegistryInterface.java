package ServersRegistry;

import Server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Server.ServerDesc;

public interface ServersRegistryInterface extends Remote {
    boolean register(ServerDesc serverDesc) throws RemoteException;
    List<ServerDesc> getServers() throws RemoteException;
}
