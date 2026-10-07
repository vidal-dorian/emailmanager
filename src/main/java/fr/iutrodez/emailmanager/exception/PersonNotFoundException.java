package fr.iutrodez.emailmanager.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(int id) {
        super("Aucune personne ne correspond à l'identifiant " + id + ".");
    }
}