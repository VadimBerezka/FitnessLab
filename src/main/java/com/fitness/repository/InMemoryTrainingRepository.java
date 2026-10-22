package com.fitness.repository;

import com.fitness.model.Training;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTrainingRepository implements TrainingRepository {
    private final Map<String, Training> storage = new HashMap<>();

    @Override
    public void save(Training training) {
        storage.put(training.getId(), training);
    }

    @Override
    public Optional<Training> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}