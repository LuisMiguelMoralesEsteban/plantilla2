package org.morales.proyecto.controller;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpSession;

import org.morales.proyecto.domain.Categoria;
import org.morales.proyecto.domain.Pais;
import org.morales.proyecto.domain.Persona;
import org.morales.proyecto.domain.Producto;
import org.morales.proyecto.domain.Venta;
import org.morales.proyecto.exception.DangerException;
import org.morales.proyecto.exception.InfoException;
import org.morales.proyecto.helper.H;
import org.morales.proyecto.helper.PRG;
import org.morales.proyecto.repository.Categoriarepositorio;
import org.morales.proyecto.repository.Paisrepositorio;
import org.morales.proyecto.repository.Perosnarepositorio;
import org.morales.proyecto.repository.Productorepositorio;
import org.morales.proyecto.repository.Ventarepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/producto")
public class Productocontroler {
	
	@Autowired
	private Productorepositorio repoProducto;
	@Autowired
	private Categoriarepositorio repocategoria;

	@Value("${app.uploadFolder}")
	private String UPLOADED_FOLDER;
	
	

	@GetMapping("u")
	public String actualizarGet(ModelMap m, @RequestParam("id") Long id, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);

		m.put("categorias", repocategoria.findAll());

		m.put("producto", repoProducto.getOne(id));
		
		m.put("view", "/producto/productoU");
		return "/_t/frame";
	}
	
	
	@PostMapping("u")
	public void updatePost(
			@RequestParam("id") Long id,
			@RequestParam("nombre") String nombre,
			
			@RequestParam("stock") int stock,
			@RequestParam("precio") double precio, 
			@RequestParam(value = "idcategoria", required = false ) Long idcategoria,
			@RequestParam("img") MultipartFile imgFile
			
			) throws DangerException, InfoException {
		try {
			Producto producto = repoProducto.getOne(id);

		
			producto.setNombre(nombre);
			producto.setStock(stock);
			producto.setPrecio(precio);

			Categoria categoria = repocategoria.getOne(idcategoria);
			
			
			
			producto.setCategoria(categoria);
		
			String uploadDir = "/img/upload/";
			String uploadDirRealPath = "";
			String fileName = "defeault";
			

			if (imgFile != null && imgFile.getOriginalFilename().split("\\.").length == 2) {
				fileName = "producto-" + producto.getNombre();
			
				uploadDirRealPath = UPLOADED_FOLDER;
				
				File transferFile = new File(uploadDirRealPath + fileName + "." + "png");
				imgFile.transferTo(transferFile);
			}

		String img ="png";
		
		String sFichero = uploadDirRealPath + fileName + "." + "png";
		File fichero = new File(sFichero);

		if (fichero.exists())
			 producto.setImg(img );
		else {
			producto.setImg(null);
		}					
			
			
			repoProducto.save(producto);										
						
			
		}
		
		catch (Exception e) {
			PRG.error("Error al actualizar " + nombre + " // "+e.getMessage(), "/producto/r");
		}

		PRG.info("Producto " + nombre + " actualizado correctamente", "/producto/r");
		
	
		
		}
		
	

	@PostMapping("d")
	public String borrarPost(@RequestParam("id") Long idProducto, HttpSession s) throws DangerException {
		H.isRolOK("admin", s);
		String nombreproducto = "----";
		try {
			Producto producto = repoProducto.getOne(idProducto);
			nombreproducto = producto.getNombre();
			repoProducto.delete(producto);
		} catch (Exception e) {
			PRG.error("Error al borrar la persona " + nombreproducto, "/producto/r");
		}
		return "redirect:/producto/r";
	}
	
	
	
	
	@GetMapping("r")
	public String mostrar(ModelMap m) {
		m.put("productos", repoProducto.findAll());

		m.put("view", "/producto/productoR");
		return "/_t/frame";
	}
	
	
	@GetMapping("c")
	public String registro(ModelMap m) {
		m.put("productos", repoProducto.findAll());
		m.put("categorias", repocategoria.findAll());
		
		m.put("view", "/producto/productoC");
		
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String crearPost(
			@RequestParam("nombre") String nombre,
		 
			@RequestParam("stock") int stock,
			@RequestParam(value="precio") double precio, 
			@RequestParam(value = "idcategoria", required = false ) Long idcategoria,
			@RequestParam("img") MultipartFile imgFile
			
			) throws DangerException, InfoException {
		try {	BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
	
		Producto producto = new Producto( nombre, stock,  precio);
		
		Categoria categoria = repocategoria.getOne(idcategoria);
		if(idcategoria!=null) {
		producto.setCategoria(categoria);}
		
		
		
		
		String uploadDir = "/img/upload/";
		String uploadDirRealPath = "";
		String fileName = "defeault";
		

		if (imgFile != null && imgFile.getOriginalFilename().split("\\.").length == 2) {
			fileName = "producto-" + producto.getNombre();
		
			uploadDirRealPath = UPLOADED_FOLDER;
			
			File transferFile = new File(uploadDirRealPath + fileName + "." + "png");
			imgFile.transferTo(transferFile);
		}

		String img ="png";
		
		String sFichero = uploadDirRealPath + fileName + "." + "png";
		File fichero = new File(sFichero);

		if (fichero.exists())
			 producto.setImg(img );
		

		repoProducto.save(producto);
		
		
		
		
	
	
		}
		catch (Exception e) {
			
			PRG.error("no se  parmite producto sin categoria");
			}
		
		PRG.info("producto creado correctamente");
		return "";}
	
	
	@ResponseBody
	@PostMapping(value="/lanzarajax",produces="text/plain")
	public String lanzarajax( @RequestParam("nombre") String nombre,@RequestParam("stringnombresrepetidos") String stringnombresrepetidos) {
		
		String[] parts = stringnombresrepetidos.split(",");
		String elmensaje="";
		for(int i=0;i<parts.length;i++) {
			if(parts[i].equals(nombre)&&nombre!=null) {
			elmensaje="el producoto"+nombre+"esta duplicado";
		}
			
		
		}
		return elmensaje;}
	

}
	
	
	


	
