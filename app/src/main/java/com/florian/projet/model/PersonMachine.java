package com.florian.projet.model;

public class PersonMachine {
    private Machine machine;
    private Person person;
    private double volume;
    private int nbHour;

    public PersonMachine(Machine machine, Person person, double volume, int nbHour) {
        this.machine = machine;
        this.person = person;
        this.volume = volume;
        this.nbHour = nbHour;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getNbHour() {
        return nbHour;
    }

    public void setNbHour(int nbHour) {
        this.nbHour = nbHour;
    }
}
