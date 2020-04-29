package org.morales.proyecto.repository;


import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Paisrepositorio extends JpaRepository<Pais, Long> {
	
	public Pais getByNombre(String nombre);
	public List<Pais> findAllByOrderByNombreAsc();
	
	

}
