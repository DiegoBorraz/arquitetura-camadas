package com.br.javaCrud.controller;

import java.util.List;

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

import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.core.dto.ProfessionalDTO;
import com.br.javaCrud.core.service.Professional.ProfessionalServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalServiceImpl professionalServiceImpl;

    @Operation(description = "Filter professionals by name, position or date of birth.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns a list of professionals.") })
    @GetMapping
    public ResponseEntity<List<Professional>> listProfessional(
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(value = "fields", required = false) String[] fields,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "name") String sort) {
        List<Professional> listProfessional = professionalServiceImpl.listProfessional(search, fields, page, size,
                sort);
        return ResponseEntity.ok(listProfessional);
    }

    @Operation(description = "Search for the professional by ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns the professional by id.") })
    @GetMapping("/{id}")
    public ResponseEntity<Professional> searchProfessionalById(
            @PathVariable Long id) {

        Professional professional = professionalServiceImpl.searchProfessionalById(id);
        return ResponseEntity.ok(professional);
    }

    @Operation(description = "Insert a professional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. Professional with id({id}) registered.") })
    @PostMapping
    public ResponseEntity<String> insertProfessional(@RequestBody @Valid ProfessionalDTO professional) {
        Professional professionalSaved = professionalServiceImpl.insertProfessional(professional.name(),
                professional.role(), professional.dateOfBirth());
        return ResponseEntity.ok("Success. Professional with id (" + professionalSaved.getId() + ") registered.");
    }

    @Operation(description = "Edit the professional by Id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Professional updated successfully.") })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfessional(@PathVariable Long id,
            @RequestBody @Valid ProfessionalDTO professional) {
        professionalServiceImpl.updateProfessional(id, professional.name(), professional.role(),
                professional.dateOfBirth());
        return ResponseEntity.ok("Professional updated successfully.");
    }

    @Operation(description = "Deletes the professional by ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Professional success excluded.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfessional(@PathVariable Long id) {
        professionalServiceImpl.deleteProfessional(id);
        return ResponseEntity.ok("Professional successfully deleted.");
    }
}
