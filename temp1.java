package com.example.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for processing integer data.
 * About 100 lines of clean, simple Java code.
 */
public class DataProcessor {

    protected List<Integer> data;
    protected String source;

    public DataProcessor(String source) {
        this.source = source;
        this.data = new ArrayList<>();
    }

    public void loadDummyData() {
        for (int i = 1; i <= 20; i++) {
            data.add(i);
        }
    }

    public void addValue(int value) {
        data.add(value);
    }

    public void removeValue(int value) {
        data.remove(Integer.valueOf(value));
    }

    public List<Integer> getData() {
        return data;
    }

    public void printData() {
        System.out.println("Data: " + data);
    }

    public void sortAscending() {
        Collections.sort(data);
    }

    public void sortDescending() {
        data.sort(Collections.reverseOrder());
    }

    public int getMin() {
        return data.isEmpty() ? 0 : Collections.min(data);
    }

    public int getMax() {
        return data.isEmpty() ? 0 : Collections.max(data);
    }

    public double getAverage() {
        if (data.isEmpty()) return 0;
        double sum = 0;
        for (int x : data) {
            sum += x;
        }
        return sum / data.size();
    }

    public void clearData() {
        data.clear();
    }

    public void printSummary() {
        System.out.println("Source: " + source);
        System.out.println("Count: " + data.size());
        System.out.println("Min: " + getMin());
        System.out.println("Max: " + getMax());
        System.out.println("Avg: " + getAverage());
    }

    public void pipeline() {
        loadDummyData();
        printSummary();
        sortDescending();
        printData();
    }
}
