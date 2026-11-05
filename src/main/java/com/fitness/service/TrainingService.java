package com.fitness.service;

import com.fitness.model.Client;
import com.fitness.model.Training;
import com.fitness.notification.NotificationService;
import com.fitness.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainingService {
    private final TrainingRepository repository;
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    public TrainingService(TrainingRepository repository,
                           NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public boolean enrollClient(String trainingId, Client client) {
        logger.info("Attempt to sign up a client {} for a training session {}", client.getName(), trainingId);
        // Попытка записи клиента {} на тренировку {}
        Training training = repository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training session not found:" + trainingId));

        if (training.isFull()) {
            logger.warn("Refusal to register: there are no empty seats at the training {}", trainingId);
            // Отказ в записи: на тренировке {} нет свободных мест
            notificationService.notify("Refusal: for training \"" + training.getTitle() + "\" There are no more seats.");
            return false;
        }

        boolean added = training.addParticipant(client);
        if (added) {
            repository.save(training);
            logger.info("Client {} successfully signed up for training {}", client.getName(), trainingId);
            notificationService.notify("Client " + client.getName() + " signed up for \"" + training.getTitle() + "\".");
        } else {
            logger.error("Recording error: client {} has already been signed up for training {}", client.getName(), trainingId);
            notificationService.notify("Client " + client.getName() + " It's already been recorded.");
        }
        return added;
    }
}