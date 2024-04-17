package com.springgen.app.service;

import com.springgen.app.dto.EntityObjDto;

public interface EntityObjService {
	public String generateEntity(EntityObjDto dto);//ok
	public String generateMapper(EntityObjDto dto);//ok
	public String generateEntityDto(EntityObjDto dto);//ok
	public String generateProperties(EntityObjDto dto);//ok
	public String generateService(EntityObjDto dto);//ok
	public String generateServiceImpl(EntityObjDto dto); //ok
	public String generateController(EntityObjDto dto);//ok
	public String generateRepository(EntityObjDto dto);//ok
	public String generateException(EntityObjDto dto);//ok
}
