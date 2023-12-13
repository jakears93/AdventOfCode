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

    public List<String> readAsLines(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile(fileName)))) {
            return reader.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getFile(String fileName){
        TestType type = TestType.fromFileName(fileName);
        String path = String.format(BASE_PATH, this.year, type.getValue()) + fileName;
        return new File(path);
    }
}

enum TestType{
    SAMPLE("sample", "samples"),
    INPUT("input", "inputs");

    private final String key;
    private final String value;

    TestType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static TestType fromFileName(String fileName){
        for(TestType type : TestType.values()){
            if(fileName.startsWith(type.key)) return type;
        }
        throw new IllegalArgumentException("Unable to find key for: "+fileName);
    }

    public String getValue() {
        return value;
    }
}
