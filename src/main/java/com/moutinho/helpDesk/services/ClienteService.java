package com.moutinho.helpDesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moutinho.helpDesk.domain.Cliente;
import com.moutinho.helpDesk.domain.Pessoa;
import com.moutinho.helpDesk.domain.dtos.ClienteDTO;
import com.moutinho.helpDesk.repository.ClienteRepository;
import com.moutinho.helpDesk.repository.PessoaRepository;
import com.moutinho.helpDesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		validaPorCpfEEmail(clienteDTO);
		Cliente cliente = new Cliente(clienteDTO);
		return clienteRepository.save(cliente);
	}

	private void validaPorCpfEEmail(ClienteDTO clienteDTO) {

		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
		if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException("CPF Já Cadastrado");
		}
		pessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());
		if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException(
					"Email Já cadatrado no sistema!");
		}
	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente oldCliente = findById(id);
		if (clienteDTO.getSenha().equals(oldCliente.getSenha())) {
			clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		}

		validaPorCpfEEmail(clienteDTO);
		oldCliente = new Cliente(clienteDTO);
		oldCliente.setSenha(clienteDTO.getSenha());
		return clienteRepository.save(oldCliente);
	}

	public Cliente delete(Integer id) {
		Cliente cliente = findById(id);
		if (cliente.getChamados().size() > 0) {
			throw new com.moutinho.helpDesk.services.exceptions.DataIntegrityViolationException(
					" Cliente Possui Ordens de serviço e não pode ser deletado!");
		}
		clienteRepository.deleteById(id);

		return null;
	}

}
