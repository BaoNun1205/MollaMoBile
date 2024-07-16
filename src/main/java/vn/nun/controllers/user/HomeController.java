package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.models.Cart;
import vn.nun.models.CartItem;
import vn.nun.models.Category;
import vn.nun.models.Product;
import vn.nun.services.CartService;
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

	@Autowired
	private CartService cartService;

	@GetMapping
	public String home(Model model) {
		List<Product> all = this.productService.getAll();
//		List<Product> iphones = this.productService.getAll();
//		List<Product> samsungs = this.productService.getAll();
//		List<Product> vivos = this.productService.getAll();
//		List<Product> oppos = this.productService.getAll();
//		List<Product> xiaomis = this.productService.getAll();

		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);

		model.addAttribute("all", all);
		model.addAttribute("iphones", all);
		model.addAttribute("samsungs", all);
		model.addAttribute("vivos", all);
		model.addAttribute("oppos", all);
		model.addAttribute("xiaomis", all);
		return ("user/index");
	}

//	@GetMapping("/new-arrivals/{id}")
//	public String NewArrivalsCategory(Model model, @PathVariable("id") Integer id){
//		Category category = this.categoryService.findById(id);
//
//		List<Product> listProduct = this.productService.findByCategory(category);
//		model.addAttribute("list", listProduct);
//		return ("user/index");
//	}
}
