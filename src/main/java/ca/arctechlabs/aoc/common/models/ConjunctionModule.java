package ca.arctechlabs.aoc.common.models;

import java.util.*;

public class ConjunctionModule extends Module{

    private Map<String, Pulse> inputMemory;
    public ConjunctionModule(String id, List<String> connections) {
        super(id, connections);
    }

    public void initConnectionMemory(List<String> inputs){
        this.inputMemory = new HashMap<>();
        for(String id : inputs){
            inputMemory.put(id, Pulse.LOW);
        }
    }

    @Override
    public Queue<Signal> receiveAndSend(String source, Pulse incomingPulse) {
        Pulse outgoingPulse = Pulse.HIGH;
        this.inputMemory.put(source, incomingPulse);
        if(this.inputMemory.values().stream().allMatch(Pulse.HIGH::equals)){
            outgoingPulse = Pulse.LOW;
        }
        return createSendQueue(outgoingPulse);
    }

    @Override
    public String toString() {
        return  this.getClass().getSimpleName() +"{" +
                "id='" + id + '\'' +
                ", connections=" + connections +
                ", inputs=" + inputMemory +
                "}\n";
    }
}
