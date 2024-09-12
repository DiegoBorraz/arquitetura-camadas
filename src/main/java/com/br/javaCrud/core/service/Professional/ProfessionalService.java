package com.br.javaCrud.core.service.Professional;

import java.util.Date;
import java.util.List;

import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.enums.Role;

public interface ProfessionalService {
    List<Professional> listProfessional(String search, String[] fields, int page, int size, String sort);

    Professional searchProfessionalById(Long id);

    Professional insertProfessional(String nome, Role cargo, Date nascimento);

    Professional updateProfessional(Long id, String nome, Role cargo, Date nascimento);

    void deleteProfessional(Long id);
}
