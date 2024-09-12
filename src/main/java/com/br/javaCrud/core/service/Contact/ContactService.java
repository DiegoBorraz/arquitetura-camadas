package com.br.javaCrud.core.service.Contact;

import java.util.List;

import com.br.javaCrud.core.domain.Contact;

public interface ContactService {
    List<Contact> listContact(String search, String[] fields, int page, int size, String sort);

    Contact searchContactById(Long id);

    Contact insertContact(String name, String description, Long professionalId);

    Contact updateContact(Long id, String name, String description);

    void deleteContact(Long id);

    List<Contact> contactsByProfessionalId(Long id);
}
