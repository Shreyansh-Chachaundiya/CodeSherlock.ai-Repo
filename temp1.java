package com.example.processing;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Base class representing a generic data processor.
 * Handles loading, validation, transformation, exporting, stats,
 * caching, and utility operations.
 *
 * This file intentionally contains ~200 lines of real code.
 */
public class DataProcessor {

    // -----------------------------------------
    // Fields
    // -----------------------------------------

    protected String source;
    protected boolean cacheEnabled;
    protected List<Integer> data;
    protected List<Integer> cache;

    // -----------------------------------------
    // Constructor
    // -----------------------------------------

    public DataProcessor(String source, boolean cacheEnabled) {
        this.source = source;
        this.cacheEnabled = cacheEnabled;
        this.data = new ArrayList<>();
        this.cache = new ArrayList<>();
    }

    // -----------------------------------------
    // Core Lifecycle Methods
    // -----------------------------------------

    /** Loads sample data for demonstration. */
    public List<Integer> load() {
        log("Loading data from: " + source);

        data.clear();
        for (int i = 1; i <= 100; i++) {
            data.add(i);
        }

        log("Loaded " + data.size() + " records.");
        return data;
    }

    /** Validates that data is consistent and usable. */
    public boolean validate() {
        log("Validating data...");

        if (data == null) {
            throw new IllegalStateException("Data not initialized.");
        }
        if (data.contains(null)) {
            throw new IllegalArgumentException("Data contains null values.");
        }

        log("Validation successful.");
        return true;
    }

    /** Squares all values as a sample transformation. */
    public List<Integer> transform() {
        log("Transforming data...");

        data = data.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());

        log("Transformation complete.");
        return data;
    }

    // -----------------------------------------
    // Saving & Exporting
    // -----------------------------------------

    public void save(String file) {
        log("Saving to file: " + file);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int d : data) {
                bw.write(String.valueOf(d));
                bw.newLine();
            }
        } catch (IOException e) {
            log("Error saving file: " + e.getMessage());
        }
    }

    public void exportCsv(String file) {
        log("Exporting CSV...");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("value");
            bw.newLine();
            for (int d : data) {
                bw.write(d + "");
                bw.newLine();
            }
        } catch (IOException e) {
            log("CSV export failed: " + e.getMessage());
        }
    }

    public void exportJson(String file) {
        log("Exporting JSON...");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(data.toString());
        } catch (IOException e) {
            log("JSON export failed: " + e.getMessage());
        }
    }

    // -----------------------------------------
    // Stats
    // -----------------------------------------

    public Map<String, Double> stats() {
        log("Calculating statistics...");

        Map<String, Double> map = new HashMap<>();
        if (data.isEmpty()) {
            return map;
        }

        map.put("min", (double) Collections.min(data));
        map.put("max", (double) Collections.max(data));
        map.put("mean", data.stream().mapToDouble(x -> x).average().orElse(0));

        List<Integer> sorted = new ArrayList<>(data);
        Collections.sort(sorted);
        double median;
        int size = sorted.size();
        if (size % 2 == 0)
            median = (sorted.get(size / 2) + sorted.get(size / 2 - 1)) / 2.0;
        else
            median = sorted.get(size / 2);

        map.put("median", median);

        log("Statistics: " + map);
        return map;
    }

    // -----------------------------------------
    // Cache Management
    // -----------------------------------------

    public void cacheData() {
        if (cacheEnabled) {
            cache = new ArrayList<>(data);
            log("Data cached.");
        }
    }

    public void rollback() {
        if (!cache.isEmpty()) {
            data = new ArrayList<>(cache);
            log("Rolled back to cached version.");
        } else {
            log("No cache available.");
        }
    }

    public void clearCache() {
        cache.clear();
        log("Cache cleared.");
    }

    // -----------------------------------------
    // Utility Methods (fill meaningful lines)
    // -----------------------------------------

    public void filter(int threshold) {
        data = data.stream().filter(x -> x > threshold).collect(Collectors.toList());
    }

    public void multiply(int factor) {
        data = data.stream().map(x -> x * factor).collect(Collectors.toList());
    }

    public void subtract(int value) {
        data = data.stream().map(x -> x - value).collect(Collectors.toList());
    }

    public void reverse() {
        Collections.reverse(data);
    }

    public void unique() {
        data = new ArrayList<>(new LinkedHashSet<>(data));
    }

    public void sortAsc() {
        Collections.sort(data);
    }

    public void sortDesc() {
        data.sort(Collections.reverseOrder());
    }

    public void preview() {
        log("Preview: " + data.stream().limit(5).toList());
    }

    // -----------------------------------------
    // Logging
    // -----------------------------------------

    protected void log(String msg) {
        System.out.println("[DataProcessor] " + msg);
    }

    // -----------------------------------------
    // Pipeline
    // -----------------------------------------

    public void pipeline() {
        load();
        validate();
        transform();
        cacheData();
        preview();
    }
}
