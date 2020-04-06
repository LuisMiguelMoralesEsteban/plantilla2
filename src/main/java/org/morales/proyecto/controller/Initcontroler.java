package org.morales.proyecto.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.exception.DangerException;
import org.morales.proyecto.exception.InfoException;
import org.morales.proyecto.helper.H;
import org.morales.proyecto.helper.PRG;
import org.morales.proyecto.repository.Perosnarepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller

public class Initcontroler {
	@Autowired
	private Perosnarepositorio repoPersona;
	
	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {

		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "redirect:/persona/c";
	}
	@GetMapping("init")
	public String crearadmin(ModelMap m
			
	

					
					) throws  InfoException {
				
		if(repoPersona.getByLoginname("admin") == null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
			
				repoPersona.save(new Persona("admin","admin",bpe.encode("admin"), null, null));
				PRG.info("base de datos creada");
						
						
					

			
				
				return "redirect:/";}
		else {
			
			m.put("view", "/anonymous/init");
			return "/_t/frame";
		}
	
 
}

	@GetMapping("/login")
	public String loginGet(ModelMap m,HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("view", "/anonymous/login");
		return "/_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(HttpSession s,ModelMap m,@RequestParam("loginname") String loginname, @RequestParam("password") String password) throws Exception {
		
		
		H.isRolOK("anon", s);
		
		try {
			Persona persona = repoPersona.getByLoginname(loginname);
		if (!(new BCryptPasswordEncoder()).matches(password, persona.getPassword())) {
			
			
			throw new Exception();
		} 
		
		s.setAttribute("persona", persona);
		}catch (Exception e) {
			PRG.error("Usuario o contraseña incorrecta", "/login");
		}
		
		
		

		return "redirect:/";
	
		
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession s) throws DangerException {
		H.isRolOK("auth", s);

		s.invalidate();
		return "redirect:/";
	}
	
	
	@PostMapping("/borrar")
	public String borrarPost(ModelMap m,@RequestParam("password") String password) throws Exception {
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		if(bpe.matches(password, bpe.encode("admin"))) {
		
		
			repoPersona.deleteAll();
			repoPersona.save(new Persona("admin","admin",bpe.encode("admin"), null, null));
			PRG.info("base de datos asido reiniciada");
		
	}else {
		PRG.error("Contraseña incorrecta","/init");
	}
		return "";
	}
			
		}
			
		

