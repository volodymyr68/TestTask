package com.senchenko.person.service;

import com.senchenko.person.model.dto.PersonDto;
import com.senchenko.person.mapper.PersonMapper;
import com.senchenko.person.model.entity.Person;
import com.senchenko.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @Spy
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void testCreatePerson() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setAge(30);
        personDto.setId(id);

        Person person = new Person();
        person.setId(id);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setDateOfBirth(LocalDate.now().minusYears(30));

        when(personMapper.toPerson(personDto)).thenReturn(person);

        // When
        personService.create(personDto);

        // Then
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testFindPersonById() {
        // Given
        UUID id = UUID.randomUUID();
        Person person = new Person();
        person.setId(id);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setDateOfBirth(LocalDate.now().minusYears(30));

        PersonDto personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setAge(30);
        personDto.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personMapper.toPersonDto(person)).thenReturn(personDto);

        // When
        PersonDto result = personService.findById(id);

        // Then
        assertEquals(personDto, result);
    }

    @Test
    public void testFindAllPersons() {
        // Given
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        persons.add(new Person());

        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(new PersonDto());
        personDtos.add(new PersonDto());

        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.toPersonDto(any(Person.class))).thenReturn(new PersonDto());

        // When
        List<PersonDto> result = personService.findAll();

        // Then
        assertEquals(personDtos.size(), result.size());
    }

    @Test
    public void testUpdatePerson() {
        // Given
        UUID id = UUID.randomUUID();
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setAge(30);
        personDto.setId(id);

        Person updatedPerson = new Person();
        updatedPerson.setId(id);
        updatedPerson.setFirstName("Jane");
        updatedPerson.setLastName("Doe");
        updatedPerson.setDateOfBirth(LocalDate.now().minusYears(30));

        when(personRepository.findById(id)).thenReturn(Optional.of(updatedPerson));

        // When
        PersonDto result = personService.update(id, personDto);

        // Then
        assertEquals(personDto, result);
        verify(personRepository, times(1)).save(updatedPerson);
    }

    @Test
    public void testDeletePerson() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        personService.delete(id);

        // Then
        verify(personRepository, times(1)).deleteById(id);
    }

}
