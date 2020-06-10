package org.morales.proyecto.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.morales.proyecto.domain.Categoria;
import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.exception.DangerException;
import org.morales.proyecto.exception.InfoException;
import org.morales.proyecto.helper.H;
import org.morales.proyecto.helper.PRG;
import org.morales.proyecto.repository.Categoriarepositorio;
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
@RequestMapping(value = "/categoria")
public class Categoriacontroler {
	
	@Autowired
	private Categoriarepositorio repocategoria;
	
	
	@PostMapping("d")
	public String borrarPost(@RequestParam("id") Long idcategoria, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);
		String nombrecategoria = "----";
		try {
			Categoria categoria = repocategoria.getOne(idcategoria);
			nombrecategoria = categoria.getNombre();
			repocategoria.delete(categoria);
		} catch (Exception e) {
			PRG.error("Error al borrar la categoria " + nombrecategoria, "/categoria/r");
		}
		return "redirect:/categoria/r";
	}
	
	
	
	@GetMapping("r")
	public String mostrar(ModelMap m) {
		m.put("categorias", repocategoria.findAll());
		m.put("view", "/categoria/categoriaR");
		return "/_t/frame";
	}
	
	
	@GetMapping("c")
	public String crearget(ModelMap m) {
		m.put("view", "/categoria/categoriaC");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String crearPost(
			@RequestParam("nombre") String nombre
			
			) throws DangerException {
		
		try {
		Categoria categoria = new Categoria(nombre);
		repocategoria.save(categoria);}
		catch (Exception e) {
			Categoria categoria = new Categoria(nombre);
			if(categoria==null) {
			
		}else {
			PRG.error("datos duplicados " + nombre, "/categoria/r");
			}
		}
		
		
		return "redirect:/categoria/r";}
	
	
	@GetMapping("u")
	public String actualizarGet(ModelMap m, @RequestParam("id") Long id, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);

		
		m.put("categorias", repocategoria.getOne(id));
		
		m.put("view", "/categoria/categoriaU");
		return "/_t/frame";
	}
	@PostMapping("u")
	public void updatePost(@RequestParam("id") Long idcategoria, 
			@RequestParam("nombrenuevo") String  nombrenuevo,
			
			 HttpSession s) throws DangerException, InfoException {
		H.isRolOK("admin", s);

		try {
		Categoria categoria = repocategoria.getOne(idcategoria);

	
		categoria.setNombre(nombrenuevo);
		repocategoria.save(categoria);
		
	}
	
	catch (Exception e) {
		PRG.error("Error al actualizar " + nombrenuevo + " // "+e.getMessage(), "/categoria/r");
	}

	PRG.info("categoria " + nombrenuevo + " actualizada correctamente", "/categoria/r");

}
	
}
	