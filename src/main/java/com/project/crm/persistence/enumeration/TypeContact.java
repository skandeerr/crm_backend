package com.project.crm.persistence.enumeration;

public enum TypeContact {
    Fournisseur("Fournisseur"),
    Partenaire("Partenaire"),
    Client("Client");

    private final String frenchTranslation;

    TypeContact(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }



}
