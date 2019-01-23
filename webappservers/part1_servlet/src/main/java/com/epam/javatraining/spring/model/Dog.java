package com.epam.javatraining.spring.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Dog {
    private String id;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @NotNull
    @Positive
    private int height;
    @NotNull
    @Positive
    private int weight;

    public Dog() {
    }

    public Dog(String id, String name, LocalDate dateOfBirth, int height, int weight) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return height == dog.height &&
                weight == dog.weight &&
                id.equals(dog.id) &&
                name.equals(dog.name) &&
                Objects.equals(dateOfBirth, dog.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, height, weight);
    }
}
