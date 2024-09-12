package com.br.javaCrud.infrastructure.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.br.javaCrud.core.domain.Contact;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ContactSpecification implements Specification<Contact> {
    private final String search;
    private final String[] fields;

    public ContactSpecification(String search, String[] fields) {
        this.search = search;
        this.fields = fields;
    }

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(search) && fields != null) {
            for (String field : fields) {
                switch (field) {
                    case "name":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),
                                "%" + search.toUpperCase() + "%"));
                        break;
                    case "description":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("description")),
                                "%" + search.toUpperCase() + "%"));
                        break;
                }
            }
        }
        return criteriaBuilder.or(predicates.stream().toArray(Predicate[]::new));
    }
}
