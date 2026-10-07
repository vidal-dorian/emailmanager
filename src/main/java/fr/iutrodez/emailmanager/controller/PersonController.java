package fr.iutrodez.emailmanager.controller;

import fr.iutrodez.emailmanager.model.Person;
import fr.iutrodez.emailmanager.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/")
class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable int id) throws IOException {
        personService.deletePerson(id);
        return "redirect:/";
    }

    @GetMapping("/view/{id}")
    public String viewPerson(@PathVariable int id, Model model) {
        Person person = personService.getPersonById(id);
        model.addAttribute("person", person);
        return "view";
    }

    @GetMapping("/add")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "add";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute Person person) throws IOException {
        personService.addPerson(person);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPersonForm(@PathVariable int id, Model model) {
        Person person = personService.getPersonById(id);
        model.addAttribute("person", person);
        return "edit";
    }

    @PostMapping("/edit")
    public String editPerson(@ModelAttribute Person person) throws IOException {
        personService.updatePerson(person);
        return "redirect:/";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex) {
        return "error_data";
    }
}
