package com.springgen.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="entities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityObj {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="entity_name",nullable=false)
	private String entityName;
	@Column(name="crud_operations")
	private String crudOps;
	@Column(name="fields")
	private String fields;
	@Column(name="package_path")
	private String basePackagePath;
	@Column(name="db_url")
	private String databaseUrl;
	@Column(name="db_user")
	private String databaseUserName;
	@Column(name="db_password")
	private String databasePassword;
}
