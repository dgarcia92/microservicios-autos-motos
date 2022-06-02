package com.usuario.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.User;
import com.usuario.service.models.Car;
import com.usuario.service.models.Moto;
import com.usuario.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private UserService service;
	
	
	@GetMapping
	public ResponseEntity<List<User>> listarUsuario()
	{
		List<User> users = service.getAll();
		
		if(users.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(users);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id)
	{
		User user = service.getUserByID(id);
		
		if(user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(user);
	}
	
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user)
	{	
		User newUser = service.save(user);
		
		return ResponseEntity.ok(newUser);
	}
	
	
	
	@CircuitBreaker(name = "carCB", fallbackMethod = "fallBackGetCar")
	@GetMapping("/cars/{idUser}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("idUser") Long idUser)
	{
		User users = service.getUserByID(idUser);
		
		if(users == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		
		List<Car> cars = service.getCars(idUser);
		
		return ResponseEntity.ok(cars);
		
	}

	@CircuitBreaker(name = "motoCB", fallbackMethod = "fallBackGetMoto")
	@GetMapping("/motos/{idUser}")
	public ResponseEntity<List<Moto>> getMotos(@PathVariable("idUser") Long idUser)
	{
		User users = service.getUserByID(idUser);
		
		if(users == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		
		List<Moto> motos = service.getMotos(idUser);
		
		return ResponseEntity.ok(motos);
		
	}
	
	
	@CircuitBreaker(name = "carCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("/car/{id}")
	public ResponseEntity<Car> saveCarByUser(@PathVariable("id") Long id, @RequestBody Car car)
	{
		Car newCar = service.saveCar(id, car);  //Usando el FeingClient para guardar en el otro microservicio
		return ResponseEntity.ok(newCar);
	}
	
	@CircuitBreaker(name = "motoCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{id}")
	public ResponseEntity<Moto> saveMotoByUser(@PathVariable("id") Long id, @RequestBody Moto moto)
	{
		Moto newMoto = service.saveMoto(id, moto);  //Usando el FeingClient para guardar en el otro microservicio
		return ResponseEntity.ok(newMoto);
	}
	
	
	@CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
	@GetMapping("/vehicules/{id}")
	public ResponseEntity<Map<String, Object>> getAllVehicules(@PathVariable("id") Long id)
	{
		Map<String, Object> vehicules = service.getUserAndVehicles(id);
		return ResponseEntity.ok(vehicules);
	}
	
	
	
	private ResponseEntity<List<Car>> fallBackGetCar(@PathVariable("iduser") Long id, RuntimeException exception)
	{
		return new ResponseEntity("El usuario " + id + " tiene los carros en el taller", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Car>> fallBackSaveCar(@PathVariable("iduser") Long id, @RequestBody Car car, RuntimeException exception)
	{
		return new ResponseEntity("El usuario " + id + " no puede guardar su carro", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Moto>> fallBackGetMoto(@PathVariable("iduser") Long id, RuntimeException exception)
	{
		return new ResponseEntity("El usuario " + id + " tiene las motos en el taller", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Moto>> fallBackSaveMoto(@PathVariable("iduser") Long id, @RequestBody Moto moto, RuntimeException exception)
	{
		return new ResponseEntity("El usuario " + id + " no puede guardar su moto", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Car>> fallBackGetAll(@PathVariable("iduser") Long id, RuntimeException exception)
	{
		return new ResponseEntity("El usuario " + id + " tiene las vehículos en el taller", HttpStatus.OK);
	}
	
	
}
