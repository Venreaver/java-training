package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static com.epam.javatraining.dogapp.constants.SqlStatements.CREATE_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.DELETE_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_ALL_DOGS;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.UPDATE_DOG_PREP;

public class JdbcPreparedStatementDogDao extends JdbcDogDao {
    public JdbcPreparedStatementDogDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        executeUpdate(CREATE_DOG_PREP,
                statement -> {
                    try {
                        statement.setString(1, dog.getId());
                        statement.setString(2, dog.getName());
                        statement.setString(3, getNullableDate(dog.getDateOfBirth()));
                        statement.setInt(4, dog.getHeight());
                        statement.setInt(5, dog.getWeight());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        return dog;
    }

    @Override
    public Collection<Dog> getAll() {
        return executeQuery(GET_ALL_DOGS, preparedStatement -> {
        });
    }

    @Override
    public Dog get(String id) {
        List<Dog> result = executeQuery(GET_DOG_PREP, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        if (result.isEmpty()) {
            throw new DogNotFoundException(id);
        }
        return result.get(0);
    }

    @Override
    public Dog update(Dog dog) {
        int rowCount = executeUpdate(UPDATE_DOG_PREP,
                statement -> {
                    try {
                        statement.setString(1, dog.getName());
                        statement.setString(2, getNullableDate(dog.getDateOfBirth()));
                        statement.setInt(3, dog.getHeight());
                        statement.setInt(4, dog.getWeight());
                        statement.setString(5, dog.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
        if (rowCount < 1) {
            throw new DogNotFoundException(dog.getId());
        }
        return dog;
    }

    @Override
    public void delete(String id) {
        int rowCount = executeUpdate(DELETE_DOG_PREP, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        if (rowCount < 1) {
            throw new DogNotFoundException(id);
        }
    }

    private List<Dog> executeQuery(String sqlQuery, Consumer<PreparedStatement> consumer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            List<Dog> result = new ArrayList<>();
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(dogRowMapper.mapRow(resultSet, 0));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int executeUpdate(String sqlQuery, Consumer<PreparedStatement> consumer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            consumer.accept(statement);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNullableDate(LocalDate date) {
        return date == null ? null : date.toString();
    }
}
