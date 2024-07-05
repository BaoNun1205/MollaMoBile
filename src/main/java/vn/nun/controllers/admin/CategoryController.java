package vn.nun.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.nun.models.Category;
import vn.nun.services.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		
		Page<Category> list = this.categoryService.getAll(pageNo);
		
		if (keyword != null) {
			list = this.categoryService.searchCategories(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPages", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("list", list);
		return "admin/category/index";
	}
	
	@GetMapping("/add-category")
	public String add(Model model) {
		Category category = new Category();
		category.setCategoryStatus(true);
		model.addAttribute("category", category);
		return "admin/category/add";
	}
	
	@PostMapping("/add-category")
	public String save(@ModelAttribute("category") Category category) {
		if (this.categoryService.create(category)) {
			return ("redirect:/admin/category"); //return về địa chỉ
		} else {
			return ("admin/category/add"); // return về view
		}
	}
	
	@GetMapping("/edit-category/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Category category = this.categoryService.findById(id);
		model.addAttribute("category", category);
		return ("admin/category/edit");
	}
	
	@PostMapping("/edit-category")
	public String update(@ModelAttribute("category") Category category) {
		if (this.categoryService.update(category)) {
			return ("redirect:/admin/category"); //return về địa chỉ
		} else {
			return ("admin/category/edit"); // return về view
		}
	}
	
	@GetMapping("/delete-category/{id}")
	public String delete(@PathVariable("id") Integer id) {
		if (this.categoryService.delete(id)) {
			return ("redirect:/admin/category"); //return về địa chỉ
		} else {
			return ("redirect:/admin/category"); // return về địa chỉ
		}
	}

}
