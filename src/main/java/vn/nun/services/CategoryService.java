package vn.nun.services;

import java.util.List;

import org.springframework.data.domain.Page;

import vn.nun.models.Category;

public interface CategoryService {
	List<Category> getAll();
	Boolean create(Category category);
	Category findById(Integer id);
	Boolean update(Category category);
	Boolean delete(Integer id);
	List<Category> searchCategories(String keyword);
	Page<Category> getAll(Integer pageNo);
	Page<Category> searchCategories(String keyword, Integer pageNo);
}
