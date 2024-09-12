package com.br.javaCrud.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.br.javaCrud.core.domain.Contact;
import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.enums.Role;
import com.br.javaCrud.core.repository.Contact.ContactRepository;
import com.br.javaCrud.core.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.core.service.Professional.ProfessionalServiceImpl;
import com.br.javaCrud.core.util.Dates;
import com.br.javaCrud.infrastructure.exceptions.CustomException;
import com.br.javaCrud.infrastructure.specification.ProfessionalSpecification;

@ExtendWith(MockitoExtension.class)
public class ProfessionalServiceTeste {

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ProfessionalServiceImpl professionalServiceImpl;

    private Long ID = 1l;
    private String nome = "Diego";
    private Role role = Role.DEVELOPER;
    private Date dateOfBirth = Dates.convertoToDate(1986, 012, 7);
    private Date createdDate = Dates.convertoToDate(2024, 8, 26);

    private Professional professional;
    private Professional newProfessional;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        professional = new Professional();
        professional.setId(ID);
        professional.setName(nome);
        professional.setRole(role);
        professional.setDateOfBirth(dateOfBirth);
        professional.setCreatedDate(createdDate);

        newProfessional = new Professional();
        newProfessional.setName("Ronaldo Nazario");
        newProfessional.setRole(Role.DESIGNER);
        newProfessional.setDateOfBirth(Dates.convertoToDate(1976, 7, 18));

    }

    @Test
    public void testListProfessionalNoFilter() {
        String search = "die";
        String[] fields = { "name", "role" };
        String[] fieldsDateOfBirth = { "dateOfBirth" };
        Pageable pageable = PageRequest.of(0, 100, Sort.by("name"));


        List<Professional> expectedProfessionals = new ArrayList<Professional>();
        expectedProfessionals.add(professional);

        Mockito.when(professionalRepository.findAll(any(ProfessionalSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(expectedProfessionals, pageable, expectedProfessionals.size()));
        List<Professional> actualProfessionals = professionalServiceImpl.listProfessional(search, fields, 0, 100,
                "name");
        assertEquals(expectedProfessionals, actualProfessionals);

        Mockito.when(professionalRepository.findAll()).thenReturn(expectedProfessionals);
        actualProfessionals = professionalServiceImpl.listProfessional("", fields, 0, 100, "name");
        assertEquals(expectedProfessionals, actualProfessionals);
    }

    @Test
    void mustSaveAndSearchProfessionalById() {
        Professional professionalExpected = professionalServiceImpl.insertProfessional(newProfessional.getName(),
                newProfessional.getRole(), newProfessional.getDateOfBirth());
        Optional<Professional> optionalProfessional = professionalRepository.findById(newProfessional.getId());

        if (optionalProfessional.isPresent()) {
            Professional professionalSaved = optionalProfessional.get();
            assertEquals(professionalExpected.getName(), professionalSaved.getName());
        } else {
            assertThrows(CustomException.class, () -> {
                throw new CustomException("Professional not included");
            });
        }
    }

    @Test
    void MustUpdateProfessionalSuccessfully() {
        String newName = "CLEBER";
        Role newRole = Role.SUPPORT;
        Date newDateOfBirth = Dates.convertoToDate(1988, 5, 10);

        when(professionalRepository.findById(professional.getId())).thenReturn(Optional.of(professional));
        Professional professionalUpdated = professionalServiceImpl.updateProfessional(professional.getId(),
                newName, newRole, newDateOfBirth);

        verify(professionalRepository).save(professional);
        if (professionalUpdated != null) {
            assertEquals(newName, professionalUpdated.getName());
            assertEquals(newRole, professionalUpdated.getRole());
            assertEquals(newDateOfBirth, professionalUpdated.getDateOfBirth());
        } else {
            assertThrows(CustomException.class, () -> {
                throw new CustomException("Professional cannot be upgraded.");
            });
        }
    }

    @Test
    void mustDeleteProfessional() {
        Long idToDelete = 1L;
        List<Contact> listContact = contactRepository.findByProfessional_Id(idToDelete);
        if (listContact.isEmpty()) {
            professionalServiceImpl.deleteProfessional(idToDelete);
            verify(professionalRepository).deleteById(idToDelete);
        } else {
            assertThrows(CustomException.class, () -> {
                throw new CustomException("Professional cannot be deleted as it has linked contacts.");
            });
        }
    }

}
