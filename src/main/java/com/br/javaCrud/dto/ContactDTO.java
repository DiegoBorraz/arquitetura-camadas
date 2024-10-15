package com.br.javaCrud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDTO {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    private LocalDateTime createdDate;
    @NotNull(message = "professional is required")
    @Min(value = 1, message = "It is not a valid id for the professional")
    private Long professionalId;

}
