package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.AttributeMapping;
import ca.arctechlabs.aoc.y2023.models.SeedInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 {
    //https://adventofcode.com/2023/day/5

    private static final String SEED_HEADER = "seeds: ";
    private static final String SOIL_HEADER = "seed-to-soil map:";
    private static final String FERTILIZER_HEADER = "soil-to-fertilizer map:";
    private static final String WATER_HEADER = "fertilizer-to-water map:";
    private static final String LIGHT_HEADER = "water-to-light map:";
    private static final String TEMP_HEADER = "light-to-temperature map:";
    private static final String HUMIDITY_HEADER = "temperature-to-humidity map:";
    private static final String LOCATION_HEADER = "humidity-to-location map:";

    public long inPlaceMinimumLocation(List<String> input, boolean seedsAsRange){
        Map<String, List<AttributeMapping>> attributeMaps = getAttributeMaps(input);
        String[] seedData = input.get(0).substring(SEED_HEADER.length()).split(" ");
        return getMinimumLocation(seedData, attributeMaps, seedsAsRange);
    }

    private long getMinimumLocation(String[] data, Map<String, List<AttributeMapping>> attributeMaps, boolean seedsAsRange){
        Long minimumLocation = Long.MAX_VALUE;
        for(int i=0; i < data.length; i++){
            SeedInfo seed;
            long start = Long.parseLong(data[i].trim());
            if(seedsAsRange){
                long range = Long.parseLong(data[++i].trim());
                for(long j=0; j<range; j++){
                    seed = populateSeed(start+j, attributeMaps);
                    if(seed.getLocation() < minimumLocation){
                        minimumLocation = seed.getLocation();
                    }
                }
            }
            else{
                seed = populateSeed(start, attributeMaps);
                if(seed.getLocation() < minimumLocation){
                    minimumLocation = seed.getLocation();
                }
            }

        }
        return minimumLocation;
    }
    private Map<String, List<AttributeMapping>> getAttributeMaps(List<String> input){
        List<AttributeMapping> seedToSoilMap = new ArrayList<>();
        List<AttributeMapping> soilToFertilizerMap = new ArrayList<>();
        List<AttributeMapping> fertilizerToWaterMap = new ArrayList<>();
        List<AttributeMapping> waterToLightMap = new ArrayList<>();
        List<AttributeMapping> lightToTempMap = new ArrayList<>();
        List<AttributeMapping> tempToHumidityMap = new ArrayList<>();
        List<AttributeMapping> humidityToLocationMap = new ArrayList<>();

        for(int index=0; index<input.size(); index++){
            String line = input.get(index);
            if (line.startsWith(SOIL_HEADER)) {
                seedToSoilMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(FERTILIZER_HEADER)) {
                soilToFertilizerMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(WATER_HEADER)) {
                fertilizerToWaterMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(LIGHT_HEADER)) {
                waterToLightMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(TEMP_HEADER)) {
                lightToTempMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(HUMIDITY_HEADER)) {
                tempToHumidityMap = processAttributeMap(input, index);
            }
            else if (line.startsWith(LOCATION_HEADER)) {
                humidityToLocationMap = processAttributeMap(input, index);
            }
        }

        Map<String, List<AttributeMapping>> map = new HashMap<>();
        map.put("seedToSoilMap", seedToSoilMap);
        map.put("soilToFertilizerMap", soilToFertilizerMap);
        map.put("fertilizerToWaterMap", fertilizerToWaterMap);
        map.put("waterToLightMap", waterToLightMap);
        map.put("lightToTempMap", lightToTempMap);
        map.put("tempToHumidityMap", tempToHumidityMap);
        map.put("humidityToLocationMap", humidityToLocationMap);
        return map;
    }

    private SeedInfo populateSeed(long id, Map<String, List<AttributeMapping>> attributeMaps){
        SeedInfo seed = new SeedInfo(id);
        seed.setSoil(getMapping(attributeMaps.get("seedToSoilMap"), seed.getId()));
        seed.setFertilizer(getMapping(attributeMaps.get("soilToFertilizerMap"), seed.getSoil()));
        seed.setWater(getMapping(attributeMaps.get("fertilizerToWaterMap"), seed.getFertilizer()));
        seed.setLight(getMapping(attributeMaps.get("waterToLightMap"), seed.getWater()));
        seed.setTemperature(getMapping(attributeMaps.get("lightToTempMap"), seed.getLight()));
        seed.setHumidity(getMapping(attributeMaps.get("tempToHumidityMap"), seed.getTemperature()));
        seed.setLocation(getMapping(attributeMaps.get("humidityToLocationMap"), seed.getHumidity()));
        return seed;
    }
    private List<AttributeMapping> processAttributeMap(List<String> input, int index){
        List<AttributeMapping> mappings = new ArrayList<>();
        String dataLine = input.get(++index);
        do {

            String[] data = dataLine.split(" ");
            long destRangeStart = Long.parseLong(data[0].trim());
            long sourceRangeStart = Long.parseLong(data[1].trim());
            long rangeLength = Long.parseLong(data[2].trim());
            mappings.add(new AttributeMapping(destRangeStart, sourceRangeStart, rangeLength));
            index++;
            if(index >= input.size()){
                break;
            }
            dataLine = input.get(index);
        }
        while (!dataLine.isEmpty());
        return mappings;
    }

    private long getMapping(List<AttributeMapping> mappings, long source){
        long value = source;
        for(AttributeMapping mapping : mappings){
            if(source >= mapping.getSourceStart() && source < mapping.getSourceStart() + mapping.getRange()){
                long difference = source - mapping.getSourceStart();
                value = mapping.getDestinationStart() + difference;
                break;
            }
        }
        return value;
    }
}
