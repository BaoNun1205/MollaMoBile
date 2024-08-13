package vn.nun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import vn.nun.models.Category;
import vn.nun.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Page<Product> findByCategory(Category category, Pageable pageable);
    List<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(Double minPrice, Double maxPrice, Category category, String keyword, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndCategoryAndProductNameContainingIgnoreCase(Double minPrice, Category category, String keyword, Pageable pageable);
    Page<Product> findByPriceLessThanEqualAndCategoryAndProductNameContainingIgnoreCase(Double maxPrice, Category category, String keyword, Pageable pageable);
    Page<Product> findByPriceBetweenAndProductNameContainingIgnoreCase(Double minPrice, Double maxPrice, String keyword, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqualAndProductNameContainingIgnoreCase(Double minPrice, String keyword, Pageable pageable);
    Page<Product> findByPriceLessThanEqualAndProductNameContainingIgnoreCase(Double maxPrice, String keyword, Pageable pageable);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceBetweenAndCategoryAndProductNameContainingIgnoreCase(Double minPrice, Double maxPrice, Category category, String keyword);
    List<Product> findByPriceGreaterThanEqualAndCategoryAndProductNameContainingIgnoreCase(Double minPrice, Category category, String keyword);
    List<Product> findByPriceLessThanEqualAndCategoryAndProductNameContainingIgnoreCase(Double maxPrice, Category category, String keyword);
    List<Product> findByPriceBetweenAndProductNameContainingIgnoreCase(Double minPrice, Double maxPrice, String keyword);
    List<Product> findByPriceGreaterThanEqualAndProductNameContainingIgnoreCase(Double minPrice, String keyword);
    List<Product> findByPriceLessThanEqualAndProductNameContainingIgnoreCase(Double maxPrice, String keyword);

    @Query("select p from Product p where p.productName like %?1%")
    List<Product> searchProducts(String keyword);
}
