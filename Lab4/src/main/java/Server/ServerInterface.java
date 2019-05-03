package Server;

import Algorithms.Item;
import Algorithms.Knapsack;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
    String solve(ArrayList<Item> listOfItems, int capacity) throws RemoteException;
}
