package com.florian.projet.manager;

import com.florian.projet.model.PersonMachine;

import java.util.List;

public class PersonMachineManager {
    private List<PersonMachine> personMachineList;
    private static PersonMachineManager instance;

    private PersonMachineManager() {

    }

    public static PersonMachineManager getInstance() {
        if(instance == null) {
            instance = new PersonMachineManager();
        }
        return instance;
    }

    public List<PersonMachine> getPersonMachineList() {
        return personMachineList;
    }

    public void setPersonMachineList(List<PersonMachine> volumePersonMachineList) {
        this.personMachineList = volumePersonMachineList;
    }
}
