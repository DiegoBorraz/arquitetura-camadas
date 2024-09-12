package com.br.javaCrud.infrastructure.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.br.javaCrud.core.domain.Professional;
import com.br.javaCrud.infrastructure.exceptions.CustomException;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProfessionalSpecification implements Specification<Professional> {
    private final String search;
    private final String[] fields;

    public ProfessionalSpecification(String search, String[] fields) {
        this.search = search;
        this.fields = fields;
    }

    @Override
    public Predicate toPredicate(Root<Professional> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(search) && fields != null) {
            for (String field : fields) {
                switch (field) {
                    case "name":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),
                                "%" + search.toUpperCase() + "%"));
                        break;
                    case "role":
                        predicates.add(
                                criteriaBuilder.like(criteriaBuilder.upper(root.get("role")),
                                        "%" + search.toUpperCase() + "%"));
                        break;
                    case "dateOfBirth":
                        try {
                            LocalDate parsedDate = LocalDate.parse(search);
                            LocalDateTime startOfDay = parsedDate.atStartOfDay();
                            LocalDateTime endOfDay = parsedDate.plusDays(1).atStartOfDay();
                            predicates.add(criteriaBuilder.between(root.get("dateOfBirth"), startOfDay, endOfDay));
                        } catch (DateTimeParseException e) {
                            throw new CustomException("Date of birth is invalid for use as a filter.");
                        }
                        break;
                }
            }

        }
        return criteriaBuilder.or(predicates.stream().toArray(Predicate[]::new));

    }
}
