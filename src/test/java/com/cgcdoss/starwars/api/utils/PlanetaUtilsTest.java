package com.cgcdoss.starwars.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanetaUtilsTest {

	@Test
	public void testApi() {
		Logger log = LoggerFactory.getLogger(PlanetaUtilsTest.class);
		assertEquals(PlanetaUtils.preparaQtdFilmes(log).size(), 60);
	}

}
