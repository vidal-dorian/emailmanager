package fr.iutrodez.emailmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {

    @GetMapping("/")
    public String listPersons() {
        // Pas besoin de mettre 'list.html', il comprend tout seul le '.html'
        return "list";
    }

}