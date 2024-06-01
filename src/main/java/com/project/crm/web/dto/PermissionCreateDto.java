package com.project.crm.web.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionCreateDto {
    @Size(max=50)
    private String name;
    private List<String> roles;
}
