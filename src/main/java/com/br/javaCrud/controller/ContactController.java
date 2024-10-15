package com.br.javaCrud.controller;

import java.util.List;

import com.br.javaCrud.dto.ContactDTO;
import com.br.javaCrud.record.ContactRecord;
import com.br.javaCrud.record.ContactUpdateRecord;
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

import com.br.javaCrud.domain.Contact;
import com.br.javaCrud.service.Contact.ContactServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactServiceImpl contactServiceImpl;

    @Operation(description = "Filter contacts by name or contact.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns a list of contacts.") })
    @GetMapping
    public ResponseEntity<List<ContactDTO>> listContact(
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(value = "fields", required = false) String[] fields,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "name") String sort) {
        List<ContactDTO> listContactDTO = contactServiceImpl.listContact(search, fields, page, size, sort);
        return ResponseEntity.ok(listContactDTO);
    }

    @Operation(description = "Search for contact by ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns object of contact .") })
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> buscarPorId(@PathVariable Long id) {
        ContactDTO contactDTO = contactServiceImpl.searchContactById(id);
        return ResponseEntity.ok(contactDTO);
    }

    @Operation(description = "Insert a contact.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns object of contact .") })
    @PostMapping
    public ResponseEntity<ContactDTO> insertContact(@RequestBody @Valid ContactRecord contactRecord) {
        ContactDTO contactDTO = contactServiceImpl.insertContact(contactRecord);
        return ResponseEntity.ok(contactDTO);
    }

    @Operation(description = "Edit a contact by Id.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contact updated successfully.") })
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody @Valid ContactUpdateRecord contactUpdateRecord) {
       ContactDTO contactDTO =  contactServiceImpl.updateContact(id, contactUpdateRecord );
        return ResponseEntity.ok(contactDTO);
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
    public ResponseEntity<List<ContactDTO>> contactByProfessionalId(@PathVariable Long id) {
        List<ContactDTO> contatos = contactServiceImpl.contactsByProfessionalId(id);
        return ResponseEntity.ok(contatos);
    }
}
