package com.auto.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.service.service.CarService;
import com.auto.service.entity.Car;

@RestController
@RequestMapping("/car")
public class CarController {

	@Autowired
	private CarService service;

	
	@GetMapping
	public ResponseEntity<List<Car>> listarCarros()
	{
		List<Car> cars = service.getAll();
		
		if(cars.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(cars);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCar(@PathVariable("id") Long id)
	{
		Car car = service.getCarByID(id);
		
		if(car == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(car);
	}
	
	
	@PostMapping
	public ResponseEntity<Car> create(@RequestBody Car car)
	{	
		Car newCar = service.save(car);
		return ResponseEntity.ok(newCar);
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Car>> listarCarrosByUser(@PathVariable("id") Long id)
	{
		List<Car> cars = service.byUsuarioId(id);
		
		if(cars.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(cars);
	}
}
