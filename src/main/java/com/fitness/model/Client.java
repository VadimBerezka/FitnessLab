package com.fitness.model;

import java.util.Objects;

public class Client {
    private final String id;
    private final String name;

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public String toString() {
        return "Client{id='" + id + "', name='" + name + "'}";
    }
}