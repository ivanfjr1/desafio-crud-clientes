package com.devsuperior.controllers;

import java.net.URI;
import java.time.Instant;
import java.util.NoSuchElementException;

import com.devsuperior.dto.ClientDto;
import com.devsuperior.dto.CustomError;
import com.devsuperior.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.services.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
		ClientDto clientDto = clientService.findById(id);
		return ResponseEntity.ok(clientDto);
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable) {
		Page<ClientDto> clientDtoPage = clientService.findAll(pageable);
		return ResponseEntity.ok(clientDtoPage);
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> insert(@Valid @RequestBody ClientDto clientDto) {
		ClientDto clientDtoInsert = clientService.insert(clientDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientDtoInsert.getId()).toUri();
		return ResponseEntity.created(uri).body(clientDtoInsert);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> update( @PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
		clientDto = clientService.update(id, clientDto);
		return ResponseEntity.ok(clientDto);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
