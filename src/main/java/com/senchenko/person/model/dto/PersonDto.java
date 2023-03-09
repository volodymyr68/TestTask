package com.senchenko.person.model.dto;

import com.senchenko.person.config.CrudGroups;
import com.senchenko.person.config.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Dto
public class PersonDto {

    private UUID id;
    @NotNull(groups = CrudGroups.Create.class)
    private String firstName;

    @NotNull(groups = CrudGroups.Create.class)
    private String lastName;

    @NotNull(groups = CrudGroups.Create.class)
    private Integer age;

    public PersonDto(UUID id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public PersonDto() {

    }
}
