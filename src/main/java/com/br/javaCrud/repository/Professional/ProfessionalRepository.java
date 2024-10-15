package com.br.javaCrud.repository.Professional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.javaCrud.domain.Professional;

@Repository
public interface ProfessionalRepository
        extends CrudRepository<Professional, Long>, JpaSpecificationExecutor<Professional> {

    Professional findByNameIgnoringCase(String nome);

    @Override
    Page<Professional> findAll(Specification<Professional> spec, Pageable pageable);

    List<Professional> findAll();
}
