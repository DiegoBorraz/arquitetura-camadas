package com.br.javaCrud.dto;

import com.br.javaCrud.enums.Role;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfessionalDTO {
    private Long id;
    private String name;
    private Role role;
    private LocalDate dateOfBirth;
    private LocalDateTime createdDate;
}
