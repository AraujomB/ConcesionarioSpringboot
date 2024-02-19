package org.jrotero.cocheB.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jrotero.cocheB.models.CocheEntity;
import org.jrotero.cocheB.service.CocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jro/concesionario/car")
public class CocheController {
	
	@Autowired
	CocheService service;
	
	public CocheController(CocheService service) {
		super();
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCar(@PathVariable Long id, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> error = result.getFieldErrors().stream().map(err -> {
				return "El campo'"+err.getField()+"' el mensaje "+err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errores",error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("Get car: ", service.getCar(id));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@PostMapping("/createCar")
	public ResponseEntity<?> create(@Valid @RequestBody CocheEntity coche, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> error = result.getFieldErrors().stream().map(err -> {
				return "El campo'"+err.getField()+"' el mensaje "+err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errores",error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("Create car: ", service.create(coche));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CocheEntity coche, BindingResult result){
		Map<String, Object> response = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> error = result.getFieldErrors().stream().map(err -> {
				return "El campo'"+err.getField()+"' el mensaje "+err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errores",error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("Update car: ", service.update(id, coche));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/get-all-cars")
	public ResponseEntity<?> getAllCars(){
		return ResponseEntity.ok(service.getAllCars());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok("Elemento borrado");
	}
	
}
