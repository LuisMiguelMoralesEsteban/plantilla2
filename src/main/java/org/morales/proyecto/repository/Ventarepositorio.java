package org.morales.proyecto.repository;


import org.morales.proyecto.domain.Venta;
import org.morales.proyecto.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Ventarepositorio extends JpaRepository<Venta, Long> {
	

	

}
