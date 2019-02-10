package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.time.LocalDate;

@AllArgsConstructor
public abstract class JdbcDogDao implements DogDao {
    final DataSource dataSource;
    final RowMapper<Dog> dogRowMapper = (rs, rowNum) ->
            Dog.builder()
               .id(rs.getString("id"))
               .name(rs.getString("name"))
               .dateOfBirth(rs.getObject("birth_date", LocalDate.class))
               .height(rs.getInt("height"))
               .weight(rs.getInt("weight"))
               .build();
}
