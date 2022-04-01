package com.moutinho.helpDesk.services;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moutinho.helpDesk.domain.Chamado;
import com.moutinho.helpDesk.domain.Cliente;
import com.moutinho.helpDesk.domain.Tecnico;
import com.moutinho.helpDesk.domain.dtos.ChamadoDTO;
import com.moutinho.helpDesk.domain.enums.Prioridade;
import com.moutinho.helpDesk.domain.enums.Status;
import com.moutinho.helpDesk.repository.ChamadoRepository;
import com.moutinho.helpDesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	ChamadoRepository chamadoRepository;
	@Autowired
	TecnicoService tecnicoService;
	@Autowired
	ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> chamado = chamadoRepository.findById(id);
		return chamado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! " + id));
	}

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO chamadoDTO) {
		return chamadoRepository.save(newChamado(chamadoDTO));
	}

	private Chamado newChamado(ChamadoDTO chamadoDTO) {
		Tecnico tecnico = tecnicoService.findById(chamadoDTO.getTecnico());
		Cliente cliente = clienteService.findById(chamadoDTO.getCliente());
		Chamado chamado = new Chamado();
		if (chamado.getId() != null) {
			chamado.setId(chamadoDTO.getId());
		}
		if(chamadoDTO.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
		chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
		chamado.setTitulo(chamadoDTO.getTitulo());
		chamado.setObservacao(chamadoDTO.getObservacao());
		return chamado;
	}

	public Chamado update(@Valid Integer id, ChamadoDTO chamadoDTO) {
		chamadoDTO.setId(id);
		Chamado oldChamado = findById(id);
		oldChamado = newChamado(chamadoDTO);
		return chamadoRepository.save(oldChamado);
	}

	 

}
