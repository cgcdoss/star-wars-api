package com.cgcdoss.starwars.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.cgcdoss.starwars.api.entities.Planeta;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PlanetaUtils {

	public static List<Planeta> preparaQtdFilmes(Logger log) {
		List<Planeta> planetasComFilmes = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			try {
				String url = "https://swapi.dev/api/planets/?page=" + i;

				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					log.info("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output = "";
				String line;
				while ((line = br.readLine()) != null) {
					output += line;
				}

				conn.disconnect();

				JsonElement elem = JsonParser.parseString(output);
				int count = elem.getAsJsonObject().get("count").getAsInt();
				int qtdFilmesPag = elem.getAsJsonObject().get("results").getAsJsonArray().size();
				log.info("Página " + i + " de " + count / qtdFilmesPag + " da API do Star Wars");

				elem.getAsJsonObject().get("results").getAsJsonArray().forEach(p -> {
					planetasComFilmes.add(new Planeta(p.getAsJsonObject().get("name").getAsString(),
							p.getAsJsonObject().get("films").getAsJsonArray().size()));
				});

			} catch (IOException ex) {
				log.error(ex.getMessage());
			}

		}
		return planetasComFilmes;
	}

}
