package com.devsuperior.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.devsuperior.Dto.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.entities.Client;
import com.devsuperior.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final ClientRepository clientRepository;
	private final ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public ClientDto findById(Long id) {
		Optional<Client> clientOptional = clientRepository.findById(id);
		Client client = clientOptional.orElseThrow(NoSuchElementException::new);
        return modelMapper.map(client, ClientDto.class);
	}

	@Transactional(readOnly = true)
	public Page<ClientDto> findAll(Pageable pageable) {
		Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(client -> modelMapper.map(client, ClientDto.class));
	}

	public ClientDto insert(ClientDto clientDto) {
		Client client = modelMapper.map(clientDto, Client.class);
		Client clientSaved = clientRepository.save(client);
		return modelMapper.map(clientSaved, ClientDto.class);
	}

	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
}
