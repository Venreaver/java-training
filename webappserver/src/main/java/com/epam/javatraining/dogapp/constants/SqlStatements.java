package com.epam.javatraining.dogapp.constants;

public interface SqlStatements {
    String GET_ALL_DOGS = "SELECT * FROM dog";

    String CREATE_DOG = "INSERT INTO dog (id, name, birth_date, height, weight) VALUES (%s, %s, %s, %d, %d)";
    String GET_DOG = "SELECT * FROM dog WHERE id = %s";
    String UPDATE_DOG = "UPDATE dog SET name = %s, birth_date = %s, height = %d, weight = %d WHERE id = %s";
    String DELETE_DOG = "DELETE FROM dog WHERE id = %s";

    String CREATE_DOG_PREP = "INSERT INTO dog (id, name, birth_date, height, weight) VALUES (?, ?, ?, ?, ?)";
    String GET_DOG_PREP = "SELECT * FROM dog WHERE id = ?";
    String UPDATE_DOG_PREP = "UPDATE dog SET name = ?, birth_date = ?, height = ?, weight = ? WHERE id = ?";
    String DELETE_DOG_PREP = "DELETE FROM dog WHERE id = ?";
}
