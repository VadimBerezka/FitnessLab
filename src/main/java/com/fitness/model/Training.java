package com.fitness.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Training {
    private final String id;
    private final String title;
    private final Trainer trainer;
    private final int maxParticipants;
    private final List<Client> participants = new ArrayList<>();

    public Training(String id, String title, Trainer trainer, int maxParticipants) {
        if (maxParticipants <= 0) {
            throw new IllegalArgumentException("maxParticipants must be > 0");
        }
        this.id = id;
        this.title = title;
        this.trainer = trainer;
        this.maxParticipants = maxParticipants;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public Trainer getTrainer() { return trainer; }
    public int getMaxParticipants() { return maxParticipants; }

    public List<Client> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public boolean isFull() {
        return participants.size() >= maxParticipants;
    }

    public boolean hasParticipant(Client client) {
        return participants.contains(client);
    }

    public boolean addParticipant(Client client) {
        if (isFull() || hasParticipant(client)) {
            return false;
        }
        participants.add(client);
        return true;
    }

    @Override
    public String toString() {
        return "Training{id='" + id + "', title='" + title +
                "', trainer=" + trainer.getName() +
                ", participants=" + participants.size() + "/" + maxParticipants + "}";
    }
}