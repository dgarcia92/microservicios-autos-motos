package com.auto.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto.service.repository.CarRepository;
import com.auto.service.entity.Car;

@Service
public class CarService {

	@Autowired
	private CarRepository repository;
	
	
	
	public List<Car> getAll()
	{
		return repository.findAll();
	}
	
	
	public Car getCarByID(Long id)
	{
		return repository.findById(id).orElse(null);
	}
	
	
	public Car save(Car car)
	{
		Car newCar = repository.save(car);
		return newCar;
	}
	
	
	public List<Car> byUsuarioId(Long id)
	{
		return repository.findByUserId(id);
	}
	
	
}
