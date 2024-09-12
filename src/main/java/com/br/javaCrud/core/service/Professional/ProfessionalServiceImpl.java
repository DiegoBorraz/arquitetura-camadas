package com.br.javaCrud.core.service.Professional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.enums.Role;
import com.br.javaCrud.core.repository.Contact.ContactRepository;
import com.br.javaCrud.core.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.infrastructure.exceptions.CustomException;
import com.br.javaCrud.infrastructure.specification.ProfessionalSpecification;

import io.micrometer.common.util.StringUtils;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Professional> listProfessional(String search, String[] fields, int page, int size, String sort) {
        if (StringUtils.isNotEmpty(search) && fields != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            ProfessionalSpecification spec = new ProfessionalSpecification(search, fields);
            Page<Professional> professionais = professionalRepository.findAll(spec, pageable);
            return professionais.getContent();
        }
        return professionalRepository.findAll();
    }

    @Override
    public Professional searchProfessionalById(Long id) {
        Optional<Professional> opcionalProfessional = professionalRepository.findById(id);
        if (opcionalProfessional.isPresent()) {
            return opcionalProfessional.get();
        }
        throw new CustomException("Professional not found.");
    }

    @Override
    public Professional insertProfessional(String name, Role role, Date DateOfBirth) {
        Professional professional = null;
        professional = professionalRepository.findByNameIgnoringCase(name);
        if (professional != null) {
            throw new CustomException("Professional " + name + " Already exists.");
        }
        professional = new Professional();
        professional.setName(name);
        professional.setRole(role);
        professional.setDateOfBirth(DateOfBirth);
        professional.setCreatedDate(new Date());
        return professionalRepository.save(professional);
    }

    @Override
    public Professional updateProfessional(Long id, String name, Role role, Date dateOfBirth) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isPresent()) {
            Professional professionalExistente = optionalProfessional.get();
            professionalExistente.setName(name);
            professionalExistente.setRole(role);
            professionalExistente.setDateOfBirth(dateOfBirth);
            return professionalRepository.save(professionalExistente);
        }
        throw new CustomException("Professional id (" + id + ") not found for editing.");

    }

    @Override
    public void deleteProfessional(Long id) {
        if (contactRepository.existsByProfessional_Id(id)) {
            throw new CustomException(
                    "Professional de  id (" + id + ") não pode ser excluido pois possui contatos vinculados.");
        } else {
            professionalRepository.deleteById(id);
        }
    }

}
