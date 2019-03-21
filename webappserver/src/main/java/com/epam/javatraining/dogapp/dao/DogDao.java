package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;

public interface DogDao extends Dao<Dog> {
    RowMapper<Dog> DOG_ROW_MAPPER = (rs, rowNum) ->
            Dog.builder()
               .id(rs.getString("id"))
               .name(rs.getString("name"))
               .dateOfBirth(rs.getObject("birth_date", LocalDate.class))
               .height(rs.getInt("height"))
               .weight(rs.getInt("weight"))
               .build();
}
