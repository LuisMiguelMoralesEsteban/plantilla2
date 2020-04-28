package org.morales.proyecto.controller;

import java.time.LocalDate;

import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.exception.DangerException;
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
	
}
	