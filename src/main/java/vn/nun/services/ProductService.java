package vn.nun.services;

import java.util.List;

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
	List<Product> filterByPriceAndCategory(List<String> filters, Category category, String keyword);
	List<Product> filterByPriceSliderAndCategory(Double min, Double max, Category category, String keyword);
}
