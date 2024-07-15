package vn.nun.services;

import vn.nun.models.Image;
import vn.nun.models.Product;

import java.util.List;

public interface ImageService {
    List<Image> findByProduct(Product product);
    Image findById(Integer id);

    Boolean create(Image image);
    Boolean update(Image image);
    Boolean delete(Integer id);

}
