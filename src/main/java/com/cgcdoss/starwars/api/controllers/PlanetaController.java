package com.cgcdoss.starwars.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgcdoss.starwars.api.entities.Planeta;
import com.cgcdoss.starwars.api.repositories.PlanetaRepository;
import com.cgcdoss.starwars.api.utils.PlanetaUtils;

@RestController
@RequestMapping("/planeta")
@CrossOrigin(origins = "*")
public class PlanetaController {

	private static final Logger log = LoggerFactory.getLogger(PlanetaController.class);

	@Autowired
	private PlanetaRepository planetaRepository;

	List<Planeta> planetasComFilmes = new ArrayList<>();

	public PlanetaController(PlanetaRepository planetaRepository) {
		this.planetaRepository = planetaRepository;
		planetasComFilmes = PlanetaUtils.preparaQtdFilmes();
	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<Planeta>> cadastrar(@Valid @RequestBody Planeta planeta, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Cadastrando planeta: {}", planeta.toString());
		Response<Planeta> response = new Response<Planeta>();

		validaPlanetasExistentes(planeta, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro planeta: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.planetaRepository.save(planeta);

		response.setData(planeta);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Response<Planeta>> buscarPorId(@PathVariable("id") String id) {
		log.info("Buscando planetas por id: {}", id);
		Response<Planeta> response = new Response<Planeta>();
		Optional<Planeta> planeta = planetaRepository.findById(id);

		if (!planeta.isPresent()) {
			log.info("Planeta não encontrado para o id: {}", id);
			response.getErrors().add("Planeta não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		setQtdFilmes(planeta.get());

		response.setData(planeta.get());
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<Planeta>> buscarPorNome(@PathVariable("nome") String nome) {
		log.info("Buscando planetas por nome: {}", nome);
		Response<Planeta> response = new Response<Planeta>();
		Optional<Planeta> planeta = planetaRepository.findByNome(nome);

		if (!planeta.isPresent()) {
			log.info("Planeta não encontrado para o nome: {}", nome);
			response.getErrors().add("Planeta não encontrado para o nome '" + nome + "'");
			return ResponseEntity.badRequest().body(response);
		}

		setQtdFilmes(planeta.get());

		response.setData(planeta.get());
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<Planeta>> get() {
		List<Planeta> planetas = planetaRepository.findAll();

		planetas.forEach(planeta -> {
			setQtdFilmes(planeta);
		});

		return ResponseEntity.ok(planetas);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Optional<Planeta> planeta = planetaRepository.findById(id);

		if (!planeta.isPresent()) {
			response.getErrors().add("Planeta não existe aqui");
			return ResponseEntity.badRequest().body(response);
		}

		planetaRepository.deleteById(id);

		return ResponseEntity.ok(new Response<String>());
	}

	private void validaPlanetasExistentes(Planeta planeta, BindingResult result) {
		if (planetaRepository.findByNome(planeta.getNome()).isPresent())
			result.addError(new ObjectError("planeta", "Planeta com nome '" + planeta.getNome() + "' já existe"));
	}

	private void setQtdFilmes(Planeta planeta) {
		planeta.setQtdFilmes(planetasComFilmes.stream().filter(p -> p.getNome().equals(planeta.getNome()))
				.collect(Collectors.toList()).get(0).getQtdFilmes());
	}

}
