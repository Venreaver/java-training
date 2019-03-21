package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.epam.javatraining.dogapp.constants.SqlStatements.CREATE_DOG;
import static com.epam.javatraining.dogapp.constants.SqlStatements.DELETE_DOG;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_ALL_DOGS;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_DOG;
import static com.epam.javatraining.dogapp.constants.SqlStatements.UPDATE_DOG;

public class JdbcStatementDogDao extends JdbcDogDao {
    public JdbcStatementDogDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        executeUpdate(String.format(CREATE_DOG,
                quote(dog.getId()), quote(dog.getName()),
                quote(dog.getDateOfBirth()), dog.getHeight(), dog.getWeight()));
        return dog;
    }

    @Override
    public Collection<Dog> getAll() {
        return executeQuery(GET_ALL_DOGS);
    }

    @Override
    public Dog get(String id) {
        List<Dog> result = executeQuery(String.format(GET_DOG, quote(id)));
        return result.isEmpty() ? null: result.get(0);
    }

    @Override
    public Dog update(Dog dog) {
        int rowCount = executeUpdate(String.format(UPDATE_DOG,
                quote(dog.getName()), quote(dog.getDateOfBirth()),
                dog.getHeight(), dog.getWeight(), quote(dog.getId())));
        return rowCount < 1 ? null : dog;
    }

    @Override
    public int delete(String id) {
        return executeUpdate(String.format(DELETE_DOG, quote(id)));
    }

    private List<Dog> executeQuery(String sqlQuery) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            List<Dog> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(dogRowMapper.mapRow(resultSet, 0));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int executeUpdate(String sqlQuery) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String quote(Object obj) {
        return obj == null ? null : String.format("'%s'", obj.toString());
    }
}
