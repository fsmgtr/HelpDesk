package com.moutinho.helpDesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moutinho.helpDesk.domain.Chamado;
import com.moutinho.helpDesk.domain.Cliente;
import com.moutinho.helpDesk.domain.Tecnico;
import com.moutinho.helpDesk.domain.enums.Perfil;
import com.moutinho.helpDesk.domain.enums.Prioridade;
import com.moutinho.helpDesk.domain.enums.Status;
import com.moutinho.helpDesk.repository.ChamadoRepository;
import com.moutinho.helpDesk.repository.ClienteRepository;
import com.moutinho.helpDesk.repository.PessoaRepository;
import com.moutinho.helpDesk.repository.TecnicoRepository;

@Service
public class DbService {
	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@SuppressWarnings("unused")
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
 
	public void instaciaDb() {
		Tecnico tecnico = new Tecnico(null, "Olha aí", "85797230518", "lonk@seila.com", encoder.encode("123"));
		tecnico.addPerfil(Perfil.ADMIN);
		tecnicoRepository.saveAll(Arrays.asList(tecnico));

		Cliente cliente = new Cliente(null, "Filipão gato", "06129317069", "GATO@LIVE.OI", encoder.encode("123"));
		clienteRepository.saveAll(Arrays.asList(cliente));

		Chamado chamado = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado",
				tecnico, cliente);
		chamadoRepository.saveAll(Arrays.asList(chamado));

	}
}
