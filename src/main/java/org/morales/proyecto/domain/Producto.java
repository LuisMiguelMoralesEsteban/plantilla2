package org.morales.proyecto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private int stock;
	private double precio;
	private String img;
	
	
	public Producto() {}
	public Producto( String nombre, int stock, double precio) {
		
		
		this.nombre = nombre;
		this.stock = stock;
		this.precio = precio;
		
		this.categoria = categoria;
		this.lineadeventaencurso = new ArrayList<Lineaventa>();
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


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public Collection<Lineaventa> getLineadeventaencurso() {
		return lineadeventaencurso;
	}


	public void setLineadeventaencurso(Collection<Lineaventa> lineadeventaencurso) {
		this.lineadeventaencurso = lineadeventaencurso;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(mappedBy="producto")
	private Collection<Lineaventa> lineadeventaencurso;

}
