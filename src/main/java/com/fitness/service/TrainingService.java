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
        logger.info("Попытка записи клиента {} на тренировку {}", client.getName(), trainingId);

        Training training = repository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Тренировка не найдена: " + trainingId));

        if (training.isFull()) {
            logger.warn("Отказ в записи: на тренировке {} нет свободных мест", trainingId);
            notificationService.notify("Отказ: на тренировку \"" + training.getTitle() + "\" больше нет мест.");
            return false;
        }

        boolean added = training.addParticipant(client);
        if (added) {
            repository.save(training);
            logger.info("Клиент {} успешно записан на тренировку {}", client.getName(), trainingId);
            notificationService.notify("Клиент " + client.getName() + " записан на \"" + training.getTitle() + "\".");
        } else {
            logger.error("Ошибка записи: клиент {} уже был записан на тренировку {}", client.getName(), trainingId);
            notificationService.notify("Клиент " + client.getName() + " уже записан.");
        }
        return added;
    }
}