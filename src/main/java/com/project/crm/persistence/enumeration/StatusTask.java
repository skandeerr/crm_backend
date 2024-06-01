package com.project.crm.persistence.enumeration;

public enum StatusTask {
    EN_ATTENTE("En attente"),
    FAIT("Fait");

    private final String frenchTranslation;

    StatusTask(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
