package com.nubank.desafio_backend.repository;

import com.nubank.desafio_backend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}