package com.senchenko.person.service;


import com.senchenko.person.model.dto.PersonDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    /**
     * Creates a new PersonDto object in the system.
     *
     * @param personDto The PersonDto object to create.
     * @return The created PersonDto object.
     */
    PersonDto create(PersonDto personDto);

    /**
     * Retrieves a PersonDto object by its ID.
     *
     * @param id The ID of the PersonDto object to retrieve.
     * @return The retrieved PersonDto object.
     */
    PersonDto findById(UUID id);

    /**
     * Retrieves a list of all PersonDto objects in the system.
     *
     * @return A list of all PersonDto objects in the system.
     */
    List<PersonDto> findAll();

    /**
     * Updates an existing PersonDto object in the system.
     *
     * @param id        The ID of the PersonDto object to update.
     * @param personDto The updated PersonDto object.
     * @return The updated PersonDto object.
     */
    PersonDto update(UUID id, PersonDto personDto);

    /**
     * Deletes a PersonDto object from the system.
     *
     * @param id The ID of the PersonDto object to delete.
     */
    void delete(UUID id);

}
