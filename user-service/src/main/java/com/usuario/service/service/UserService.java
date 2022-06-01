package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.User;
import com.usuario.service.feingclients.CarFeingClient;
import com.usuario.service.feingclients.MotoFeingClient;
import com.usuario.service.models.Car;
import com.usuario.service.models.Moto;
import com.usuario.service.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CarFeingClient carFeingClient;
	
	@Autowired
	private MotoFeingClient motoFeingClient;
	
	

	// ----------- USANDO REST TEMPLATE --------------
	
	
	public List<Car> getCars(Long usuarioID)
	{
		List<Car> carros = restTemplate.getForObject("http://localhost:8082/car/user/" + usuarioID, List.class);
		return carros;
	}

	public List<Moto> getMotos(Long usuarioID)
	{
		List<Moto> motos = restTemplate.getForObject("http://localhost:8083/moto/user/" + usuarioID, List.class);
		return motos;
	}
	

	// ----------- END USANDO REST TEMPLATE --------------

	
	// ----------- USANDO FEING CLIENTE --------------
	
	public Car saveCar(Long idUser, Car car)
	{	
		car.setUserId(idUser);
		Car newCar = carFeingClient.save(car);
		return newCar;
	}
	
	public Moto saveMoto(Long idUser, Moto moto)
	{	
		moto.setUserId(idUser);
		Moto newMoto = motoFeingClient.save(moto);
		return newMoto;
	}

	
	public Map<String, Object> getUserAndVehicles(Long id)
	{
		Map<String, Object> result = new HashMap<>();
		User user = repository.findById(id).orElse(null);
		
		if(user == null)
		{
			result.put("Mensaje", "El usuario no existe");
		}
		
		result.put("Usuario", user);
		
		List<Car> cars = carFeingClient.getCars(id);
		
		if( cars.isEmpty())
		{
			result.put("Cars", "El usuario no tiene carros");
		}
		else
		{
			result.put("Cars", cars);
		}
		
		
		List<Moto> motos = motoFeingClient.getMotos(id);
		
		if( motos.isEmpty())
		{
			result.put("moto", "El usuario no tiene motos");
		}
		else
		{
			result.put("moto", motos);
		}
		
		return result;
	}

	// ----------- END USANDO FEING CLIENTE --------------
	
	
	
	
	
	
	public List<User> getAll()
	{
		return repository.findAll();
	}
	
	
	public User getUserByID(Long id)
	{
		return repository.findById(id).orElse(null);
	}
	
	
	public User save(User user)
	{
		User nuevoUsuario = repository.save( user);
		
		return nuevoUsuario;
	}
	
}
