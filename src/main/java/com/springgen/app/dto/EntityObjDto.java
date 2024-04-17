package com.springgen.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityObjDto {
	private Integer id;
	private String entityName;
	private String crudOps;
	private String fields;
	private String basePackagePath;
	private String databaseUrl;
	private String databaseUserName;
	private String databasePassword;
}
