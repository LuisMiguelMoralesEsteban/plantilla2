package org.morales.proyecto.domain;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Pais {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "nace")
	private Collection<Persona> nace;
	public Pais(String nombre) {
		super();
		this.nombre = nombre;
	}

	
	public Collection<Persona> getNace() {
		return nace;
	}


	public void setNace(Collection<Persona> nace) {
		this.nace = nace;
	}


	public Pais() {
		super();
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
