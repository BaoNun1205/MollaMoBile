package vn.nun.services;

import vn.nun.models.OrderPlaced;

import java.util.List;

public interface OrderPlacedService {
    List<OrderPlaced> getAll();
    OrderPlaced findById(Integer id);
    OrderPlaced create(OrderPlaced order);
    Boolean updateStatus(OrderPlaced order, String status);
    Boolean delete(Integer id);
    void deleteByOrderItemsIsEmpty();
}
