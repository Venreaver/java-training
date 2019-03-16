package com.epam.javatraining.dogapp.dao;

import com.epam.javatraining.dogapp.model.Dog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.epam.javatraining.dogapp.constants.SqlStatements.CREATE_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.DELETE_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_ALL_DOGS;
import static com.epam.javatraining.dogapp.constants.SqlStatements.GET_DOG_PREP;
import static com.epam.javatraining.dogapp.constants.SqlStatements.UPDATE_DOG_PREP;
import static java.sql.Date.valueOf;

@RequiredArgsConstructor
public class JdbcTemplateDogDao implements DogDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Dog> dogRowMapper = (rs, rowNum) ->
            Dog.builder()
               .id(rs.getString("id"))
               .name(rs.getString("name"))
               .dateOfBirth(rs.getObject("birth_date", LocalDate.class))
               .height(rs.getInt("height"))
               .weight(rs.getInt("weight"))
               .build();

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        jdbcTemplate.update(CREATE_DOG_PREP, st -> {
            st.setString(1, dog.getId());
            st.setString(2, dog.getName());
            st.setDate(3, getNullableDate(dog.getDateOfBirth()));
            st.setInt(4, dog.getHeight());
            st.setInt(5, dog.getWeight());
        });
        return dog;
    }

    @Override
    public Collection<Dog> getAll() {
        return jdbcTemplate.query(GET_ALL_DOGS, (Object[]) null, dogRowMapper);
    }

    @Override
    public Dog get(String id) {
        List<Dog> result = jdbcTemplate.query(GET_DOG_PREP, st -> st.setObject(1, id), dogRowMapper);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Dog update(Dog dog) {
        int rowCount = jdbcTemplate.update(UPDATE_DOG_PREP, st -> {
            st.setString(1, dog.getName());
            st.setDate(2, getNullableDate(dog.getDateOfBirth()));
            st.setInt(3, dog.getHeight());
            st.setInt(4, dog.getWeight());
            st.setString(5, dog.getId());
        });
        return rowCount < 1 ? null : dog;
    }

    @Override
    public int delete(String id) {
        return jdbcTemplate.update(DELETE_DOG_PREP, st -> st.setObject(1, id));
    }

    private Date getNullableDate(LocalDate date) {
        return date == null ? null : valueOf(date);
    }
}
