package io.qala.javatraining.domain;

import java.util.Objects;

public class Human {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Human setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Human setName(String name) {
        this.name = name;
        return this;
    }
}
