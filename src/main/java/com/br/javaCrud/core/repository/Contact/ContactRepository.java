package com.br.javaCrud.core.repository.Contact;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.javaCrud.core.domain.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    List<Contact> findByProfessional_Id(Long id);

    boolean existsByProfessional_Id(Long id);

    List<Contact> findAll();

    @Override
    Page<Contact> findAll(Specification<Contact> spec, Pageable pageable);

}
