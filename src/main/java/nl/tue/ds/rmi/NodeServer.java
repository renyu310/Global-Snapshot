package nl.tue.ds.rmi;

import nl.tue.ds.entity.Node;
import org.jetbrains.annotations.NotNull;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface to be used by SERVER for accessing the remote node via RMI
 */
public interface NodeServer extends Remote {

    @NotNull Node getNode() throws RemoteException;

    void addNode(int id, @NotNull String host) throws RemoteException;

    boolean transferMoney(int senderNodeId, int amount) throws RemoteException;

    boolean withdrawMoney(int amount) throws RemoteException;

    void restoreMoney() throws RemoteException;

    void sendMarker(int nodeId) throws RemoteException;
}
