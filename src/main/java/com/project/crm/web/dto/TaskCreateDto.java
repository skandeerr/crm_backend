package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreateDto {
    @Size(max=50)
    private String name;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Size(max=50)
    private String statusTask;
    @Size(max=50)
    private String typeActivity;
    @Size(max=50)
    private String description;
}
