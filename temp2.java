package com.example.processing;

import java.util.*;

/**
 * Advanced data processor extending DataProcessor.
 * Adds preprocessing, ML-like workflow, metrics, batch processing,
 * prediction, tuning, and extended pipeline.
 *
 * This file intentionally contains ~200 lines of real Java code.
 */
public class AdvancedDataProcessor extends DataProcessor {

    private MLModel model;
    private List<PreprocessStep> preprocessSteps;
    private Map<String, Double> metrics;

    // -----------------------------------------
    // Constructor
    // -----------------------------------------

    public AdvancedDataProcessor(String source) {
        super(source, true);
        this.model = new MLModel();
        this.preprocessSteps = new ArrayList<>();
        this.metrics = new HashMap<>();
    }

    // -----------------------------------------
    // Preprocessing
    // -----------------------------------------

    public void addPreprocessStep(PreprocessStep step) {
        preprocessSteps.add(step);
    }

    public void removePreprocessStep(PreprocessStep step) {
        preprocessSteps.remove(step);
    }

    public List<Integer> preprocess() {
        log("Running preprocess steps...");
        for (PreprocessStep step : preprocessSteps) {
            data = step.apply(data);
        }
        log("Preprocessing complete.");
        return data;
    }

    // -----------------------------------------
    // ML-like Operations
    // -----------------------------------------

    public Map<String, Object> trainModel() {
        log("Training model...");
        Map<String, Object> result = model.train(data);
        log("Training result: " + result);
        return result;
    }

    public Map<String, Double> evaluateModel() {
        log("Evaluating model...");
        Map<String, Double> result = model.evaluate(data);
        metrics.putAll(result);
        log("Evaluation: " + result);
        return result;
    }

    public double tuneModel() {
        log("Tuning hyperparameters...");

        double best = 0;
        for (double lr : new double[]{0.01, 0.05, 0.1}) {
            double score = lr * 9.2; // Fake logic
            if (score > best) {
                best = score;
            }
        }

        metrics.put("tunedScore", best);
        log("Best score: " + best);
        return best;
    }

    public List<Integer> predictAll() {
        List<Integer> out = new ArrayList<>();
        for (int d : data) {
            out.add(model.predict(d));
        }
        return out;
    }

    public void printMetrics() {
        log("Metrics: " + metrics);
    }

    // -----------------------------------------
    // Batch Processing
    // -----------------------------------------

    public List<Map<String, Object>> batchProcess(int batches) {
        log("Batch processing: " + batches);

        List<Map<String, Object>> results = new ArrayList<>();
        int size = data.size() / batches;

        for (int i = 0; i < batches; i++) {
            int start = i * size;
            int end = Math.min(start + size, data.size());
            List<Integer> batch = data.subList(start, end);

            results.add(model.train(batch));
        }

        return results;
    }

    // -----------------------------------------
    // Visualization (text-based)
    // -----------------------------------------

    public void visualize() {
        log("Visualizing top values...");
        System.out.println("Chart Sample: " + data.stream().limit(5).toList());
    }

    // -----------------------------------------
    // Extended Pipeline (Overrides)
    // -----------------------------------------

    @Override
    public void pipeline() {
        load();
        validate();
        preprocess();
        transform();
        trainModel();
        evaluateModel();
        tuneModel();
        visualize();
        cacheData();
        preview();
    }

    // -----------------------------------------
    // Inner Interfaces & Mock ML Model
    // -----------------------------------------

    /** Preprocess functional interface */
    public interface PreprocessStep {
        List<Integer> apply(List<Integer> input);
    }

    /** Dummy ML Model class */
    public static class MLModel {
        public Map<String, Object> train(List<Integer> data) {
            Map<String, Object> m = new HashMap<>();
            m.put("trained", true);
            m.put("size", data.size());
            return m;
        }

        public Map<String, Double> evaluate(List<Integer> data) {
            Map<String, Double> m = new HashMap<>();
            m.put("accuracy", 0.91);
            return m;
        }

        public int predict(int value) {
            return value * 2;
        }
    }
}
