package com.senchenko.person.controller;

import com.senchenko.person.model.dto.PersonDto;
import com.senchenko.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<PersonDto> findAll(){
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public PersonDto findById(@PathVariable UUID id) throws Exception {
        return personService.findById(id);
    }

    @PostMapping
    public PersonDto create(@RequestBody @Validated PersonDto personDto) {
        return  personService.create(personDto);
    }

    @PatchMapping("/{id}")
    public PersonDto update(@PathVariable UUID id, @RequestBody @Validated PersonDto personDto){
        return personService.update(id,personDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        personService.delete(id);
    }
}
