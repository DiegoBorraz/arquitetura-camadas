package com.br.javaCrud.service.Professional;

import java.util.List;

import com.br.javaCrud.dto.ProfessionalDTO;
import com.br.javaCrud.record.ProfessionalRecord;

public interface ProfessionalService {
    List<ProfessionalDTO> listProfessional(String search, String[] fields, int page, int size, String sort);

    ProfessionalDTO searchProfessionalById(Long id);

    ProfessionalDTO insertProfessional(ProfessionalRecord professionalRecord);

    ProfessionalDTO updateProfessional(Long id, ProfessionalRecord professionalRecord);

    void deleteProfessional(Long id);
}
