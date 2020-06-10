package org.morales.proyecto.repository;


import org.morales.proyecto.domain.Categoria;
import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Productorepositorio extends JpaRepository<Producto, Long> {
	
	public Producto getByNombre(String nombre);
	public List<Producto> findAllByOrderByNombreAsc();
	
	

}
