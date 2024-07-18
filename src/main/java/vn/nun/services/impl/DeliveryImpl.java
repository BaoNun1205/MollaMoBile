package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.Delivery;
import vn.nun.repository.DeliveryRepository;
import vn.nun.services.DeliveryService;

import java.util.List;

@Service
public class DeliveryImpl implements DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Override
    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery findById(Integer id) {
        return deliveryRepository.findById(id).get();
    }

    @Override
    public Boolean create(Delivery delivery) {
        try {
            deliveryRepository.save(delivery);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Delivery delivery) {
        try {
            deliveryRepository.save(delivery);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            deliveryRepository.delete(findById(id));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
