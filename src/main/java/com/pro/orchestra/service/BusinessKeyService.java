package com.pro.orchestra.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class BusinessKeyService {

    @PersistenceContext
    private EntityManager entityManager;

    public String generate(String processKey) {

        Long sequence = ((Number) entityManager
                .createNativeQuery("SELECT nextval('workflow_business_key_seq')")
                .getSingleResult())
                .longValue();

        return processKey.toUpperCase() + "-" + String.format("%08d", sequence);
    }
}