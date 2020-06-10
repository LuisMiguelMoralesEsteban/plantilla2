package org.morales.proyecto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.crypto.Data;



@Entity
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Venta(LocalDate fecha, Collection<Lineaventa> lineaventaencurso) {
		
		this.fecha = fecha;
		
		this.lineaventaencurso = new ArrayList<Lineaventa>();
		
	}


	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public Collection<Lineaventa> getLineaventaencurso() {
		return lineaventaencurso;
	}


	public void setLineaventaencurso(Collection<Lineaventa> lineaventaencurso) {
		this.lineaventaencurso = lineaventaencurso;
	}


	private LocalDate fecha;
	
	
	@OneToMany(mappedBy="venta")
	private Collection<Lineaventa> lineaventaencurso;
	@ManyToOne
	private Persona ventaencurso;

	public Persona getVentaencurso() {
		return ventaencurso;
	}




	public void setVentaencurso(Persona ventaencurso) {
		this.ventaencurso = ventaencurso;
	}
	



	

	

	
	
}