package com.cgcdoss.starwars.api.controllers;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cgcdoss.starwars.api.entities.Planeta;
import com.cgcdoss.starwars.api.repositories.PlanetaRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlanetaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PlanetaRepository planetaRepository;

	private static final String BUSCAR_PLANETA_ID_URL = "/planeta/id/";
	private static final String BUSCAR_PLANETA_NOME_URL = "/planeta/nome/";
	private static final String ID = "1";
	private static final String NOME = "Alderaan";
	private static final String CLIMA = "temperate";
	private static final String TERRENO = "grasslands, mountains";
	private static final Integer QTDFILMES = 2;
	
	@Test
	@WithMockUser
	public void testBuscarPlanetaPorId() throws Exception {
		BDDMockito.given(this.planetaRepository.findById(Mockito.anyString())).willReturn(Optional.of(this.obterDadosPlaneta()));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_PLANETA_ID_URL + ID).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.nome", equalTo(NOME)))
		.andExpect(jsonPath("$.data.clima", equalTo(CLIMA)))
		.andExpect(jsonPath("$.data.terreno", equalTo(TERRENO)))
		.andExpect(jsonPath("$.data.qtdFilmes", equalTo(QTDFILMES)))
		.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testBuscarPlanetaPorNome() throws Exception {
		BDDMockito.given(this.planetaRepository.findByNome(Mockito.anyString())).willReturn(Optional.of(this.obterDadosPlaneta()));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_PLANETA_NOME_URL + NOME).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.nome", equalTo(NOME)))
		.andExpect(jsonPath("$.data.clima", equalTo(CLIMA)))
		.andExpect(jsonPath("$.data.terreno", equalTo(TERRENO)))
		.andExpect(jsonPath("$.data.qtdFilmes", equalTo(QTDFILMES)))
		.andExpect(jsonPath("$.errors").isEmpty());
	}

	private Planeta obterDadosPlaneta() {
		Planeta planeta = new Planeta();
		planeta.setId(ID);
		planeta.setNome(NOME);
		planeta.setClima(CLIMA);
		planeta.setTerreno(TERRENO);
		planeta.setQtdFilmes(QTDFILMES);
		return planeta;
	}

}
