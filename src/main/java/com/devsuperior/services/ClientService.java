package com.devsuperior.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsuperior.entities.Client;
import com.devsuperior.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final ClientRepository clientRepository;
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public Client findById(Long id) {
		return clientRepository.findById(id).orElseThrow();
	}
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
}
