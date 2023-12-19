package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day19 {
    public long sumOfAcceptedParts(List<String> input) {
        List<Part> parts = parseParts(input);
        Map<String, List<Rule>> originMap = createOriginMap(input);
        List<Part> acceptedParts = findAcceptedParts(originMap, parts);
        return acceptedParts.stream()
                .mapToInt(Part::sumOfValues)
                .sum();
    }

    private List<Part> findAcceptedParts(Map<String, List<Rule>> originMap, List<Part> parts) {
        List<Part> acceptedParts = new ArrayList<>();
        for(Part part : parts){
            String location = "in";
            String next;
            List<Rule> rules;
            
            while(true){
                rules = originMap.get(location);
                for(Rule rule : rules){
                    next = rule.evaluate(part);
                    if(next != null){
                        location = next;
                        break;
                    }
                }
                if(location.equals("A")){
                    acceptedParts.add(part);
                    break;
                }
                else if(location.equals("R")){
                    break;
                }
            }
        }
        return acceptedParts;
    }

    private Map<String, List<Rule>> createOriginMap(List<String> input){
        Map<String, List<Rule>> originMap = new HashMap<>();
        for(String line : input) {
            if (line.isBlank()) {
                break;
            }
            String origin = line.split("\\{")[0];
            List<Rule> rules = createRules(line.replace(origin+"{", "").replace("}", ""));
            originMap.put(origin, rules);
        }
        return originMap;
    }

    private List<Rule> createRules(String cleanLine) {
        List<Rule> rules = new ArrayList<>();
        String[] rawRules = cleanLine.split(",");
        for(int i=0; i<rawRules.length-1; i++){
            char operator;
            if(rawRules[i].contains(">")) operator = '>';
            else operator = '<';
            
            String destination = rawRules[i].split(":")[1];
            
            String[] fieldAndComparison = rawRules[i].replace(":", "").replace(destination, "").split(String.valueOf(operator));
            char field =  fieldAndComparison[0].charAt(0);
            int comparison = Integer.parseInt(fieldAndComparison[1]);
            
            rules.add(parseRule(field, operator, comparison, destination));
        }
        rules.add(parseDefaultRule(rawRules[rawRules.length-1]));
        return rules;
    }

    private Rule parseDefaultRule(String destination){
        return parseRule('n', 'n', 0, destination);
    }
    private Rule parseRule(char field, char operator, int comparison, String destination){
        Predicate<Part> test = p -> true;
        if(field == 'x'){
            if(operator == '>') test = p -> p.x > comparison;
            else test = p -> p.x < comparison;
        }
        else if(field == 'm'){
            if(operator == '>') test = p -> p.m > comparison;
            else test = p -> p.m < comparison;
        }
        else if(field == 'a'){
            if(operator == '>') test = p -> p.a > comparison;
            else test = p -> p.a < comparison;
        }
        else if(field == 's'){
            if(operator == '>') test = p -> p.s > comparison;
            else test = p -> p.s < comparison;
        }
//        System.out.println(field +""+ operator +""+ comparison + ":" + destination);
        return new Rule(test, destination);
    }
    private List<Part> parseParts(List<String> input){
        List<Part> parts = new ArrayList<>();
        boolean hasConsumedEmptyLine = false;
        for(String line : input){
            if(line.isBlank()){
                hasConsumedEmptyLine = true;
                continue;
            }
            else if (!hasConsumedEmptyLine) continue;
            String workingLine = line.replace("{", "").replace("}", "");
            String[] rawComponents = workingLine.split(",");
            int x = Integer.parseInt(rawComponents[0].split("=")[1]);
            int m = Integer.parseInt(rawComponents[1].split("=")[1]);
            int a = Integer.parseInt(rawComponents[2].split("=")[1]);
            int s = Integer.parseInt(rawComponents[3].split("=")[1]);
            parts.add(new Part(x,m,a,s));
        }
        return parts;
    }
    private record Part(int x, int m, int a, int s){
        public int sumOfValues(){
            return x+m+a+s;
        }
    }

    private record Rule(Predicate<Part> predicate, String destination) {
        public String evaluate(Part p) {
            if (predicate.test(p)) {
                return destination;
            }
            return null;
        }
    }
}
