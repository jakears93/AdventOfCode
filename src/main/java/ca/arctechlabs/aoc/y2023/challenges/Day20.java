package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.models.*;
import ca.arctechlabs.aoc.common.models.Module;

import java.util.*;

public class Day20 {
    public long productOfDifferentSignals(List<String> input, int cycles) {
        Map<String, Module> moduleMap = parseModules(input);
        PulseCounts pulseCounts = countPulses(moduleMap, cycles);
        return pulseCounts.high * pulseCounts.low;
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
                List<String> inputs = moduleMap.values().stream().filter(m -> m.getConnections().contains(module.getId())).map(Module::getId).toList();
                ((ConjunctionModule) module).initConnectionMemory(inputs);
            }
        }

        return moduleMap;
    }

    private static class PulseCounts{
        private long low = 0;
        private long high = 0;
    }
}
