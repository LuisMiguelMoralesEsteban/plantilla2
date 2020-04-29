package org.morales.proyecto.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.exception.DangerException;
import org.morales.proyecto.exception.InfoException;
import org.morales.proyecto.helper.H;
import org.morales.proyecto.helper.PRG;
import org.morales.proyecto.repository.Paisrepositorio;
import org.morales.proyecto.repository.Perosnarepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/pais")
public class Paiscontroler {
	
	@Autowired
	private Paisrepositorio repoPais;
	
	
	@PostMapping("d")
	public String borrarPost(@RequestParam("id") Long idPais, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);
		String nombrePais = "----";
		try {
			Pais pais = repoPais.getOne(idPais);
			nombrePais = pais.getNombre();
			repoPais.delete(pais);
		} catch (Exception e) {
			PRG.error("Error al borrar la persona " + nombrePais, "/pais/r");
		}
		return "redirect:/pais/r";
	}
	
	
	
	@GetMapping("r")
	public String mostrar(ModelMap m) {
		m.put("paises", repoPais.findAll());
		m.put("view", "/pais/paisR");
		return "/_t/frame";
	}
	
	
	@GetMapping("c")
	public String crearget(ModelMap m) {
		m.put("view", "/pais/paisC");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String crearPost(
			@RequestParam("nombre") String nombre
			
			) throws DangerException {
		
		try {
		Pais pais = new Pais(nombre);
		repoPais.save(pais);}
		catch (Exception e) {
			Pais pais =  repoPais.getByNombre( nombre);
			if(pais==null) {
			
		}else {
			PRG.error("datos duplicados " + nombre, "/persona/r");
			}
		}
		
		
		return "redirect:/pais/r";}
	
	
	@GetMapping("u")
	public String actualizarGet(ModelMap m, @RequestParam("id") Long id, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);

		
		m.put("paises", repoPais.getOne(id));
		
		m.put("view", "/pais/paisU");
		return "/_t/frame";
	}
	@PostMapping("u")
	public void updatePost(@RequestParam("id") Long idPais, 
			@RequestParam("nombrenuevo") String  nombrenuevo,
			
			 HttpSession s) throws DangerException, InfoException {
		H.isRolOK("admin", s);

		try {
		Pais pais = repoPais.getOne(idPais);

	
		pais.setNombre(nombrenuevo);
		repoPais.save(pais);
		
	}
	
	catch (Exception e) {
		PRG.error("Error al actualizar " + nombrenuevo + " // "+e.getMessage(), "/pais/r");
	}

	PRG.info("Pais " + nombrenuevo + " actualizada correctamente", "/pais/r");

}
	
}
	