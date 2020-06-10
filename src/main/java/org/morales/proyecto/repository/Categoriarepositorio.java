package org.morales.proyecto.repository;


import org.morales.proyecto.domain.Categoria;
import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Categoriarepositorio extends JpaRepository<Categoria, Long> {
	
	public Categoria getByNombre(String nombre);
	public List<Categoria> findAllByOrderByNombreAsc();
	
	

}
