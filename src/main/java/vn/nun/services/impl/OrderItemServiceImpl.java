package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.OrderPlaced;
import vn.nun.models.OrderItem;
import vn.nun.repository.OrderItemRepository;
import vn.nun.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Boolean save(OrderItem orderItem) {
        try {
            orderItemRepository.save(orderItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
