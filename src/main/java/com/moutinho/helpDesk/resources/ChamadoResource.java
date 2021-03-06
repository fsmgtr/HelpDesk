package com.moutinho.helpDesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moutinho.helpDesk.domain.Chamado;
import com.moutinho.helpDesk.domain.dtos.ChamadoDTO;
import com.moutinho.helpDesk.services.ChamadoService;

@RestController
@RequestMapping (value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado chamado = chamadoService.findById(id);		
		return ResponseEntity.ok().body(new ChamadoDTO(chamado));
	}
	
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> chamados = chamadoService.findAll();
		List<ChamadoDTO> chamadosDTOS = chamados.stream().map(chDto -> new ChamadoDTO(chDto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(chamadosDTOS);
	}
	
	@PostMapping()
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO){
		Chamado chamado = chamadoService.create(chamadoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(chamado.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@Valid @PathVariable Integer id, @RequestBody ChamadoDTO chamadoDTO){
		Chamado chamado = chamadoService.update(id, chamadoDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(chamado));
	}
	
	

}

