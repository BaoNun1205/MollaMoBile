package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.Image;
import vn.nun.models.Product;
import vn.nun.repository.ImageRepository;
import vn.nun.services.ImageService;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> findByProduct(Product product) {
        return this.imageRepository.findByProduct(product);
    }

    @Override
    public Image findById(Integer id) {
        return this.imageRepository.findById(id).get();
    }

    @Override
    public Boolean create(Image image) {
        try {
            this.imageRepository.save(image);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Image image) {
        try {
            this.imageRepository.save(image);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            this.imageRepository.delete(findById(id));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
