package com.cgcdoss.starwars.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cgcdoss.starwars.api.entities.Planeta;

@Repository
public interface PlanetaRepository {

	Planeta save(Planeta planeta);

	List<Planeta> findAll();

	long deleteById(String id);

	public long deleteAll();

	Optional<Planeta> findById(String id);

	Optional<Planeta> findByNome(String nome);

}
