package com.devsuperior.controllers;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import com.devsuperior.Dto.ClientDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.entities.Client;
import com.devsuperior.services.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Validated
@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
		try {
			ClientDto clientDto = clientService.findById(id);
			return ResponseEntity.ok(clientDto);
		} catch (NoSuchElementException e) {
			throw new ErrorResponseException(HttpStatusCode.valueOf(404), e.getCause());
		} catch (Exception e) {
			throw new ErrorResponseException(HttpStatusCode.valueOf(500), e.getCause());
		}
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



	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Client>> deleteById(@PathVariable Long id) {
		clientService.deleteById(id);

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
	}

}
