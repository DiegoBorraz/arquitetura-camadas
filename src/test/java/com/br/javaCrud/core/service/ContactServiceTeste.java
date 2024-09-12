package com.br.javaCrud.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.br.javaCrud.infrastructure.specification.ContactSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.javaCrud.core.domain.Contact;
import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.repository.Contact.ContactRepository;
import com.br.javaCrud.core.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.core.service.Contact.ContactServiceImpl;
import com.br.javaCrud.core.util.Dates;
import com.br.javaCrud.infrastructure.exceptions.CustomException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTeste {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    @Mock
    private ProfessionalRepository professionalRepository;

    private Long ID = 1l;
    private String name = "Telefone";
    private String description = "53991870300";
    private Date createdDate = Dates.convertoToDate(2024, 8, 26);
    private Long professionalId = 1l;

    private Contact contact;
    private Contact newContact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Professional professional = new Professional();
        professional.setId(professionalId);
        contact = new Contact();
        contact.setId(ID);
        contact.setName(name);
        contact.setDescription(description);
        contact.setCreatedDate(createdDate);
        contact.setProfessional(professional);

        newContact = new Contact();
        newContact.setName("watsapp");
        newContact.setDescription("53225588");
        newContact.setProfessional(professional);
    }

    @Test
    public void testListProfessionalNoFilter() {
        String search = "tel";
        String[] fields = { "name", "description" };
        Pageable pageable = PageRequest.of(0, 100, Sort.by("name"));
        List<Contact> expeContactList = new ArrayList<>();
        expeContactList.add(contact);

        Mockito.when(contactRepository.findAll(any(ContactSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(expeContactList, pageable, expeContactList.size()));
        List<Contact> actualContacts = contactServiceImpl.listContact(search, fields,0,100,"name");
        assertEquals(expeContactList, actualContacts);

        Mockito.when(contactRepository.findAll()).thenReturn(expeContactList);
        actualContacts = contactServiceImpl.listContact("", fields,0,100,"name");
        assertEquals(expeContactList, actualContacts);
    }

    @Test
    void mustSaveAndSearchContactById() {
        Optional<Professional> optionalProfessional = professionalRepository
                .findById(newContact.getProfessional().getId());
        if (optionalProfessional.isPresent()) {
            Contact contactExpected = contactServiceImpl.insertContact(newContact.getName(),
                    newContact.getDescription(), professionalId);
            Optional<Contact> optionalContact = contactRepository.findById(newContact.getId());

            if (optionalContact.isPresent()) {
                Contact contactSaved = optionalContact.get();
                assertEquals(contactExpected.getName(), contactSaved.getName());
            } else {
                assertThrows(CustomException.class, () -> {
                    throw new CustomException("Contact not entered.");
                });
            }
        } else {
            assertThrows(CustomException.class, () -> {
                throw new CustomException("Professional with id (" + newContact.getProfessional().getId()
                        + ") not found for contact insertion");
            });
        }

    }

    @Test
    void mustUpdateContactComSucesso() {
        String newName = "Telefone residencial";
        String newDescription = "5332266998";
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        Contact contactUpdated = contactServiceImpl.updateContact(contact.getId(), newName, newDescription);
        verify(contactRepository).save(contact);

        if (contactUpdated != null) {
            assertEquals(newName, contactUpdated.getName());
            assertEquals(newDescription, contactUpdated.getDescription());
        } else {
            assertThrows(CustomException.class, () -> {
                throw new CustomException("Contact cannot be updated.");
            });
        }
    }

    @Test
    void mustDeleteContact() {
        contactServiceImpl.deleteContact(contact.getId());
    }
}
