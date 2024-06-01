package com.project.crm.persistence.enumeration;

public enum TypeActivity {
    REUNION("Réunion"),
    APPEL_TELEPHONIQUE("Appel Téléphonique"),
    RESUME_APPEL("Résumé de l’appel"),
    DESCRIPTION("Description");
    private final String frenchTranslation;

    TypeActivity(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
