package ca.arctechlabs.aoc.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    private final int year;
    private static String BASE_PATH = "src/test/resources/%s/%s/";

    public FileLoader(int year){
        this.year = year;
    }

    public List<String> loadLines(String fileName){
        String testType;
        if(fileName.startsWith("sample")){
            testType = "samples";
        }
        else if(fileName.startsWith("input")){
            testType = "inputs";
        }
        else{
            throw new IllegalArgumentException();
        }

        String path = String.format(BASE_PATH, this.year, testType) + fileName;
        File input = new File(path);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while(reader.ready()){
                lines.add(reader.readLine());
            }
        } catch (IOException e) { e.printStackTrace(); }

        return lines;
    }
}
