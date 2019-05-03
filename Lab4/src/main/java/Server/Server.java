package Server;

import Algorithms.GreedyAlgorithm;
import Algorithms.Item;
import Algorithms.Knapsack;
import Algorithms.RandomAlgorithm;
import ServersRegistry.ServersRegistryInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class Server extends UnicastRemoteObject implements ServerInterface, Serializable{
    private static final long serialVersionUID = 2L;
    private Registry registry;
    private Registry serversListRegistry;
    private ServersRegistryInterface serversRegistryInterface;
    private ServerDesc serverDesc;
    private int port = 1100;

    public Server() throws RemoteException {
        super();
        try{
            boolean finished = false;

            while(port < 1300) {
                try {
                    System.out.println("halo" + port);
                    registry = LocateRegistry.createRegistry(port);
                    registry.rebind("Server" + (port - 1099), this);

                    String algorithmDesc;
                    if(port % 2 == 0)
                        algorithmDesc = "Algorytm Zachlanny";
                    else
                        algorithmDesc = "Algorytm Losowy";

                    serverDesc = new ServerDesc("Server" + (port - 1099), port, algorithmDesc);
                    finished = true;
                    break;
                }catch(Exception e){
                    port++;
                }
            }

            if(!finished)
                System.exit(0);

            serversListRegistry = LocateRegistry.getRegistry(1099);
            serversRegistryInterface = (ServersRegistryInterface) serversListRegistry.lookup("ListOfServers");
            serversRegistryInterface.register(serverDesc);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public String solve(ArrayList<Item> listOfItems, int capacity) {
        if(port % 2 == 0) {
            GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(listOfItems, capacity);
            greedyAlgorithm.solve();
            return greedyAlgorithm.description();
        }
        else{
            RandomAlgorithm randomAlgorithm = new RandomAlgorithm(listOfItems, capacity);
            randomAlgorithm.solve();
            return randomAlgorithm.description();
        }
    }

    public static void main(String args[]) throws RemoteException {
        new Server();

    }
}
