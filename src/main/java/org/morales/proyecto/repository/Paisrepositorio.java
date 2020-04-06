package org.morales.proyecto.repository;


import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Paisrepositorio extends JpaRepository<Pais, Long> {
	
	public Pais getByNombre(String nombre);

	
	

}
