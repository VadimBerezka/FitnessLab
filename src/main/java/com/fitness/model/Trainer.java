package com.fitness.model;

public class Trainer {
    private final String id;
    private final String name;
    private final String specialization;

    public Trainer(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }

    @Override
    public String toString() {
        return "Trainer{id='" + id + "', name='" + name +
                "', specialization='" + specialization + "'}";
    }
}