package vn.nun.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import vn.nun.models.Category;
import vn.nun.models.Product;

public interface ProductService {
	List<Product> getAll();
	Boolean create(Product product);
	Product findById(Integer id);
	Boolean update(Product product);
	Boolean delete(Integer id);
	List<Product> findByKeyword(String keyword);
	List<Product> findByKeyword(String keyword, Pageable pageable);
	List<Product> filterByPriceAndCategory(List<String> filters, Category category, String keyword);
	Page<Product> filterByPriceAndCategory(List<String> filters, List<Category> categories, String keyword, Pageable pageable);
	List<Product> filterByPriceSliderAndCategory(Double min, Double max, Category category, String keyword);
	Page<Product> filterByPriceSliderAndCategory(Double min, Double max, List<Category> categories, String keyword, Pageable pageable);

}
