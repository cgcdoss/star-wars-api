package com.cgcdoss.starwars.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Entity
public class Planeta {

	@Id
	private String id;
	private String nome;
	private String clima;
	private String terreno;

	@Transient
	@JsonSerialize
	@JsonDeserialize
	private Integer qtdFilmes;

	public Planeta() {
	}

	public Planeta(String nome, Integer qtdFilmes) {
		this.nome = nome;
		this.qtdFilmes = qtdFilmes;
	}

	@JsonSerialize(using = ToStringSerializer.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public Integer getQtdFilmes() {
		return qtdFilmes;
	}

	public void setQtdFilmes(Integer qtdFilmes) {
		this.qtdFilmes = qtdFilmes;
	}

	@Override
	public String toString() {
		return "Planeta [id=" + id + ", nome=" + nome + ", clima=" + clima + ", terreno=" + terreno + ", qtdFilmes="
				+ qtdFilmes + "]";
	}

}
