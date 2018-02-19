package com.florian.projet.manager;

import com.florian.projet.model.Machine;
import com.florian.projet.model.Person;
import com.florian.projet.model.PersonMachine;

import java.util.ArrayList;
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
        personList = new ArrayList<>();
        Person person;
        Machine machine;
        PersonMachine personMachine;
        List<Machine> machineList = new ArrayList<>();
        List<PersonMachine> personMachineList;
        int j = 1;
        for (int i = 1; i < 10; i++) {

            person = new Person();

            for (; j < j + 3; j++) {
                machine = new Machine(j + "",18.1,1 + j,12.12);
                machineList.add(machine);

                personMachine = new PersonMachine(machine,person,123.12,8);

                personMachineList = PersonMachineManager.getInstance().getPersonMachineList();
                personMachineList.add(personMachine);
                PersonMachineManager.getInstance().setPersonMachineList(personMachineList);
            }

            person.setMachineList(machineList);
            personList.add(person);
            //TODO: Récupèration des données à partir du manager qui récupère du serveur
        }
    }
}
