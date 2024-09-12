package com.br.javaCrud.core.dto;

import java.util.Date;

import com.br.javaCrud.core.enums.Role;

public record ProfessionalDTO(String name, Role role, Date dateOfBirth) {

}
