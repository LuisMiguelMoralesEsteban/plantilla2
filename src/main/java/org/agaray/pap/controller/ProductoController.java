package org.agaray.pap.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.agaray.pap.domain.Producto;
import org.agaray.pap.exception.DangerException;
import org.agaray.pap.exception.InfoException;
import org.agaray.pap.helper.H;
import org.agaray.pap.helper.PRG;
import org.agaray.pap.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductoController {

	@Autowired
	ServletContext sc;

	@Autowired
	private ProductoRepository repoProducto;
	
	@GetMapping("/producto/c")
	public String crearGet(ModelMap m,HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("view","producto/productoC");
		return "_t/frame";
	}
	
	@PostMapping("/producto/c")
	public String crearPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("precio") Double precio,
			@RequestParam("stock") Long stock,
			@RequestParam("img") MultipartFile imgFile,
			HttpSession s)
			 throws InfoException, DangerException {
		try {
			Producto producto = new Producto(nombre,stock,precio);
			producto = repoProducto.save(producto);
			
			String uploadDir = "/img/upload/";
			String img = uploadDir+"_p.png";
			
			if (imgFile!=null && imgFile.getOriginalFilename().split("\\.").length==2) {
				String fileExtension =  imgFile.getOriginalFilename().split("\\.")[1];
				String fileName = "p-"+producto.getId();
				img = uploadDir+fileName+"."+fileExtension;
			}
			
			producto.setImg(img);
			repoProducto.save(producto);
			
			//String uploadDirRealPath = sc.getRealPath(uploadDir);
			//File transferFile = new File(uploadDirRealPath + imgFile.getOriginalFilename()); 
			//imgFile.transferTo(transferFile);
			 
		}
		catch (Exception e) {
			//PRG.error("Error al crear el producto "+e.getMessage(),"/producto/r");
			throw e;
		}
		return "redirect:/producto/r";
	}
	
	@GetMapping("/producto/r")
	public String read(HttpSession s,ModelMap m) throws DangerException {
		H.isRolOK("admin", s);
		m.put("productos", repoProducto.findAll());
		m.put("view","producto/productoR");
		return "_t/frame";
	}
}
