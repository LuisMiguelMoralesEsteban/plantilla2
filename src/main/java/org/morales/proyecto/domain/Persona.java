package org.morales.proyecto.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@Column(unique = true)
	private String loginname;

	private String password;
	
	private Integer altura;
	
	private LocalDate fnac;
	@Column(unique = true)
	private String img;
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@ManyToOne
	private Pais nace;
	
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
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	public LocalDate getFnac() {
		return fnac;
	}
	public Pais getNace() {
		return nace;
	}
	public void setNace(Pais nace) {
		this.nace = nace;
	}
	public void setFnac(LocalDate fnac) {
		this.fnac = fnac;
	}
	public Persona() {}
	public Persona(String nombre, String loginname, String password, Integer altura, LocalDate fnac) {
	
		this.nombre = nombre;
		this.loginname = loginname;
		this.password = password;
		this.altura = altura;
		this.fnac = fnac;
		
	}
	
	

}
