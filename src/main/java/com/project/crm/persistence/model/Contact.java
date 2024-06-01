package com.project.crm.persistence.model;

import java.io.Serializable;

import com.project.crm.persistence.enumeration.Priorite;
import com.project.crm.persistence.enumeration.TypeContact;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_contact")
@ApiModel(description = "Contact")


public class Contact extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;
    @Column(name = "adresse")
    private String adresse;

    @Column(name = "note")
    private String note;
    @Column(name = "email")
    private String email;
    @Column(name = "type_contact")
    private TypeContact typeContact;
    @Column(name = "priorite")
    private Priorite priorite;
    @Column(name = "tel_num")
    private Long telephone;

}
