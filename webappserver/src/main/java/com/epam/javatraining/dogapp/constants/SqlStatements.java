package com.epam.javatraining.dogapp.constants;

public interface SqlStatements {
    String GET_DOG = "SELECT * FROM dog WHERE id = '%s'";
    String GET_ALL_DOGS = "SELECT * FROM dog";
    String CREATE_DOG = "INSERT INTO dog (id, name, birth_date, height, weight) VALUES ('%s', '%s', '%s', %d, %d)";
    String UPDATE_DOG = "UPDATE dog SET name = '%s', birth_date = '%s', height = %d, weight = %d WHERE id = '%s'";
    String DELETE_DOG = "DELETE FROM dog WHERE id = '%s'";
}
