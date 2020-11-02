package com.cgcdoss.starwars.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cgcdoss.starwars.api.entities.Planeta;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanetaRepositoryTest {
	
	@Autowired
	private PlanetaRepository planetaRepository;
	
	private static final String NOME = "Terra";
	private String id = "1";
	

	@Before
	public void setUp() throws Exception {
		Planeta planeta = new Planeta();
		planeta.setNome("Terra");
		planeta.setClima("Temperado, Tropical");
		planeta.setTerreno("Florestas, Mares");
		id = this.planetaRepository.save(planeta).getId();
	}
	
	@After
    public final void tearDown() {
		this.planetaRepository.deleteAll();
	}

	@Test
	public void testBuscarPorId() {
		Planeta planeta = this.planetaRepository.findById(id).get();
		
		assertEquals(id, planeta.getId());
	}
	
	@Test
	public void testBuscarPorNome() {
		Planeta planeta = this.planetaRepository.findByNome(NOME).get();
		
		assertEquals(NOME, planeta.getNome());
	}

}