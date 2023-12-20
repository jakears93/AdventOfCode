package ca.arctechlabs.aoc.common.models;

import java.util.List;
import java.util.Queue;

public class BroadcastModule extends Module{
    public BroadcastModule(String id, List<String> connections) {
        super(id, connections);
    }

    @Override
    public Queue<Signal> receiveAndSend(String source, Pulse incomingPulse) {
        return createSendQueue(incomingPulse);
    }
}
