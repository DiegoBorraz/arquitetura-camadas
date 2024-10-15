package com.br.javaCrud.service.Professional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.br.javaCrud.dto.ProfessionalDTO;
import com.br.javaCrud.record.ProfessionalRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.javaCrud.domain.Professional;
import com.br.javaCrud.repository.Contact.ContactRepository;
import com.br.javaCrud.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.config.exceptions.CustomException;
import com.br.javaCrud.config.specification.ProfessionalSpecification;

import io.micrometer.common.util.StringUtils;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProfessionalDTO> listProfessional(String search, String[] fields, int page, int size, String sort) {
        if (StringUtils.isNotEmpty(search) && fields != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            ProfessionalSpecification spec = new ProfessionalSpecification(search, fields);
            Page<Professional> professionais = professionalRepository.findAll(spec, pageable);
            return professionais.getContent().stream()
                    .map(professional -> modelMapper.map(professional, ProfessionalDTO.class))
                    .collect(Collectors.toList());
        }
        return professionalRepository.findAll().stream()
                .map(professional -> modelMapper.map(professional, ProfessionalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessionalDTO searchProfessionalById(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isPresent()) {
            return modelMapper.map(optionalProfessional.get(), ProfessionalDTO.class);
        }
        throw new CustomException("Professional not found.");
    }

    @Override
    public ProfessionalDTO insertProfessional(ProfessionalRecord professionalRecord) {
        Professional professional = null;
        professional = professionalRepository.findByNameIgnoringCase(professionalRecord.name());
        if (professional != null) {
            throw new CustomException("Professional " + professionalRecord.name() + " Already exists.");
        }
        professional = new Professional();
        professional.setName(professionalRecord.name());
        professional.setRole(professionalRecord.role());
        professional.setDateOfBirth(professionalRecord.dateOfBirth());
        professional.setCreatedDate(LocalDateTime.now());
        professional = professionalRepository.save(professional);
        return  modelMapper.map(professional,ProfessionalDTO.class);
    }

    @Override
    public ProfessionalDTO updateProfessional(Long id, ProfessionalRecord professionalRecord) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isPresent()) {
            Professional professionalExistente = optionalProfessional.get();
            professionalExistente.setName(professionalRecord.name());
            professionalExistente.setRole(professionalRecord.role());
            professionalExistente.setDateOfBirth(professionalRecord.dateOfBirth());
            professionalExistente = professionalRepository.save(professionalExistente);
            return  modelMapper.map(professionalExistente, ProfessionalDTO.class);
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
