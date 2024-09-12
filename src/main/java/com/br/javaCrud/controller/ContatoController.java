package com.br.javaCrud.controller;

import java.util.List;

import com.br.javaCrud.core.dto.UpdateContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.javaCrud.core.domain.Contact;
import com.br.javaCrud.core.dto.ContactDTO;
import com.br.javaCrud.core.service.Contact.ContactServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contact")
public class ContatoController {

    @Autowired
    private ContactServiceImpl contactServiceImpl;

    @Operation(description = "Filter contacts by name or contact.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns a list of contacts.") })
    @GetMapping
    public ResponseEntity<List<Contact>> listContact(
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(value = "fields", required = false) String[] fields,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "name") String sort) {
        List<Contact> listContact = contactServiceImpl.listContact(search, fields, page, size, sort);
        return ResponseEntity.ok(listContact);
    }

    @Operation(description = "Search for contact by ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns the contact by id.") })
    @GetMapping("/{id}")
    public ResponseEntity<Contact> buscarPorId(@PathVariable Long id) {
        Contact contact = contactServiceImpl.searchContactById(id);
        return ResponseEntity.ok(contact);
    }

    @Operation(description = "Insert a contact.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contact with id({id}) registered.") })
    @PostMapping
    public ResponseEntity<String> insertContact(@RequestBody ContactDTO contactDTO) {
        Contact contactSaved = contactServiceImpl.insertContact(contactDTO.name(), contactDTO.description(),
                contactDTO.professionalId());
        return ResponseEntity.ok("Contact with id " + contactSaved.getId() + " registered.");
    }

    @Operation(description = "Edit a contact by Id.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contact updated successfully.") })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContact(@PathVariable Long id, @RequestBody @Valid UpdateContactDTO updateContactDTO) {
        contactServiceImpl.updateContact(id, updateContactDTO.name(), updateContactDTO.description());
        return ResponseEntity.ok("Contact updated successfully.");
    }

    @Operation(description = "Deletes contact by Id.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contact deleted successfully.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        contactServiceImpl.deleteContact(id);
        return ResponseEntity.ok("Contact deleted successfully.");
    }

    @Operation(description = "List of contacts by professional ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns contacts that belong to a specific professional.") })
    @GetMapping("/professionais/{id}")
    public ResponseEntity<List<Contact>> contactByProfessionalId(@PathVariable Long id) {
        List<Contact> contatos = contactServiceImpl.contactsByProfessionalId(id);
        return ResponseEntity.ok(contatos);
    }
}
