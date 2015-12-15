package nl.tue.ds.entity;

import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Distributed Snapshot associated with node
 *
 * @see Node
 */
public final class Snapshot implements Serializable {

    /**
     * Sequential number, current snapshot ID to be taken
     */
    private int id;

    /**
     * Current amount of money at the bank
     */
    private int localBalance;

    /**
     * All money transfers from incoming channels upon receiving the marker
     */
    private int moneyInTransfer;

    /**
     * Incoming nodes to be recorded for distributed snapshot
     * holds only node ids from where the marker has not arrived yet
     * if collection is empty -> all markers are received
     * <p>
     * Map<NodeId>
     */
    private final @NotNull Set<Integer> unrecordedNodes = new HashSet<>();

    public void startSnapshotRecording(int balance, Map<Integer, String> nodes) {
        id++;
        localBalance = balance;
        moneyInTransfer = 0;
        unrecordedNodes.addAll(nodes.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
    }

    public int getId() {
        return id;
    }

    public int getLocalBalance() {
        return localBalance;
    }

    public int getMoneyInTransfer() {
        return moneyInTransfer;
    }

    public void incrementMoneyInTransfer(int nodeId, int amount) {
        if (unrecordedNodes.contains(nodeId)) {
            moneyInTransfer += amount;
        }
    }

    public void markRecorded(int nodeId) {
        unrecordedNodes.remove(nodeId);
    }

    public boolean isRecording() {
        return unrecordedNodes.size() != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (o instanceof Snapshot) {
            Snapshot object = (Snapshot) o;

            return Objects.equals(id, object.id) &&
                    Objects.equals(localBalance, object.localBalance) &&
                    Objects.equals(moneyInTransfer, object.moneyInTransfer);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localBalance, moneyInTransfer);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("localBalance", localBalance)
                .add("moneyInTransfer", moneyInTransfer)
                .add("unrecordedNodes", Arrays.toString(unrecordedNodes.toArray()))
                .toString();
    }
}