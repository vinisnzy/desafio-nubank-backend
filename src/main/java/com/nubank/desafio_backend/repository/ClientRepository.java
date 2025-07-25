package com.nubank.desafio_backend.repository;

import com.nubank.desafio_backend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}