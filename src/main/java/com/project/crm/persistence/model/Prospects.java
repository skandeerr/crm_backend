package com.project.crm.persistence.model;

import com.project.crm.persistence.enumeration.Priorite;
import com.project.crm.persistence.enumeration.StatusProspect;
import com.project.crm.persistence.enumeration.TypeContact;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_prospects")
@ApiModel(description = "Prospects")
public class Prospects extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;
    @Column(name = "note")
    private String note;

    @Column(name = "adresse")
    private String adresse;
    @Column(name = "email")
    private String email;
    @Column(name = "type_contact")
    private StatusProspect statusProspect;

    @Column(name = "tel_num")
    private Long telephone;
}
