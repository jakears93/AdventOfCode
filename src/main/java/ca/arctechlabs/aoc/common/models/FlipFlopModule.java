package ca.arctechlabs.aoc.common.models;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class FlipFlopModule extends Module{
    private State state;

    public FlipFlopModule(String id, List<String> connections) {
        super(id, connections);
        this.state = State.OFF;
    }

    @Override
    public Queue<Signal> receiveAndSend(String source, Pulse incomingPulse) {
        if(Pulse.LOW.equals(incomingPulse)){
            Pulse outgoingPulse;
            if(State.OFF.equals(this.state)){
                this.state = State.ON;
                outgoingPulse = Pulse.HIGH;
            }
            else{
                this.state = State.OFF;
                outgoingPulse = Pulse.LOW;
            }
            return createSendQueue(outgoingPulse);
        }
        return new ArrayDeque<>();
    }
}
