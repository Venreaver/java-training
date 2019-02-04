package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_DOG_BY_ID;

@AllArgsConstructor
public class JdbcDogDao implements DogDao {
    private final DataSource dataSource;
    private final RowMapper<Dog> dogRowMapper = (rs, rowNum) ->
            Dog.builder()
               .id(rs.getString("id"))
               .name(rs.getString("name"))
               .dateOfBirth(rs.getDate("birth_date").toLocalDate())
               .height(rs.getInt("height"))
               .weight(rs.getInt("weight"))
               .build();

    @Override
    public Dog create(Dog dog) {
        return null;
    }

    @Override
    public Collection<Dog> getAll() {
        return null;
    }

    @Override
    public Dog get(String id) {
        return executeQuery(String.format(GET_DOG_BY_ID, id));
    }

    @Override
    public Dog update(Dog dog) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    private Dog executeQuery(String id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(GET_DOG_BY_ID, id))) {
            if (resultSet.next()) {
                return dogRowMapper.mapRow(resultSet, 0);
            } else {
                throw new DogNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
