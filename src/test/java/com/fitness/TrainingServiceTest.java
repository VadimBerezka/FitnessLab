package com.fitness;

import com.fitness.model.Client;
import com.fitness.model.Trainer;
import com.fitness.model.Training;
import com.fitness.notification.NotificationService;
import com.fitness.repository.InMemoryTrainingRepository;
import com.fitness.repository.TrainingRepository;
import com.fitness.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingServiceTest {

    static class NotificationSpy implements NotificationService {
        String lastMessage;
        @Override public void notify(String message) { lastMessage = message; }
    }

    private TrainingRepository repository;
    private TrainingService service;
    private Training training;
    private NotificationSpy spy;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTrainingRepository();
        spy = new NotificationSpy();
        service = new TrainingService(repository, spy);

        Trainer trainer = new Trainer("T1", "Саймон", "Марафон");
        training = new Training("TR1", "Йога", trainer, 2);
        repository.save(training);
    }

    @Test
    void shouldEnrollClientSuccessfully() {
        boolean result = service.enrollClient("TR1", new Client("C1", "Вадим"));
        assertTrue(result);
        //assertFalse(result);
        assertEquals(1, training.getParticipants().size());
        assertTrue(spy.lastMessage.contains("записан"));
    }

    @Test
    void shouldRejectDuplicateEnrollment() {
        Client vadim = new Client("C1", "Вадим");
        service.enrollClient("TR1", vadim);
        boolean second = service.enrollClient("TR1", vadim);

        assertFalse(second);
        assertEquals(1, training.getParticipants().size());
        assertTrue(spy.lastMessage.contains("уже записан"));
    }

    @Test
    void shouldRejectEnrollmentWhenTrainingIsFull() {
        service.enrollClient("TR1", new Client("C1", "Вадим"));
        service.enrollClient("TR1", new Client("C2", "Леша"));

        boolean third = service.enrollClient("TR1", new Client("C3", "Влад"));

        assertFalse(third);
        assertEquals(2, training.getParticipants().size());
        assertTrue(spy.lastMessage.contains("нет мест"));
    }

    @Test
    void shouldNotAllowEnrollingToFullTraining() {
        Training fullTraining = new Training("TR2", "Кроссфит", new Trainer("T2", "Петр", "Кроссфит"), 1);
        repository.save(fullTraining);

        service.enrollClient("TR2", new Client("C10", "Игорь"));
        boolean result = service.enrollClient("TR2", new Client("C11", "Олег"));

        assertFalse(result, "Второй клиент не должен записаться, мест нет");
        assertEquals(1, fullTraining.getParticipants().size());
    }
}