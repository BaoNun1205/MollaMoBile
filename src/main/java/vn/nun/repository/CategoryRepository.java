package vn.nun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.nun.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query("Select c from Category c where c.categoryName LIKE %?1%")
	public List<Category> searchCategories(String keyword);
}
