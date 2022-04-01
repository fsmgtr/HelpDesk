package com.moutinho.helpDesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moutinho.helpDesk.domain.Cliente;
import com.moutinho.helpDesk.domain.enums.Perfil;

public class ClienteDTO implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull(message = "O campo nome é obrigatório")
	private String nome;
	
	@NotNull(message = "O campo CPF é obrigatório ")
	private String cpf;
	
	@NotNull(message = "O campo email é obrigatório")
	private String email;
	
	@NotNull(message = "O campo senha é obrigatório")
	private String senha; 
	
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao = LocalDate.now();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(perfilX -> Perfil.toEnum(perfilX)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.senha =  cliente.getSenha();
		this.cpf = cliente.getCpf();
		this.email = cliente.getEmail();
		this.perfis = cliente.getPerfis().stream().map(xPerfil -> xPerfil.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = cliente.getDataCriacao();
	}

	public ClienteDTO() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
