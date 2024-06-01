package com.project.crm.persistence.enumeration;

public enum StatusProspect {
    Nouveau("Nouveau"),
    Tentative("Tentative"),
    Qualifié("Qualifié"),
    NonQualifie("Non qualifié");

    private final String frenchTranslation;

    StatusProspect(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
