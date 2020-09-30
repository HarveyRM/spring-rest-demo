package org.jftm.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message="Name must have a least 2 characters")
	private String name;

	@JsonIgnore
	private String password;
	
	@Past
	private Date birthDate;

	@OneToMany(mappedBy = "user")
	private List<Post> posts;

	public User(Integer id, @Size(min = 2, message = "Name must have a least 2 characters") String name, String password, @Past Date birthDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthDate = birthDate;
	}
}
