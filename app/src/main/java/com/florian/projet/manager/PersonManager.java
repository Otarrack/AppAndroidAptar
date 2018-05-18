package com.florian.projet.manager;

import com.florian.projet.model.Person;

import java.util.List;

public class PersonManager {
    private static PersonManager instance;
    private List<Person> personList;

    private PersonManager() {
        setListSite();
    }

    public static PersonManager getInstance() {
        if(instance == null) {
            instance = new PersonManager();
        }
        return instance;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    private void setListSite() {

    }
}
