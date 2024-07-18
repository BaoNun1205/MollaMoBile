package vn.nun.services;

import vn.nun.models.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getAll();
    Delivery findById(Integer id);
    Boolean create(Delivery delivery);
    Boolean update(Delivery delivery);
    Boolean delete(Integer id);
}
