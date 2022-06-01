package com.usuario.service.feingclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usuario.service.models.Car;

@FeignClient(name = "car-service", url = "http://localhost:8082")
@RequestMapping("/car")
public interface CarFeingClient {
	
	@PostMapping()
	public Car save(@RequestBody Car car);
	
	@GetMapping("/user/{id}")
	public List<Car> getCars(@PathVariable("id") Long id);
	
}
