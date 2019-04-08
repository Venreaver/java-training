package com.epam.javatraining.dogapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    private UUID id;
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
    private Set<Human> owners;
}
