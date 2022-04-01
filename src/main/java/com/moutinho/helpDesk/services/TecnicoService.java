package com.moutinho.helpDesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moutinho.helpDesk.domain.Pessoa;
import com.moutinho.helpDesk.domain.Tecnico;
import com.moutinho.helpDesk.domain.dtos.TecnicoDTO;
import com.moutinho.helpDesk.repository.PessoaRepository;
import com.moutinho.helpDesk.repository.TecnicoRepository;
import com.moutinho.helpDesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		validaPorCpfEEmail(tecnicoDTO);
		Tecnico tecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(tecnico);
	}

	private void validaPorCpfEEmail(TecnicoDTO tecnicoDTO) {

		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
		if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException("CPF Já Cadastrado");
		}
		pessoa = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException("Email Já cadatrado no sistema!");
		}
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(id);
		Tecnico oldTecnico = findById(id);
		validaPorCpfEEmail(tecnicoDTO);
		oldTecnico = new Tecnico(tecnicoDTO);
		
		return tecnicoRepository.save(oldTecnico);
	}

	public Tecnico delete(Integer id) {
		Tecnico tecnico = findById(id);
		if(tecnico.getChamados().size() > 0) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException
			(" Técnico Possui Ordens de serviço e não pode ser deletado!");
		} 
			tecnicoRepository.deleteById(id);
		
		return null;
	}

}
