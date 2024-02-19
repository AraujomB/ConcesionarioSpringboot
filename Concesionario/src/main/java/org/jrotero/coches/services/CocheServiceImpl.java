package org.jrotero.coches.services;

import java.util.List;

import org.jrotero.coches.models.CocheEntity;
import org.jrotero.coches.repositories.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
	public CocheEntity create(CocheEntity coche) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CocheEntity update(Long id, CocheEntity coche) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CocheEntity> getAllCars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
