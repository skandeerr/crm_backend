package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDto {
    private Long id;
    @Size(max=50)
    private String name;
    private List<RoleDto> roles;
}
