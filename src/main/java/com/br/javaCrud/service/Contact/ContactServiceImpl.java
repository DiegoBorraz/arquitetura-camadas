package com.br.javaCrud.service.Contact;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.br.javaCrud.dto.ContactDTO;
import com.br.javaCrud.record.ContactRecord;
import com.br.javaCrud.record.ContactUpdateRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.javaCrud.domain.Contact;
import com.br.javaCrud.domain.Professional;
import com.br.javaCrud.repository.Contact.ContactRepository;
import com.br.javaCrud.repository.Professional.ProfessionalRepository;
import com.br.javaCrud.config.exceptions.CustomException;
import com.br.javaCrud.config.specification.ContactSpecification;

import io.micrometer.common.util.StringUtils;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ContactDTO> listContact(String search, String[] fields, int page, int size, String sort) {
        if (StringUtils.isNotEmpty(search) && fields != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            ContactSpecification spec = new ContactSpecification(search, fields);
            Page<Contact> contacts = contactRepository.findAll(spec, pageable);
            return contacts.getContent().stream()
                    .map(contact -> modelMapper.map(contact, ContactDTO.class))
                    .collect(Collectors.toList());
        }
        return contactRepository.findAll().stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO searchContactById(Long id) {
        Optional<Contact> optionalContato = contactRepository.findById(id);
        if (optionalContato.isPresent()) {
            return modelMapper.map(optionalContato.get(),ContactDTO.class);
        }
        throw new CustomException("Contact not found.");
    }

    @Override
    public ContactDTO insertContact(ContactRecord contactRecord) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(contactRecord.professionalId());
        if (optionalProfessional.isPresent()) {
            Professional professional = optionalProfessional.get();
            Contact contact = new Contact();
            contact.setName(contactRecord.name());
            contact.setDescription(contactRecord.description());
            contact.setProfessional(professional);
            contact.setCreatedDate(LocalDateTime.now());
            contact =  contactRepository.save(contact);
            return modelMapper.map(contact, ContactDTO.class);
        }
        throw new CustomException("Professional does not exist.");
    }

    @Override
    public ContactDTO updateContact(Long id, ContactUpdateRecord contactUpdateRecord) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contactExists = optionalContact.get();
            contactExists.setName(contactUpdateRecord.name());
            contactExists.setDescription(contactUpdateRecord.description());
            contactExists = contactRepository.save(contactExists);
            return modelMapper.map(contactExists, ContactDTO.class);
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
    public List<ContactDTO> contactsByProfessionalId(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isPresent()) {
            List<Contact> contactList = contactRepository.findByProfessional_Id(id);
            return contactList.stream()
                    .map(contact -> modelMapper.map(contact, ContactDTO.class))
                    .collect(Collectors.toList());
        }
        throw new CustomException("Professional with invalid ID (" + id + ").");
    }
}
