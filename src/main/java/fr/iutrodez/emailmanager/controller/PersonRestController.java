package fr.iutrodez.emailmanager.controller;

import fr.iutrodez.emailmanager.exception.PersonNotFoundException;
import fr.iutrodez.emailmanager.model.Person;
import fr.iutrodez.emailmanager.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/personnes")
public class PersonRestController {

    private final PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        return ResponseEntity.ok(findPersonOrThrow(id));
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws IOException {
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> editPerson(@PathVariable int id,
                                             @RequestBody Person person) throws IOException {
        findPersonOrThrow(id);
        person.setId(id);
        personService.updatePerson(person);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) throws IOException {
        findPersonOrThrow(id);
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    private Person findPersonOrThrow(int id) {
        Person person = personService.getPersonById(id);
        if (person == null) {
            throw new PersonNotFoundException(id);
        }
        return person;
    }
}