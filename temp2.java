package com.example.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Child class extending DataProcessor.
 * Contains extra simple methods.
 * About 100 lines of clean Java.
 */
public class AdvancedDataProcessor extends DataProcessor {

    private List<Integer> backup;

    public AdvancedDataProcessor(String source) {
        super(source);
        this.backup = new ArrayList<>();
    }

    public void squareAll() {
        for (int i = 0; i < data.size(); i++) {
            data.set(i, data.get(i) * data.get(i));
        }
    }

    public void multiplyAll(int factor) {
        for (int i = 0; i < data.size(); i++) {
            data.set(i, data.get(i) * factor);
        }
    }

    public void backupData() {
        backup = new ArrayList<>(data);
    }

    public void restoreBackup() {
        if (!backup.isEmpty()) {
            data = new ArrayList<>(backup);
        }
    }

    public List<Integer> filterGreater(int threshold) {
        List<Integer> result = new ArrayList<>();
        for (int x : data) {
            if (x > threshold) {
                result.add(x);
            }
        }
        return result;
    }

    public int sum() {
        int s = 0;
        for (int x : data) {
            s += x;
        }
        return s;
    }

    public void printAdvancedSummary() {
        System.out.println("=== Advanced Summary ===");
        printSummary();
        System.out.println("Sum: " + sum());
        System.out.println("Filtered > 10: " + filterGreater(10));
    }

    @Override
    public void pipeline() {
        loadDummyData();
        backupData();
        squareAll();
        multiplyAll(2);
        printAdvancedSummary();
    }
}
