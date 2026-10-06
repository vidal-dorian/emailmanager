package fr.iutrodez.emailmanager.controller;

import fr.iutrodez.emailmanager.model.Person;
import fr.iutrodez.emailmanager.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonController {

    // Déclaration du service
    private final PersonService personService;

    // Injection du service dans le controller par défaut
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String listPersons(Model model) {
        // Récupère toutes les personnes depuis le service
        List<Person> listePersonnes = this.personService.getAllPersons();

        // C'est un ensemble clé - valeur, la clé doit être la meme que dans
        // la vue
        model.addAttribute("persons", listePersonnes);

        // Pas besoin de mettre 'list.html', il comprend tout seul le '.html'
        return "list";
    }

}