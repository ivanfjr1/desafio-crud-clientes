package com.devsuperior.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
