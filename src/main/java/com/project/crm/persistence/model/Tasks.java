package com.project.crm.persistence.model;

import com.project.crm.persistence.enumeration.StatusTask;
import com.project.crm.persistence.enumeration.TypeActivity;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_task")
@ApiModel(description = "Contact")
public class Tasks extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "status_task")
    private StatusTask statusTask;
    @Column(name = "type_activity")
    private TypeActivity typeActivity;

    @Column(name = "description")
    private String description;



}
