package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.OrderPlaced;
import vn.nun.repository.OrderRepository;
import vn.nun.services.OrderPlacedService;

import java.util.List;

@Service
public class OrderPlacedServiceImpl implements OrderPlacedService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<OrderPlaced> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderPlaced findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public OrderPlaced create(OrderPlaced order) {
        return orderRepository.save(order);
    }

    @Override
    public Boolean updateStatus(OrderPlaced order, String status) {
        try {
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public void deleteByOrderItemsIsEmpty() {
        orderRepository.deleteByOrderItemsIsEmpty();
    }
}
