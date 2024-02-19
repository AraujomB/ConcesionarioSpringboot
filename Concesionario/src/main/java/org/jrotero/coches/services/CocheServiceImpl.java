package org.jrotero.coches.services;

import java.util.ArrayList;
import java.util.List;

import org.jrotero.coches.models.CocheEntity;
import org.jrotero.coches.repositories.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CocheServiceImpl implements CocheService {
	
	@Autowired
	CocheRepository repository;

	@Override
	public CocheEntity getCar(Long id) throws Exception {
		var response = repository.findById(id).orElseThrow();
		CocheEntity coche = new CocheEntity(response.getMarca(), response.getModelo());
		return coche;
	}

	@Override
	public CocheEntity create(CocheEntity coche) throws Exception{
		if(coche.getId()!=null) {
			var response = repository.findById(coche.getId()).get();
			if(response.getId() == coche.getId()) {
				throw new Exception("Error");
			}
		}
		return repository.save(coche);
	}

	@Override
	public CocheEntity update(Long id, CocheEntity coche) {
		var response = repository.findById(id).orElseThrow();
		response.setMarca(coche.getMarca());
		response.setModelo(coche.getModelo());
		return response;
	}

	@Override
	public List<CocheEntity> getAllCars() {
		List<CocheEntity> cars = new ArrayList<CocheEntity>();
		repository.findAll().forEach(carResponse -> {
			cars.add(carResponse);
		});
		return cars;
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
