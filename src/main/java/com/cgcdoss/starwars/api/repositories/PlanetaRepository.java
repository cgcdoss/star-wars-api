package com.cgcdoss.starwars.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cgcdoss.starwars.api.entities.Planeta;

public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

	@Transactional(readOnly = true)
	Optional<Planeta> findById(Long id);

	@Transactional(readOnly = true)
	Optional<Planeta> findByNome(String nome);

}
