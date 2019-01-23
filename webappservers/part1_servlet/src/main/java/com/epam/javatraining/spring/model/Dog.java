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
    private Integer height;
    @NotNull
    @Positive
    private Integer weight;

    public Dog() {
    }

    public Dog(String id, String name, LocalDate dateOfBirth, Integer height, Integer weight) {
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return height.equals(dog.height) &&
                weight.equals(dog.weight) &&
                id.equals(dog.id) &&
                name.equals(dog.name) &&
                Objects.equals(dateOfBirth, dog.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, height, weight);
    }
}
