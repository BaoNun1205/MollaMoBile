package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nun.models.Image;
import vn.nun.models.Product;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByProduct(Product product);
}
