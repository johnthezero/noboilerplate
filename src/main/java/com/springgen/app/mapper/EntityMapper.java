package com.springgen.app.mapper;

import com.springgen.app.dto.EntityObjDto;
import com.springgen.app.entity.EntityObj;

public class EntityMapper {
	public static EntityObj mapToEntityObj(EntityObjDto dto) {
		return new EntityObj(
				dto.getId(),
				dto.getEntityName(),
				dto.getCrudOps(),
				dto.getFields(),
				dto.getBasePackagePath(),
				dto.getDatabaseUrl(),
				dto.getDatabaseUserName(),
				dto.getDatabasePassword()
		);
	}
	public static EntityObjDto mapToEntityDto(EntityObj obj) {
		return new EntityObjDto(
				obj.getId(),
				obj.getEntityName(),
				obj.getCrudOps(),
				obj.getFields(),
				obj.getBasePackagePath(),
				obj.getDatabaseUrl(),
				obj.getDatabaseUserName(),
				obj.getDatabasePassword()
		);
	}
	
}
