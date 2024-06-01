package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    @Size(max=50)
    private String name;
}
