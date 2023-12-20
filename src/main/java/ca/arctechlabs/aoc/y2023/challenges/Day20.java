package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.models.*;
import ca.arctechlabs.aoc.common.utilities.Utils;

import java.util.*;

public class Day20 {

    public long productOfDifferentSignals(List<String> input, int cycles){
        PulseCounts pulseCounts = countPulses(parseModules(input), cycles);
        return pulseCounts.high * pulseCounts.low;
    }

    public long buttonPressesUntilGoal(List<String> input, String goalId, String goalPulse){
        Map<String, Module> moduleMap = parseModules(input);
        Module source = null;
        for(Module module : moduleMap.values()){
            if(module.getConnections().contains(goalId)){
                source = moduleMap.get(module.getId());
                break;
            }
        }
        assert source != null;
        Signal originalGoal = new Signal(null, source.getId(), Pulse.HIGH);

        List<Signal> allGoals = getAllGoals(moduleMap.get(originalGoal.destination()));
        List<Long> buttonPressesUntilGoals = new ArrayList<>();
        for(Signal goal : allGoals){
            moduleMap = parseModules(input);
            buttonPressesUntilGoals.add(findButtonPressesUntilGoal(moduleMap, goal));
        }
        return Utils.calculateLCM(buttonPressesUntilGoals);

    }

    private List<Signal> getAllGoals(Module origin){
        List<Signal> goals = new ArrayList<>();
        if(origin instanceof ConjunctionModule){
            goals.add(new Signal("any", origin.getId(), Pulse.LOW));
            List<Module> inputs = ((ConjunctionModule) origin).getInputs();
            if(inputs.stream().allMatch(m-> m instanceof FlipFlopModule) || inputs.size()>1){
                goals.remove(new Signal("any", origin.getId(), Pulse.LOW));
            }
            for(Module module : inputs){
                goals.addAll(getAllGoals(module));
            }
        }
        return goals;
    }

    private long findButtonPressesUntilGoal(Map<String, Module> moduleMap, Signal goal){
        long buttonPresses = 0L;
        boolean foundGoal = false;
        Queue<Signal> signalQueue = new ArrayDeque<>();
        while(!foundGoal){
            buttonPresses++;
            signalQueue.addAll(moduleMap.get("broadcaster").receiveAndSend("button", Pulse.LOW));
            while(!signalQueue.isEmpty()){
                Signal signal = signalQueue.poll();
                //Found goal, finish queue then return button presses
                if(signal.destination().equals(goal.destination()) && signal.pulse().equals(goal.pulse())){
                    foundGoal = true;
                }
                signalQueue.addAll(moduleMap.getOrDefault(signal.destination(), new BroadcastModule("temp", new ArrayList<>())).receiveAndSend(signal.source(), signal.pulse()));
            }
        }
        return buttonPresses;
    }

    private PulseCounts countPulses(Map<String, Module> moduleMap, int cycles){
        PulseCounts pulseCounts = new PulseCounts();
        Queue<Signal> signalQueue = new ArrayDeque<>();
        for(int i=0; i<cycles; i++){
            signalQueue.addAll(moduleMap.get("broadcaster").receiveAndSend("button", Pulse.LOW));
            while(!signalQueue.isEmpty()){
                Signal signal = signalQueue.poll();
                if(Pulse.HIGH.equals(signal.pulse())) pulseCounts.high++;
                else pulseCounts.low++;
                signalQueue.addAll(moduleMap.getOrDefault(signal.destination(), new BroadcastModule("temp", new ArrayList<>())).receiveAndSend(signal.source(), signal.pulse()));
            }
        }
        pulseCounts.low += cycles;
        return pulseCounts;
    }
    
    private Map<String, Module> parseModules(List<String> input){
        Map<String, Module> moduleMap = new HashMap<>();
        for(String line : input){
            List<String> connections = new ArrayList<>();
            for(String con : line.split(">")[1].split(",")){
                connections.add(con.trim());
            }
            
            Module module;
            String id;
            if(line.startsWith("%")){
                id = line.substring(1, line.indexOf(" "));
                module = new FlipFlopModule(id, connections);
            }
            else if(line.startsWith("&")){
                id = line.substring(1, line.indexOf(" "));
                module = new ConjunctionModule(id, connections);
            }
            else{
                id = "broadcaster";
                module = new BroadcastModule(id, connections);
            }
            moduleMap.put(id, module);
        }
        for(Module module : moduleMap.values()){
            if(module instanceof ConjunctionModule){
                List<Module> inputs = moduleMap.values().stream().filter(m -> m.getConnections().contains(module.getId())).toList();
                ((ConjunctionModule) module).initConnectionMemory(inputs);
            }
        }

        return moduleMap;
    }

    private static class PulseCounts{
        private long low = 0;
        private long high = 0;
    }
    private record Signal(String source, String destination, Pulse pulse){}
    private static abstract class Module {
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
    }
    private static class BroadcastModule extends Module {
        public BroadcastModule(String id, List<String> connections) {
            super(id, connections);
        }

        @Override
        public Queue<Signal> receiveAndSend(String source, Pulse incomingPulse) {
            return createSendQueue(incomingPulse);
        }
    }
    private static class ConjunctionModule extends Module {
        private List<Module> inputs;
        private Map<String, Pulse> inputMemory;
        public ConjunctionModule(String id, List<String> connections) {
            super(id, connections);
        }
        public void initConnectionMemory(List<Module> inputs){
            this.inputs = inputs;
            this.inputMemory = new HashMap<>();
            for(Module module : inputs){
                inputMemory.put(module.getId(), Pulse.LOW);
            }
        }
        public List<Module> getInputs() {
            return inputs;
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
    }
    private static class FlipFlopModule extends Module {
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
}
