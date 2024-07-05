package vn.nun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.models.Category;
import vn.nun.models.Product;
import vn.nun.services.CategoryService;
import vn.nun.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String home(Model model) {
		List<Product> list = this.productService.getAll();
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("list", list);
		model.addAttribute("listCate", listCate);
		return ("user/index");
	}

	@GetMapping("/new-arrivals/{id}")
	public String NewArrivalsCategory(Model model, @PathVariable("id") Integer id){
		Category category = this.categoryService.findById(id);

		List<Product> listProduct = this.productService.findByCategory(category);
		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		model.addAttribute("list", listProduct);
		return ("user/index");
	}

	@GetMapping("/product")
    public String DetailProduct(){
		return ("user/product");
	}
}
