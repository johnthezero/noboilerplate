package com.springgen.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springgen.app.dto.EntityObjDto;
import com.springgen.app.service.EntityObjService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/generate/")
public class EntityObjController {
	private EntityObjService entityService;
	@PostMapping("entity")
	public ResponseEntity<String>getEntity(@RequestBody EntityObjDto dto){
		String response=entityService.generateEntity(dto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	@PostMapping("dto")
	public ResponseEntity<String>getEntityDto(@RequestBody EntityObjDto dto){
		String response=entityService.generateEntityDto(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("mapper")
	public ResponseEntity<String>getMapper(@RequestBody EntityObjDto dto){
		String response=entityService.generateMapper(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("properties")
	public ResponseEntity<String>getProperties(@RequestBody EntityObjDto dto){
		String response=entityService.generateProperties(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("repository")
	public ResponseEntity<String>getRepository(@RequestBody EntityObjDto dto){
		String response=entityService.generateRepository(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("exception")
	public ResponseEntity<String>getException(@RequestBody EntityObjDto dto){
		String response=entityService.generateException(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("service")
	public ResponseEntity<String>getService(@RequestBody EntityObjDto dto){
		String response=entityService.generateService(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("serviceimpl")
	public ResponseEntity<String>getServiceImpl(@RequestBody EntityObjDto dto){
		String response=entityService.generateServiceImpl(dto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("controller")
	public ResponseEntity<String>getController(@RequestBody EntityObjDto dto){
		String response=entityService.generateController(dto);
		return ResponseEntity.ok(response);
	}
	
}
