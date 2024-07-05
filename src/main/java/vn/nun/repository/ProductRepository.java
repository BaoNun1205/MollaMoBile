package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.nun.models.Category;
import vn.nun.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByCategory(Category category);
}
