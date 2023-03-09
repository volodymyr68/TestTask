package com.senchenko.person.service;

import com.senchenko.person.model.dto.PersonDto;
import com.senchenko.person.mapper.PersonMapper;
import com.senchenko.person.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private PersonMapper personMapper;

    @Override
    public PersonDto create(PersonDto personDto) {
        personRepository.save(personMapper.toPerson(personDto));
        return personDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findById(UUID id) {
        return personRepository.findById(id)
                .map(personMapper::toPersonDto)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toPersonDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto update(UUID id, PersonDto personDto) {
        val personUpdated = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        personMapper.updatePersonFromDto(personDto,personUpdated);
        personRepository.save(personUpdated);
        return personDto;
    }

    @Override
    public void delete(UUID id) {
        personRepository.deleteById(id);
    }
}
