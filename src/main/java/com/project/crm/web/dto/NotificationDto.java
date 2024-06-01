package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Long id;
    @Size(max=50)
    private String NomTache;
    private Long taskId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Boolean isViewd;
}
