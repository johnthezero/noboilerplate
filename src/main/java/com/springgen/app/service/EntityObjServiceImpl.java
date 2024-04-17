package com.springgen.app.service;

import org.springframework.stereotype.Service;
import com.springgen.app.dto.EntityObjDto;
import com.springgen.app.mapper.EntityMapper;
import com.springgen.app.repository.EntityObjRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntityObjServiceImpl implements EntityObjService {
	private EntityObjRepository repo;
	@Override
	public String generateEntity(EntityObjDto dto) {
		String BASEPACKAGE="package "+dto.getBasePackagePath();
		String TABLE_NAME=dto.getEntityName()+"_table";
		String IMPORTS=BASEPACKAGE+".entity;\r\n"
				+ "\r\n"
				+ "import jakarta.persistence.Column;\r\n"
				+ "import jakarta.persistence.Entity;\r\n"
				+ "import jakarta.persistence.GeneratedValue;\r\n"
				+ "import jakarta.persistence.GenerationType;\r\n"
				+ "import jakarta.persistence.Id;\r\n"
				+ "import jakarta.persistence.Table;\r\n"
				+ "import lombok.AllArgsConstructor;\r\n"
				+ "import lombok.Getter;\r\n"
				+ "import lombok.NoArgsConstructor;\r\n"
				+ "import lombok.Setter;\r\n"
				+ "\r\n"
				+ "@Getter \r\n"
				+ "@Setter \r\n"
				+ "@AllArgsConstructor \r\n"
				+ "@NoArgsConstructor \r\n"
				+ "@Entity\r\n"
				+ "@Table(name=\""+TABLE_NAME.toLowerCase()+"\")\r\n"
				+ "\r\n";
		String CLASSOPEN="public class "+dto.getEntityName()+"{\r\n";
		String CLASSBODY=this.toFormattedString(dto.getFields());
		String CLASSCLOSE="}";
		repo.save(EntityMapper.mapToEntityObj(dto));
		return IMPORTS+CLASSOPEN+CLASSBODY+CLASSCLOSE;
	}
	
	@Override
	public String generateEntityDto(EntityObjDto dto) {
		String BASEPACKAGE="package "+dto.getBasePackagePath()+".dto;\r\n";
		String IMPORTS=BASEPACKAGE
				+"import lombok.AllArgsConstructor;\r\n"
				+ "import lombok.Getter;\r\n"
				+ "import lombok.NoArgsConstructor;\r\n"
				+ "import lombok.Setter;"
				+"\r\n"
				+"\r\n";
		String ANNOTATIONS="@Getter\r\n"
				+"@Setter\r\n"
				+"@AllArgsConstructor\r\n"
				+"@NoArgsConstructor\r\n"
		;
		
		String CLASSOPEN="public class "+dto.getEntityName()+"Dto { \r\n";
		String CLASSBODY=toFormattedStringNormal(dto.getFields());
		String CLASSCLOSE="}";
		return IMPORTS+ANNOTATIONS+CLASSOPEN+CLASSBODY+CLASSCLOSE;
	}
	@Override
	public String generateMapper(EntityObjDto dto) {
		String BASEPACKAGE=dto.getBasePackagePath();
		String IMPORTS="package "+BASEPACKAGE+".mapper;\r\n"
				+ "\r\n"
				+ "import "+BASEPACKAGE+".dto."+dto.getEntityName()+"Dto;\r\n"
				+ "import "+BASEPACKAGE+".entity."+dto.getEntityName()+";\r\n";
		String CLASSOPEN="public class "+dto.getEntityName()+"Mapper {\r\n";
		String METHODOPEN1="\tpublic static "+dto.getEntityName()+" mapTo"+dto.getEntityName()+"("+dto.getEntityName()+"Dto "+dto.getEntityName().toLowerCase()+"Dto"+"){\r\n";
		String METHODOPEN2="\tpublic static "+dto.getEntityName()+"Dto mapTo"+dto.getEntityName()+"Dto"+"("+dto.getEntityName()+" "+dto.getEntityName().toLowerCase()+"){\r\n";
		String METHODCLOSE="\t\t);"+"\r\n\t}"+"\r\n";
		String CLASSBODY1=METHODOPEN1+getMapperBody("",dto)+METHODCLOSE;
		String CLASSBODY2=METHODOPEN2+getMapperBody("Dto",dto)+METHODCLOSE;
		return IMPORTS+CLASSOPEN+CLASSBODY1+CLASSBODY2+"\r\n}";
	}
	//DRY!!!
	private String getMapperBody(String s,EntityObjDto dto) {
		String body="\t\treturn new "+dto.getEntityName()+s+"(\r\n";
		String[] fields=dto.getFields().split(",");
		String coma=",";
		if(s.equals("")) {
			for(int i=0;i<fields.length;i++) {
				if(i==fields.length-1) {
					coma="";
				}
				body=body.concat("\t\t\t"+dto.getEntityName().toLowerCase()+"Dto.get"+(firstUpperCase(getLastWord(fields[i])))+"()"+coma+"\r\n");
			}
		}
		if(s.equals("Dto")) {
			for(int i=0;i<fields.length;i++) {
				if(i==fields.length-1) {
					coma="";
				}
				body=body.concat("\t\t\t"+dto.getEntityName().toLowerCase()+".get"+(firstUpperCase(getLastWord(fields[i])))+"()"+coma+"\r\n");
			}
		}
		return body;
	}
	private String getMapperFields(String s,String suffix) {
		String[] array=s.split(",");
		for(String inner : array) {
			inner="get"+getLastWord(inner);
		}
		return null;
	}
	private String firstUpperCase(String s) {
		return s.replace(String.valueOf(s.charAt(0)),String.valueOf(s.charAt(0)).toUpperCase() );
	}
	@Override
	public String generateProperties(EntityObjDto dto) {
		String DB_NAME=dto.getEntityName().toLowerCase()+"_db";
		String props="spring.application.name="+DB_NAME+"\r\n"
				+ "spring.datasource.url=jdbc:mysql://localhost:3306/"+DB_NAME+"\r\n"
				+ "spring.datasource.username=root\r\n"
				+ "spring.datasource.password=root\r\n"
				+ "spring.jpa.hibernate.ddl-auto=update\r\n"
				+ "spring.jpa.properties.dialect=org.hibernate.dialect.MySQLDialect";
		return props;
	}

	@Override
	public String generateService(EntityObjDto dto) {
		String BASEPACKAGE=dto.getBasePackagePath();
		String create="",
			   read="",
			   update="",
			   delete="";
		String service="package "+BASEPACKAGE+".service;\r\n"
				+ "\r\n"
				+ "import java.util.List;\r\n"
				+ "import "+BASEPACKAGE+".dto."+dto.getEntityName()+"Dto;\r\n"
				+ "\r\n"
				+ "public interface "+dto.getEntityName()+"Service {\r\n\t";
		String[] arr=dto.getCrudOps().split(",");
		for(String s : arr) {
			switch(s) {
				case "create" : create="public "+dto.getEntityName()+"Dto create"+dto.getEntityName()+"("+dto.getEntityName()+"Dto "+dto.getEntityName().toLowerCase()+"Dto);\r\n\t";
					            break;
				case "read"   :	read="public "+dto.getEntityName()+"Dto get"+dto.getEntityName()+"ById(Integer id);\r\n\t";
								read=read
									 +"public List<"+dto.getEntityName()+"Dto> get"+dto.getEntityName()+"s();\r\n\t";
								break;
				case "update" : update="public "+dto.getEntityName()+"Dto update"+dto.getEntityName()+"(Integer id,"+dto.getEntityName()+"Dto "+dto.getEntityName().toLowerCase()+"Dto);\r\n\t";
								break;
				case "delete" : delete="public void delete"+dto.getEntityName()+"(Integer id);\r\n}";
			}
		}
		service=service+create+read+update+delete;
		return service;
	}

	@Override
	public String generateServiceImpl(EntityObjDto dto) {
		String BASEPACKAGE=dto.getBasePackagePath();
		String IMPORTS="package "+BASEPACKAGE+".service;\r\n"
				+ "\r\n"
				+ "import java.util.List;\r\n"
				+ "import java.util.stream.Collectors;\r\n"
				+ "import org.springframework.stereotype.Service;\r\n"
				+ "import "+BASEPACKAGE+".dto."+dto.getEntityName()+"Dto;\r\n"
				+ "import "+BASEPACKAGE+".entity."+dto.getEntityName()+";\r\n"
				+ "import "+BASEPACKAGE+".exception."+dto.getEntityName()+"NotFoundException;\r\n"
				+ "import "+BASEPACKAGE+".mapper."+dto.getEntityName()+"Mapper;\r\n"
				+ "import "+BASEPACKAGE+".repository."+dto.getEntityName()+"Repository;\r\n"
				+ "import lombok.AllArgsConstructor;\r\n"
				+ "";
		String ANNOTATIONS="@Service\r\n"
				+ "@AllArgsConstructor\r\n";
		String CLASSOPEN="public class "+dto.getEntityName()+"ServiceImpl implements "+dto.getEntityName()+"Service {\r\n\t";
		String CLASSBODY="private "+dto.getEntityName()+"Repository "+dto.getEntityName().toLowerCase()+"Repository;\r\n\t";
		String METHODS="";
		String method="";
		String create="",
				read="",
				update="",
				delete="";
		String impl=IMPORTS+ANNOTATIONS+CLASSOPEN+CLASSBODY;
		for(String s : dto.getCrudOps().split(",")) {
			method="@Override\r\n\t";
			switch(s) {
				case "create" : create="public "+dto.getEntityName()+"Dto create"+dto.getEntityName()+"("+dto.getEntityName()+"Dto "+dto.getEntityName().toLowerCase()+"Dto) {\r\n\t\t"
										+dto.getEntityName()+" "+dto.getEntityName().toLowerCase()+"="+dto.getEntityName()+"Mapper.mapTo"+dto.getEntityName()+"("+dto.getEntityName().toLowerCase()+"Dto);\r\n\t\t"
										+dto.getEntityName()+" saved"+dto.getEntityName()+"="+dto.getEntityName().toLowerCase()+"Repository.save("+dto.getEntityName().toLowerCase()+");\r\n\t\t"
										+"return "+dto.getEntityName()+"Mapper.mapTo"+dto.getEntityName()+"Dto(saved"+dto.getEntityName()+");\r\n\t}\r\n\t";
				            	impl=impl+method+create;
								break;
				case "read"   :	read="public "+dto.getEntityName()+"Dto get"+dto.getEntityName()+"ById(Integer id){\r\n\t\t"
										+dto.getEntityName()+" "+dto.getEntityName().toLowerCase()+"="+dto.getEntityName().toLowerCase()+"Repository.findById(id)\r\n\t\t\t.orElseThrow(()->\r\n\t\t\t new "
										+dto.getEntityName()+"NotFoundException(\""+dto.getEntityName()+" does not exists with the given id : \"+id));\r\n\t\t"
										+"return "+dto.getEntityName()+"Mapper.mapTo"+dto.getEntityName()+"Dto("+dto.getEntityName().toLowerCase()+");\r\n\t}\r\n\t";
								read=read
								 +method+"\r\n\t"
								 +"public List<"+dto.getEntityName()+"Dto> get"+dto.getEntityName()+"s(){\r\n\t\t"
								 +"List<"+dto.getEntityName()+"> "+dto.getEntityName().toLowerCase()+"s="+dto.getEntityName().toLowerCase()+"Repository.findAll();\r\n\t\t"
								 +"return "+dto.getEntityName().toLowerCase()+"s".toLowerCase()+".stream().map(("+dto.getEntityName().toLowerCase()+")->"+dto.getEntityName()+"Mapper.mapTo"+dto.getEntityName()+"Dto("+dto.getEntityName().toLowerCase()+")).collect(Collectors.toList());\r\n\t}\r\n\t";
								impl=impl+method+read;
								break;
				case "update" : update="public "+dto.getEntityName()+"Dto update"+dto.getEntityName()+"(Integer id,"+dto.getEntityName()+"Dto updated){\r\n\t\t"
								+dto.getEntityName()+" "+dto.getEntityName().toLowerCase()+"="+dto.getEntityName().toLowerCase()+"Repository.findById(id).orElseThrow(\r\n\t\t\t"
								+"()->new "+dto.getEntityName()+"NotFoundException(\""+dto.getEntityName()+" does not exists with this id :\""+"+id));\r\n\t\t"
								;
								String[] setters=dto.getFields().split(",");
								for(int i=1;i<setters.length;i++) {
									setters[i]=getLastWord(setters[i]);
									setters[i]=firstUpperCase(setters[i]);
									update=update+dto.getEntityName().toLowerCase()+".set"+setters[i]+"(updated.get"+setters[i]+"());\r\n\t\t";
								}
								update=update+dto.getEntityName()+" updatedObj="+dto.getEntityName().toLowerCase()+"Repository.save("+dto.getEntityName().toLowerCase()+");\r\n\t\t"
									    +"return "+dto.getEntityName()+"Mapper.mapTo"+dto.getEntityName()+"Dto(updatedObj);\r\n\t}\r\n\t";
							
								impl=impl+method+update;
								break;
				case "delete" : delete="public void delete"+dto.getEntityName()+"(Integer id){\r\n\t\t"
								+dto.getEntityName()+" "+dto.getEntityName().toLowerCase()+"="+dto.getEntityName().toLowerCase()+"Repository.findById(id).orElseThrow(()->\r\n\t\t\t"
								+"new "+dto.getEntityName()+"NotFoundException(\""+dto.getEntityName()+" does not exists with the given id : \"+id));\r\n\t\t"
								+dto.getEntityName().toLowerCase()+"Repository.deleteById(id);\r\n\t}\r\n\t\t";
								impl=impl+method+delete;
			}
		}
		
		return impl+"\r\n}";
	}

	@Override
	public String generateController(EntityObjDto dto) {
		String entity=dto.getEntityName();
		String entityDto=dto.getEntityName()+"Dto";
		String controller="";
		String BASEPACKAGE=dto.getBasePackagePath();
		controller="package "+BASEPACKAGE+".controller;\r\n";
		String IMPORTS="import java.util.List;\r\n"
				+ "import org.springframework.http.HttpStatus;\r\n"
				+ "import org.springframework.http.ResponseEntity;\r\n"
				+ "import org.springframework.web.bind.annotation.CrossOrigin;\r\n"
				+ "import org.springframework.web.bind.annotation.DeleteMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.GetMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
				+ "import org.springframework.web.bind.annotation.PostMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.PutMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestBody;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.RestController;\r\n"
				+ "import "+BASEPACKAGE+".dto."+entityDto+";\r\n"
				+ "import "+BASEPACKAGE+".service."+entity+"Service;\r\n"
				+ "import lombok.AllArgsConstructor;\r\n";
		String ANNOTATIONS="@RestController\r\n"
				+ "@AllArgsConstructor\r\n"
				+ "@RequestMapping(\"/api/"+dto.getEntityName().toLowerCase()+"s/"+"\")\r\n"
				+ "@CrossOrigin(\"*\")\r\n";
		controller=controller+IMPORTS+ANNOTATIONS;
		String[] methods=dto.getCrudOps().split(",");
		String BODY="public class "+dto.getEntityName()+"Controller {\r\n\t"
				     +"private " +dto.getEntityName()+"Service "+entity.toLowerCase()+"Service; \r\n\t";
		for(int i=0;i<methods.length;i++) {
			switch(methods[i]) {
				case "create" : BODY=BODY+"@PostMapping\r\n\t"
					            +"public ResponseEntity<"+dto.getEntityName()+"Dto> create"+dto.getEntityName()+"(@RequestBody "+entityDto+" dto){\r\n\t\t"
					            +entityDto+" saved="+entity.toLowerCase()+"Service.create"+entity+"(dto);\r\n\t\t"
					            +"return new ResponseEntity<>(saved,HttpStatus.CREATED);\r\n\t}\r\n\t";
							    break;
				case "read"   : BODY=BODY+"@GetMapping(\"{id}\")\r\n\t"
								+"public ResponseEntity<"+dto.getEntityName()+"Dto> get"+dto.getEntityName()+"ById(@PathVariable(\"id\")Integer id){\r\n\t\t"
								+entityDto+" "+entityDto.toLowerCase()+"="+entity.toLowerCase()+"Service.get"+entity+"ById(id);\r\n\t\t"
								+"return ResponseEntity.ok("+entityDto.toLowerCase()+");\r\n\t}\r\n\t"
								+"@GetMapping(\"\")\r\n\t"
								+"public ResponseEntity<List<"+entityDto+">>get"+entity+"s(){\r\n\t\t"
								+"List<"+entityDto+">"+entity.toLowerCase()+"s="+entity.toLowerCase()+"Service.get"+entity+"s();\r\n\t\t"
								+"return ResponseEntity.ok("+entity.toLowerCase()+"s"+");\r\n\t}\r\n\t";
								break;
				case "update" : BODY=BODY+"@PutMapping(\"{id}\")\r\n\t"
								+"public ResponseEntity<"+entityDto+">update"+entity+"(@PathVariable(\"id\") Integer id,\r\n\t\t\t\t\t\t\t\t\t\t"
								+"@RequestBody "+entityDto+" updated){\r\n\t\t"
								+entityDto+" "+entityDto.toLowerCase()+"="+entity.toLowerCase()+"Service.update"+entity+"(id,updated);\r\n\t\t"
								+"return ResponseEntity.ok("+entityDto.toLowerCase()+");\r\n\t}\r\n\t";
								break;
				case "delete" : BODY=BODY+"@DeleteMapping(\"{id}\")\r\n\t"
								+"public ResponseEntity<String>delete"+entity+"(@PathVariable (\"id\")Integer id){\r\n\t\t"
								+entity.toLowerCase()+"Service.delete"+entity+"(id);\r\n\t\t"
								+"return ResponseEntity.ok(\""+entity+" successfully deleted\");\r\n\t}";
								break;
			}
		}
		controller=controller+BODY+"\r\n}";
		return controller;
	}

	@Override
	public String generateRepository(EntityObjDto dto) {
		String BASEPACKAGE=dto.getBasePackagePath();
		String res="package "+BASEPACKAGE+".repository;\r\n"
				+ "\r\n"
				+ "import org.springframework.data.jpa.repository.JpaRepository;\r\n"
				+ "\r\n"
				+ "import "+BASEPACKAGE+".entity."+dto.getEntityName()+";\r\n"
				+ "\r\n"
				+ "public interface "+dto.getEntityName()+"Repository extends JpaRepository<"+dto.getEntityName()+", Integer> {\r\n"
				+ "\r\n"
				+ "}";
		return res;
	}

	@Override
	public String generateException(EntityObjDto dto) {
		String BASEPACKAGE=dto.getBasePackagePath();
		String res ="package "+BASEPACKAGE+".exception;\r\n"
				+ "\r\n"
				+ "import org.springframework.http.HttpStatus;\r\n"
				+ "import org.springframework.web.bind.annotation.ResponseStatus;\r\n"
				+ "\r\n"
				+ "@ResponseStatus(value=HttpStatus.NOT_FOUND)\r\n"
				+ "public class "+dto.getEntityName()+"NotFoundException extends RuntimeException {\r\n"
				+ "	\r\n"
				+ "	public "+dto.getEntityName()+"NotFoundException(String message) {\r\n"
				+ "		super(message);\r\n"
				+ "	}\r\n"
				+ "}";
		return res;
	}
	private String toFormattedString(String s) {
		String formattedString="";
		String[] array=s.split(",");
		for(int i=0;i<array.length;i++) {
			if(getLastWord(array[i]).equals("id")){
				formattedString=formattedString.concat("\t@Id\r\n");
				formattedString=formattedString.concat("\t@GeneratedValue(strategy=GenerationType.IDENTITY)\r\n");
				formattedString=formattedString.concat("\t"+array[i]+";\r\n");
			}else {
				formattedString=formattedString.concat("\t@Column(name=\""+getLastWord(array[i])+"\")\r\n");
				formattedString=formattedString.concat("\t"+array[i]+";\r\n");
			}
			
		}
		return formattedString;
	}
	private String toFormattedStringNormal(String s) {
		String formattedString="";
		String[] array=s.split(",");
		for(int i=0;i<array.length;i++) {
				formattedString=formattedString.concat("\t"+array[i]+";\r\n");
		}
		return formattedString;
	}
	private String getLastWord(String s) {
		String[] array=s.split(" ");
		s=array[array.length-1];
		return s;
	}

	

}
