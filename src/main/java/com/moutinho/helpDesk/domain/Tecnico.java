package com.moutinho.helpDesk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moutinho.helpDesk.domain.dtos.TecnicoDTO;
import com.moutinho.helpDesk.domain.enums.Perfil;

@Entity
public class Tecnico extends Pessoa implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
	private List<Chamado> chamados = new ArrayList<>();
	
	
	public Tecnico(TecnicoDTO tecnico) {
		super();
		this.id = tecnico.getId();
		this.nome = tecnico.getNome();
		this.cpf = tecnico.getCpf();
		this.email = tecnico.getEmail();
		this.perfis = tecnico.getPerfis().stream().map(xPerfil -> xPerfil.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = tecnico.getDataCriacao();
	}

	

	public Tecnico() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
	
}
