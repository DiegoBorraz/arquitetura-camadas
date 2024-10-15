package com.br.javaCrud.record;

import com.br.javaCrud.enums.Role;

import java.time.LocalDate;
import java.util.Date;

public record ProfessionalRecord(String name, Role role, LocalDate dateOfBirth) {
}
