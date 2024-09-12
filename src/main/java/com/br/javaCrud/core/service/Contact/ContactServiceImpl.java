package com.br.javaCrud.core.service.Contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.javaCrud.core.domain.Contact;
import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.repository.Contact.ContactRepository;
import com.br.javaCrud.core.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.infrastructure.exceptions.CustomException;
import com.br.javaCrud.infrastructure.specification.ContactSpecification;

import io.micrometer.common.util.StringUtils;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Override
    public List<Contact> listContact(String search, String[] fields, int page, int size, String sort) {
        if (StringUtils.isNotEmpty(search) && fields != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            ContactSpecification spec = new ContactSpecification(search, fields);
            Page<Contact> contacts = contactRepository.findAll(spec, pageable);
            return contacts.getContent();
        }
        return contactRepository.findAll();
    }

    @Override
    public Contact searchContactById(Long id) {
        Optional<Contact> optionalContato = contactRepository.findById(id);
        if (optionalContato.isPresent()) {
            return optionalContato.get();
        }
        throw new CustomException("Contact not found.");
    }

    @Override
    public Contact insertContact(String name, String description, Long professionalId) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(professionalId);
        if (optionalProfessional.isPresent()) {
            Professional professional = optionalProfessional.get();
            Contact contact = new Contact();
            contact.setName(name);
            contact.setDescription(description);
            contact.setProfessional(professional);
            contact.setCreatedDate(new Date());
            return contactRepository.save(contact);
        }
        throw new CustomException("Professional does not exist.");
    }

    @Override
    public Contact updateContact(Long id, String name, String description) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contactExists = optionalContact.get();
            contactExists.setName(name);
            contactExists.setDescription(description);
            return contactRepository.save(contactExists);
        }
        throw new CustomException("Contact id (" + id + ") not found for editing.");
    }

    @Override
    public void deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new CustomException("Contact id (" + id + ") does not exist.");
        }
    }

    @Override
    public List<Contact> contactsByProfessionalId(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isPresent()) {
            return contactRepository.findByProfessional_Id(id);
        }
        throw new CustomException("Professional with invalid ID (" + id + ").");
    }
}
