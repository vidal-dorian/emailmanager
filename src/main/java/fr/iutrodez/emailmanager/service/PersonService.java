package fr.iutrodez.emailmanager.service;

import fr.iutrodez.emailmanager.model.Person;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private static final String FILE_PATH = "persons.json";
    private List<Person> persons;
    private ObjectMapper objectMapper;

    public PersonService() {
        objectMapper = new ObjectMapper();
        persons = readFromFile();
    }

    private List<Person> readFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            } else {
                return new ArrayList<>();
            }
        } catch (JacksonException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), persons);
        } catch (JacksonException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public Person getPersonById(int id) {
        return persons.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void addPerson(Person person) {
        person.setId(generateNewId());
        persons.add(person);
        writeToFile();
    }

    public void updatePerson(Person person) {
        int index = -1;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId() == person.getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            persons.set(index, person);
            writeToFile();
        }
    }

    public void deletePerson(int id) {
        persons.removeIf(p -> p.getId() == id);
        writeToFile();
    }

    private int generateNewId() {
        if (persons.isEmpty()) {
            return 1;
        } else {
            return persons.stream()
                    .mapToInt(Person::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

}
