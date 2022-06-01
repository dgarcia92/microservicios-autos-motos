package com.moto.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moto.service.entity.Moto;
import com.moto.service.service.MotoService;

@Controller
@RequestMapping("/moto")
public class MotoController {

	@Autowired
	private MotoService service;
	
	
	@GetMapping
	public ResponseEntity<List<Moto>> listarMotos()
	{
		List<Moto> motos = service.getAll();
		
		if(motos.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(motos);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Moto> getMoto(@PathVariable("id") Long id)
	{
		Moto moto = service.getMotoByID(id);
		
		if(moto == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(moto);
	}
	
	
	@PostMapping
	public ResponseEntity<Moto> create(@RequestBody Moto moto)
	{	
		Moto newMoto = service.save(moto);
		return ResponseEntity.ok(newMoto);
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Moto>> listarMotosByID(@PathVariable("id") Long id)
	{
		List<Moto> motos = service.byUsuarioId(id);
		
		if(motos.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(motos);
	}
}
