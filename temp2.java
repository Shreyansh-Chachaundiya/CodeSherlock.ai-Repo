package com.example.processing;

import java.util.*;

public class AdvancedDataProcessor extends DataProcessor {

    private DummyMLModel model;
    private List<DataStep> preprocessSteps;
    private Map<String, Double> metrics;

    public AdvancedDataProcessor(String source) {
        super(source, true);
        this.model = new DummyMLModel();
        this.preprocessSteps = new ArrayList<>();
        this.metrics = new HashMap<>();
    }

    // ------------------------------------------------------
    // Preprocessing
    // ------------------------------------------------------

    public List<Integer> preprocess() {
        log("Running preprocess steps...");
        for (DataStep step : preprocessSteps) {
            data = step.apply(data);
        }
        return data;
    }

    public void addPreprocessStep(DataStep step) {
        preprocessSteps.add(step);
    }

    public void removePreprocessStep(DataStep step) {
        preprocessSteps.remove(step);
    }

    // ------------------------------------------------------
    // ML Operations
    // ------------------------------------------------------

    public Map<String, Object> trainModel() {
        log("Training model...");
        Map<String, Object> result = model.train(data);
        log("Model trained: " + result);
        return result;
    }

    public Map<String, Double> evaluateModel() {
        log("Evaluating model...");
        Map<String, Double> result = model.evaluate(data);
        metrics.putAll(result);
        log("Evaluation done: " + result);
        return result;
    }

    public double hyperTune() {
        log("Hyperparameter tuning...");
        double best = 0.0;
        for (double lr : new double[]{0.01, 0.05, 0.1}) {
            double score = lr * 9.2; // fake logic
            if (score > best) best = score;
        }
        metrics.put("tunedScore", best);
        log("Best hyperparam score: " + best);
        return best;
    }

    public List<Integer> predictAll() {
        List<Integer> preds = new ArrayList<>();
        for (Integer d : data) {
            preds.add(model.predict(d));
        }
        return preds;
    }

    // ------------------------------------------------------
    // Visualization (text simulation)
    // ------------------------------------------------------

    public void visualize() {
        log("Visualizing data...");
        System.out.println("Sample Chart -> " + data.stream().limit(5).toList());
    }

    // ------------------------------------------------------
    // Batch Processing
    // ------------------------------------------------------

    public List<Map<String, Object>> batchProcess(int batches) {
        log("Batch processing into " + batches + " batches");
        int size = data.size() / batches;

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < batches; i++) {
            List<Integer> batch = data.subList(i * size, Math.min((i + 1) * size, data.size()));
            log("Processing batch " + (i + 1));
            result.add(model.train(batch));
        }
        return result;
    }

    // ------------------------------------------------------
    // Recovery
    // ------------------------------------------------------

    public void rollback() {
        log("Rolling back from cache...");
        if (!cache.isEmpty()) {
            data = new ArrayList<>(cache);
            log("Rollback complete.");
        } else {
            log("No cache; rollback aborted.");
        }
    }

    // ------------------------------------------------------
    // Extended Pipeline
    // ------------------------------------------------------

    @Override
    public void pipeline() {
        load();
        validate();
        preprocess();
        transform();
        trainModel();
        evaluateModel();
        hyperTune();
        visualize();
        cacheData();
        printPreview();
    }

    // ------------------------------------------------------
    // Inner Classes
    // ------------------------------------------------------

    /** Function interface for preprocess steps. */
    public interface DataStep {
        List<Integer> apply(List<Integer> input);
    }

    /** Dummy ML Model (mock implementation). */
    public static class DummyMLModel {

        public Map<String, Object> train(List<Integer> data) {
            Map<String, Object> res = new HashMap<>();
            res.put("trained", true);
            res.put("items", data.size());
            return res;
        }

        public Map<String, Double> evaluate(List<Integer> data) {
            Map<String, Double> res = new HashMap<>();
            res.put("accuracy", 0.92);
            return res;
        }

        public int predict(int x) {
            return x * 2;
        }
    }
}
