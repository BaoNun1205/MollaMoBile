package vn.nun.services;

import java.util.List;

import vn.nun.models.Category;
import vn.nun.models.Product;

public interface ProductService {
	List<Product> getAll();
	Boolean create(Product product);
	Product findById(Integer id);
	Boolean update(Product product);
	Boolean delete(Integer id);
	List<Product> filterByPriceAndCategory(List<String> filters, Category category);
	List<Product> filterByPriceSliderAndCategory(Double min, Double max, Category category);
}
