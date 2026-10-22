package com.fitness.service;

import com.fitness.model.Client;
import com.fitness.model.Training;
import com.fitness.notification.NotificationService;
import com.fitness.repository.TrainingRepository;

public class TrainingService {
    private final TrainingRepository repository;
    private final NotificationService notificationService;

    public TrainingService(TrainingRepository repository,
                           NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public boolean enrollClient(String trainingId, Client client) {
        Training training = repository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Тренировка не найдена: " + trainingId));

        if (training.isFull()) {
            notificationService.notify(
                    "Отказ: на тренировку \"" + training.getTitle() +
                            "\" больше нет мест (макс. " + training.getMaxParticipants() + ").");
            return false;
        }

        boolean added = training.addParticipant(client);
        if (added) {
            repository.save(training);
            notificationService.notify(
                    "Клиент " + client.getName() + " записан на \"" +
                            training.getTitle() + "\".");
        } else {
            notificationService.notify(
                    "Клиент " + client.getName() + " уже записан на \"" +
                            training.getTitle() + "\".");
        }
        return added;
    }
}