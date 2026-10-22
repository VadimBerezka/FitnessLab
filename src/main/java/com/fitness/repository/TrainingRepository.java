package com.fitness.repository;

import com.fitness.model.Training;
import java.util.Optional;

public interface TrainingRepository {
    void save(Training training);
    Optional<Training> findById(String id);
}