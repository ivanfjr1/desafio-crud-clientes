package com.devsuperior.services;

import com.devsuperior.dto.ClientDto;
import com.devsuperior.services.exceptions.DatabaseException;
import com.devsuperior.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.entities.Client;
import com.devsuperior.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final ClientRepository clientRepository;
	private final ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public ClientDto findById(Long id) {
		Client client = clientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado")
		);
        return modelMapper.map(client, ClientDto.class);
	}

	@Transactional(readOnly = true)
	public Page<ClientDto> findAll(Pageable pageable) {
		Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(client -> modelMapper.map(client, ClientDto.class));
	}

	@Transactional
	public ClientDto insert(ClientDto clientDto) {
		Client client = modelMapper.map(clientDto, Client.class);
		Client clientSaved = clientRepository.save(client);
		return modelMapper.map(clientSaved, ClientDto.class);
	}

	@Transactional
	public ClientDto update(Long id, ClientDto clientDto) {
		try {
			Client client = clientRepository.getReferenceById(id);
			copyDtoToEntity(clientDto, client);
			Client clientUpdated = clientRepository.save(client);
			return modelMapper.map(clientUpdated, ClientDto.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			clientRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private static void copyDtoToEntity(ClientDto clientDto, Client client) {
		client.setName(clientDto.getName());
		client.setChildren(clientDto.getChildren());
		client.setCpf(clientDto.getCpf());
		client.setIncome(clientDto.getIncome());
		client.setBirthDate(clientDto.getBirthDate());
	}
}
