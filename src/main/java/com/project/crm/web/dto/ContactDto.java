package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {
    private Long id;
    @Size(max=50)
    private String nom;
    @Size(max=50)
    private String prenom;

    @Size(max=50)
    private String note;
    @Size(max=50)
    private String adresse;
    @Size(max=50)
    private String email;
    @Size(max=50)
    private String typeContact;
    @Size(max=50)
    private String priorite;
    private Long telephone;
}
