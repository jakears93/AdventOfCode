package ca.arctechlabs.aoc.common.models;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public abstract class Module {

    protected final String id;
    protected final List<String> connections;


    public Module(String id, List<String> connections) {
        this.id = id;
        this.connections = connections;
    }

    public abstract Queue<Signal> receiveAndSend(String source, Pulse incomingPulse);

    protected Queue<Signal> createSendQueue(Pulse pulse){
        Queue<Signal> out = new ArrayDeque<>();
        for(String connectionId : this.connections){
            out.add(new Signal(this.id,connectionId, pulse));
        }
        return out;
    }

    public String getId() {
        return id;
    }

    public List<String> getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        return  this.getClass().getSimpleName() +"{" +
                "id='" + id + '\'' +
                ", connections=" + connections +
                "}\n";
    }
}
