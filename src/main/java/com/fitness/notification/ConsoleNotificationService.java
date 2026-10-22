package com.fitness.notification;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("[NOTIFY] " + message);
    }
}