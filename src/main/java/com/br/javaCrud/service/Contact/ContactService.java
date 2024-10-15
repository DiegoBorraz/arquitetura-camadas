package com.br.javaCrud.service.Contact;

import java.util.List;

import com.br.javaCrud.domain.Contact;
import com.br.javaCrud.dto.ContactDTO;
import com.br.javaCrud.record.ContactRecord;
import com.br.javaCrud.record.ContactUpdateRecord;

public interface ContactService {
    List<ContactDTO> listContact(String search, String[] fields, int page, int size, String sort);

    ContactDTO searchContactById(Long id);

    ContactDTO insertContact(ContactRecord contactRecord);

    ContactDTO updateContact(Long id, ContactUpdateRecord contactUpdateRecord);

    void deleteContact(Long id);

    List<ContactDTO> contactsByProfessionalId(Long id);
}
