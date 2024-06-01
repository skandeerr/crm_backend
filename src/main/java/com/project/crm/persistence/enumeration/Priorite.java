package com.project.crm.persistence.enumeration;

public enum Priorite {
    Faible("Faible"),
    Moyenne("Moyenne"),
    élevée("élevée");

    private final String frenchTranslation;

    Priorite(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
