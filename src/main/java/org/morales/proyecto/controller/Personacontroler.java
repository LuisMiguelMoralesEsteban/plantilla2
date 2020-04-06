package org.morales.proyecto.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/persona")
public class Personacontroler {
	
	@Autowired
	private Perosnarepositorio repoPersona;
	
	@Autowired
	private Paisrepositorio repoPais;
	
	@GetMapping("r")
	public String mostrar(ModelMap m) {
		m.put("view", "/persona/personaR");
		return "/_t/frame";
	}
	
	
	@GetMapping("c")
	public String registro(ModelMap m) {
		m.put("paises", repoPais.findAll());
		m.put("view", "/persona/personaC");
		
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String crearPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("loginname") String loginname, 
			@RequestParam("password") String password,
			@RequestParam(value="altura") Integer altura, 
			@RequestParam(value="fnac")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate fnac,
			@RequestParam(value = "idPais", required = false) Long idPais,
			@RequestParam("img") MultipartFile imgFile
			
			) throws DangerException {
		try {	BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
	
		Persona persona = new Persona(nombre, loginname, bpe.encode(password), altura, fnac );
		
		Pais paisNacimiento = repoPais.getOne(idPais);
		paisNacimiento.getNace().add(persona);
		persona.setNace(paisNacimiento);
		String uploadDir = "/img/upload/";
		String uploadDirRealPath = "";
		String fileName = "_p";
		String fileExtension = "png";

		if (imgFile != null && imgFile.getOriginalFilename().split("\\.").length == 2) {
			fileName = "persona-" + persona.getLoginname();
			fileExtension = imgFile.getOriginalFilename().split("\\.")[1];
			uploadDirRealPath = "C:\\worpressts\\LuismiSP\\src\\main\\resources\\static\\img\\upload\\";
			
			File transferFile = new File(uploadDirRealPath + fileName + "." + fileExtension);
			imgFile.transferTo(transferFile);
		}

		String img = uploadDir + fileName + "." + fileExtension;
		persona.setImg(img);
		repoPersona.save(persona);
	
		}
		catch (Exception e) {
			
			PRG.error("error al crear " + nombre, "/persona/r");
			}
		
		
		return "redirect:/persona/r";}
		
	}
	
