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

	@GetMapping
	public String home(Model model) {
		List<Product> all = this.productService.getAll();

		List<Category> listCate = this.categoryService.getAll();
		model.addAttribute("listCate", listCate);
		model.addAttribute("all", all);
		return ("user/index");
	}
}
