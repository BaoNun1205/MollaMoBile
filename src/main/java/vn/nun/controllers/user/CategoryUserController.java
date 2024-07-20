package vn.nun.controllers.user;

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
@RequestMapping("/category")
public class CategoryUserController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/{id}")
    public String DetailCategory(Model model, @PathVariable("id") Integer id){
        Category category = categoryService.findById(id);
        List<Product> listProduct = category.getProducts();

        model.addAttribute("category", category);
        model.addAttribute("listProduct", listProduct);
        return ("user/category");
    }
}
