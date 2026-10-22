package com.fitness;

import com.fitness.model.Client;
import com.fitness.model.Trainer;
import com.fitness.model.Training;
import com.fitness.notification.ConsoleNotificationService;
import com.fitness.notification.NotificationService;
import com.fitness.repository.InMemoryTrainingRepository;
import com.fitness.repository.TrainingRepository;
import com.fitness.service.TrainingService;

public class Main {
    public static void main(String[] args) {
        TrainingRepository repository = new InMemoryTrainingRepository();
        NotificationService notifier = new ConsoleNotificationService();
        TrainingService service = new TrainingService(repository, notifier);

        Trainer trainer = new Trainer("T1", "Прохор Гулецкий", "геймер");
        Training games = new Training("TR1", "комп игры", trainer, 2);
        repository.save(games);

        service.enrollClient("TR1", new Client("C1", "Вадим"));
        service.enrollClient("TR1", new Client("C2", "Алексей"));

        System.out.println("--- Попытка записать третьего ---");
        service.enrollClient("TR1", new Client("C3", "Владислав"));

        System.out.println("--- Итог ---");
        System.out.println(games);
    }
}