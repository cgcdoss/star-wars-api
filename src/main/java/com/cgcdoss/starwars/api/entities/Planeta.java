package com.cgcdoss.starwars.api.entities;

import javax.persistence.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planetas")
public class Planeta {

	private Long id;
	private String nome;
	private String clima;
	private String terreno;
	private Integer qtdFilmes = 0;

	public Planeta() {
	}
	
	public Planeta(String nome, Integer qtdFilmes) {
		this.nome = nome;
		this.qtdFilmes = qtdFilmes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "clima", nullable = false)
	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	@Column(name = "terreno", nullable = false)
	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	@Transient
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
