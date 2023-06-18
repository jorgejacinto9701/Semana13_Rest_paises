package com.example.semana13.entity;

import java.util.List;

public class Pais {
    private String name;
    private String capital;
    private int population;

    private List<Currencie> currencies;

    private List<Currencie> languages;

    private Flag flags;

    public Flag getFlags() {
        return flags;
    }

    public void setFlags(Flag flags) {
        this.flags = flags;
    }

    public List<Currencie> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Currencie> languages) {
        this.languages = languages;
    }

    public List<Currencie> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencie> currencies) {
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
