package com.example.processing;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Base class for data processing tasks.
 * Handles loading, validating, transforming, exporting, caching, etc.
 */
public class DataProcessor {

    protected String source;
    protected boolean cacheEnabled;
    protected List<Integer> data;
    protected List<Integer> cache;

    public DataProcessor(String source, boolean cacheEnabled) {
        this.source = source;
        this.cacheEnabled = cacheEnabled;
        this.data = new ArrayList<>();
        this.cache = new ArrayList<>();
    }

    // ------------------------------------------------------
    // Core Lifecycle Methods
    // ------------------------------------------------------

    /** Simulates loading data from any source. */
    public List<Integer> load() {
        log("Loading data...");
        try {
            for (int i = 1; i <= 50; i++) {
                data.add(i);
            }
            log("Loaded " + data.size() + " items.");
        } catch (Exception e) {
            log("Load failed: " + e.getMessage());
            throw e;
        }
        return data;
    }

    /** Validates loaded data. */
    public boolean validate() {
        log("Validating data...");

        if (data == null) {
            throw new IllegalStateException("Data is null.");
        }

        if (data.contains(null)) {
            throw new IllegalArgumentException("Data contains null values.");
        }

        log("Validation successful.");
        return true;
    }

    /** Transforms data by squaring each value. */
    public List<Integer> transform() {
        log("Transforming data...");

        data = data.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());

        log("Transformation completed.");
        return data;
    }

    /** Saves processed data to file. */
    public void save(String filePath) {
        log("Saving data to " + filePath + "...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Integer i : data) {
                writer.write(i.toString());
                writer.newLine();
            }
            log("Data saved.");
        } catch (IOException e) {
            log("Save failed: " + e.getMessage());
        }
    }

    // ------------------------------------------------------
    // Cache Management
    // ------------------------------------------------------

    public void cacheData() {
        if (cacheEnabled) {
            cache = new ArrayList<>(data);
            log("Data cached.");
        }
    }

    public void clearCache() {
        cache.clear();
        log("Cache cleared.");
    }

    // ------------------------------------------------------
    // Data Exporting
    // ------------------------------------------------------

    public void exportJson(String filePath) {
        log("Exporting JSON: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data.toString());
            log("JSON exported.");
        } catch (IOException e) {
            log("JSON export failed: " + e.getMessage());
        }
    }

    public void exportCsv(String filePath) {
        log("Exporting CSV: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("value");
            writer.newLine();
            for (Integer i : data) {
                writer.write(i.toString());
                writer.newLine();
            }
            log("CSV exported.");
        } catch (IOException e) {
            log("CSV export failed: " + e.getMessage());
        }
    }

    // ------------------------------------------------------
    // Stats & Utilities
    // ------------------------------------------------------

    public Map<String, Double> getStats() {
        log("Calculating statistics...");

        if (data.isEmpty()) return new HashMap<>();

        Map<String, Double> stats = new HashMap<>();
        stats.put("min", (double) Collections.min(data));
        stats.put("max", (double) Collections.max(data));

        double mean = data.stream().mapToDouble(x -> x).average().orElse(0);
        stats.put("mean", mean);

        Collections.sort(data);
        double median = data.size() % 2 == 0
                ? (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2.0
                : data.get(data.size() / 2);

        stats.put("median", median);

        log("Stats: " + stats.toString());
        return stats;
    }

    // ------------------------------------------------------
    // Utility Methods (to reach ~200 LOC)
    // ------------------------------------------------------

    public void filterGreaterThan(int threshold) {
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

    public void append(List<Integer> extra) {
        data.addAll(extra);
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

    public void printPreview() {
        log("Preview: " + data.stream().limit(5).toList());
    }

    // ------------------------------------------------------
    // Logging
    // ------------------------------------------------------

    protected void log(String msg) {
        System.out.println("[DataProcessor] " + msg);
    }

    // Pipeline (Load → Validate → Transform → Cache)
    public void pipeline() {
        load();
        validate();
        transform();
        cacheData();
        printPreview();
    }
}
