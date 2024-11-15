package com.devsuperior.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import com.devsuperior.Dto.ClientDto;
import jakarta.validation.Valid;
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

@Validated
@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> save(@Valid @RequestBody ClientDto clientDto) {
		Client clientSaved = clientService.save(clientDto);

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(clientSaved, httpHeaders, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> getFindById(@PathVariable Long id) {
		try {
			Client client = clientService.findById(id);

			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return new ResponseEntity<>(client, httpHeaders, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			throw new ErrorResponseException(HttpStatusCode.valueOf(404), e.getCause());
		} catch (Exception e) {
			throw new ErrorResponseException(HttpStatusCode.valueOf(500), e.getCause());
		}
	}
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Client>> getAll() {
		List<Client> clients = clientService.findAll();
		
		final HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(clients, httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Client>> deleteById(@PathVariable Long id) {
		clientService.deleteById(id);

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
	}

}
