package com.senchenko.person.mapper;

import com.senchenko.person.config.CommonMapperConfig;
import com.senchenko.person.model.dto.PersonDto;
import com.senchenko.person.model.entity.Person;

import java.time.LocalDate;
import java.time.Period;

import org.mapstruct.*;

@Mapper(config = CommonMapperConfig.class)
public interface PersonMapper {

    @Mapping(source = "dateOfBirth", target = "age", qualifiedByName = "calculateAge")
    PersonDto toPersonDto(Person person);

    @Mapping(source = "age", target = "dateOfBirth", qualifiedByName = "calculateDateOfBirth")
    Person toPerson(PersonDto personDto);

    @Named("calculateAge")
    default Integer calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Named("calculateDateOfBirth")
    default LocalDate calculateDateOfBirth(Integer age) {
        return LocalDate.now().minusYears(age);
    }

    /**
     * Updates a {@link Person} object with values from a {@link PersonDto} object.
     * The ID property of the {@link Person} object will be ignored during the update.
     * Null values in the {@link PersonDto} object will be ignored.
     *
     * @param personDto The {@link PersonDto} object containing the values to be mapped onto the {@link Person} object.
     * @param person The {@link Person} object to be updated with values from the {@link PersonDto} object.
     */
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePersonFromDto(PersonDto personDto, @MappingTarget Person person);
}

