package com.project.crm.persistence.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_role")
@ApiModel(description = "Role")
public class Role extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "feature")
    private String feature;
    @ManyToMany(mappedBy= "roles")
    private List<Permission> offres = new ArrayList<>();


}
