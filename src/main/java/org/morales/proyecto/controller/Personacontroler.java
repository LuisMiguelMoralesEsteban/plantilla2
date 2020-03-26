package org.morales.proyecto.controller;

import java.time.LocalDate;

import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.repository.Perosnarepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Personacontroler {
	
	@Autowired
	private Perosnarepositorio repoPersona;
	
	@PostMapping("c")
	public String crearPost(
			
			@RequestParam("loginname") String loginname, 
			@RequestParam("nombre") String nombre,
			@RequestParam("password") String password,
			@RequestParam(value="altura", required=false) Integer altura, 
			@RequestParam(value="fnac", required=false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate fnac
			) {
		
		Persona persona = new Persona(nombre, loginname, password, altura, fnac);
		repoPersona.save(persona);
				return "";}
	
}