package com.moto.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.service.entity.Moto;
import com.moto.service.reporistory.MotoRepository;

@Service
public class MotoService {

	
	@Autowired
	private MotoRepository repository;
	
	

	public List<Moto> getAll()
	{
		return repository.findAll();
	}
	
	
	public Moto getMotoByID(Long id)
	{
		return repository.findById(id).orElse(null);
	}
	
	
	public Moto save(Moto moto)
	{
		Moto newMoto = repository.save(moto);
		return newMoto;
	}
	
	
	public List<Moto> byUsuarioId(Long id)
	{
		return repository.findByUserId(id);
	}
}
