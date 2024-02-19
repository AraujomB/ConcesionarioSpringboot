package org.jrotero.coches.services;

import java.util.List;

import org.jrotero.coches.models.CocheEntity;

public interface CocheService {
	public CocheEntity getCar(Long id) throws Exception; 
	public CocheEntity create(CocheEntity coche) throws Exception;
	public CocheEntity update(Long id, CocheEntity coche);
	public List<CocheEntity> getAllCars();
	public void delete(Long id);
}
