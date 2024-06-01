package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProspectCreateDto {
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
    private String statusProspect;
    private Long telephone;
}
