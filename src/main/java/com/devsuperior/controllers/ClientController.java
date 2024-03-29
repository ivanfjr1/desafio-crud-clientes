package com.devsuperior.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.entities.Client;
import com.devsuperior.services.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> save(@RequestBody Client client) {
		Client clientSaved = clientService.save(client);

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

}
