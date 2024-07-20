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
}
