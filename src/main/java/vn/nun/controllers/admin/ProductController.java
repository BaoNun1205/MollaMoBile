package vn.nun.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.nun.models.Category;
import vn.nun.models.Product;
import vn.nun.services.CategoryService;
import vn.nun.services.ProductService;
import vn.nun.services.StorageService;

@Controller
@RequestMapping("/admin")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/product")
	public String index(Model model) {
		List<Product> list = this.productService.getAll();
		model.addAttribute("list", list);
		return ("admin/product/index");
	}
	
	@GetMapping("/add-product")
	public String add(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		return ("admin/product/add");
	}	
	
	@PostMapping("/add-product")
	public String save(@ModelAttribute("product") Product product, @RequestParam("fileImage") MultipartFile file) {
		this.storageService.store(file);
		String fileName = file.getOriginalFilename();
		product.setImage(fileName);
		
		if (this.productService.create(product)) {
			return ("redirect:/admin/product");
		} 
		return ("admin/product/add");
	}
	
	@GetMapping("/edit-product/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Product product = this.productService.findById(id);
		model.addAttribute("product", product);
		
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		return "admin/product/edit";
	}
	
	@PostMapping("/edit-product")
	public String update(@ModelAttribute("product") Product product) {
		if (this.productService.update(product)) {
			return "redirect:/admin/product";
		} else {
			return "admin/product/edit";
		}
	}
	
	@GetMapping("/delete-product/{id}")
	private String delete(@PathVariable("id") Integer id) {
		try {
			this.productService.delete(id);
			return "redirect:/admin/product";
		} catch (Exception e) {
			return "redirect:/admin/product";
		}
	}
}
