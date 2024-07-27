package vn.nun.services;

import vn.nun.models.OrderPlaced;
import vn.nun.models.OrderItem;

import java.util.List;

public interface OrderItemService {
    Boolean save(OrderItem orderItem);
    List<OrderItem> findByOrder(OrderPlaced orderPlaced);
}
